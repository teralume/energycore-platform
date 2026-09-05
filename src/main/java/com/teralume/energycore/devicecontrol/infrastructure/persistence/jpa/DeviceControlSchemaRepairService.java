package com.teralume.energycore.devicecontrol.infrastructure.persistence.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DeviceControlSchemaRepairService implements ApplicationRunner {

    private static final String DEVICES_TABLE = "devices";
    private static final String STATUS_CONSTRAINT = "devices_status_check";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        if (!tableExists(DEVICES_TABLE)) {
            return;
        }

        dropStatusCheckConstraints();
        jdbcTemplate.execute("""
                alter table devices
                add constraint devices_status_check
                check (status in ('ON', 'OFF', 'MAINTENANCE', 'REMOVED'))
                """);
    }

    private boolean tableExists(String tableName) {
        Boolean exists = jdbcTemplate.queryForObject(
                "select to_regclass(?) is not null",
                Boolean.class,
                "public." + tableName
        );
        return Boolean.TRUE.equals(exists);
    }

    private void dropStatusCheckConstraints() {
        List<String> constraintNames = jdbcTemplate.queryForList("""
                select con.conname
                from pg_constraint con
                join pg_class rel on rel.oid = con.conrelid
                where rel.relname = ?
                  and con.contype = 'c'
                  and pg_get_constraintdef(con.oid) ilike '%status%'
                """, String.class, DEVICES_TABLE);

        constraintNames.forEach(constraintName ->
                jdbcTemplate.execute("alter table devices drop constraint if exists " + quoteIdentifier(constraintName))
        );
    }

    private String quoteIdentifier(String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }
}
