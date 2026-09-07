package com.teralume.energycore.energymonitoring.application.services;

import com.teralume.energycore.devicecontrol.domain.model.DeviceStatus;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceRepository;
import com.teralume.energycore.energymonitoring.domain.model.EnergyReadingStatus;
import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.repositories.EnergyReadingRepository;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergyDashboardSummaryResource;
import com.teralume.energycore.notifications.domain.repositories.AlertRepository;
import com.teralume.energycore.shared.application.events.DomainEventPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.MockMakers;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

class EnergyDashboardPowerTest {
    @Test
    void repeatedSamplesAccumulateEnergyButNotCurrentPower() {
        Device light = device(1L, "Lab", "120", DeviceStatus.ON);
        var summary = summary(List.of(light), Collections.nCopies(200, reading(1L, "120")));

        assertThat(summary.currentWatts()).isEqualByComparingTo("120");
        assertThat(summary.rooms()).hasSize(1);
        assertThat(summary.rooms().get(0).watts()).isEqualByComparingTo("120");
        assertThat(summary.topDevices().get(0).watts()).isEqualByComparingTo("120");
        assertThat(summary.rooms().get(0).kilowattHours()).isEqualByComparingTo("2");
        assertThat(summary.rooms().get(0).estimatedCost()).isEqualByComparingTo("1.5");
        assertThat(summary.topDevices().get(0).readings()).isEqualTo(200);
    }

    @Test
    void roomPowerMatchesActiveDevicesAndRetainsHistoryForInactiveDevices() {
        var summary = summary(List.of(
                device(1L, "Lab", "120", DeviceStatus.ON),
                device(2L, "Lab", "350", DeviceStatus.ON),
                device(3L, "Lab", "900", DeviceStatus.OFF),
                device(4L, "Office", "800", DeviceStatus.MAINTENANCE),
                device(5L, "Office", "700", DeviceStatus.REMOVED)
        ), List.of(reading(1L, "120"), reading(2L, "350"), reading(3L, "900"),
                reading(4L, "800"), reading(5L, "700")));

        assertThat(summary.currentWatts()).isEqualByComparingTo("470");
        var lab = summary.rooms().stream().filter(room -> room.room().equals("Lab")).findFirst().orElseThrow();
        assertThat(lab.watts()).isEqualByComparingTo("470");
        assertThat(lab.activeDevices()).isEqualTo(2);
        assertThat(lab.kilowattHours()).isEqualByComparingTo("0.03");
        assertThat(summary.topDevices().stream().filter(device -> device.deviceId() >= 3))
                .allSatisfy(device -> assertThat(device.watts()).isEqualByComparingTo("0"));
        assertRoomTotalsMatchCurrentPower(summary);
    }

    @Test
    void includesActiveRoomsBeforeTheirFirstReading() {
        var summary = summary(List.of(
                device(1L, " Lab ", "120", DeviceStatus.ON),
                device(2L, "Lab", "350", DeviceStatus.ON),
                device(3L, "Office", "900", DeviceStatus.ON)
        ), List.of());

        assertThat(summary.rooms()).hasSize(2);
        assertThat(summary.rooms()).allSatisfy(room -> {
            assertThat(room.kilowattHours()).isEqualByComparingTo("0");
            assertThat(room.estimatedCost()).isEqualByComparingTo("0");
        });
        assertThat(summary.currentWatts()).isEqualByComparingTo("1370");
        assertRoomTotalsMatchCurrentPower(summary);
    }

    @Test
    void unknownAndBlankRoomsKeepHistoryWithoutInventingCurrentPower() {
        var summary = summary(List.of(
                device(1L, null, "120", DeviceStatus.ON),
                device(2L, " ", null, DeviceStatus.ON)
        ), List.of(reading(1L, "120"), reading(99L, "900")));

        assertThat(summary.rooms()).hasSize(1);
        var room = summary.rooms().get(0);
        assertThat(room.room()).isEqualTo("Sin ambiente");
        assertThat(room.watts()).isEqualByComparingTo("120");
        assertThat(room.activeDevices()).isEqualTo(2);
        assertThat(room.kilowattHours()).isEqualByComparingTo("0.02");
        assertThat(summary.topDevices().stream().filter(device -> device.deviceId().equals(99L)).findFirst().orElseThrow().watts())
                .isEqualByComparingTo("0");
        assertRoomTotalsMatchCurrentPower(summary);
    }

    @Test
    void emptyAccountHasZeroPowerAndNoRooms() {
        var summary = summary(List.of(), List.of());
        assertThat(summary.rooms()).isEmpty();
        assertThat(summary.topDevices()).isEmpty();
        assertThat(summary.currentWatts()).isEqualByComparingTo("0");
    }

    private void assertRoomTotalsMatchCurrentPower(EnergyDashboardSummaryResource summary) {
        BigDecimal total = summary.rooms().stream().map(EnergyDashboardSummaryResource.RoomConsumption::watts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(total).isEqualByComparingTo(summary.currentWatts());
    }

    private EnergyDashboardSummaryResource summary(List<Device> devices, List<EnergyReading> readings) {
        EnergyReadingRepository repository = testMock(EnergyReadingRepository.class);
        DeviceRepository deviceRepository = testMock(DeviceRepository.class);
        AlertRepository alerts = testMock(AlertRepository.class);
        EnergySamplingSettingsService settings = testMock(EnergySamplingSettingsService.class);
        when(deviceRepository.findByUserId(42L)).thenReturn(devices);
        when(repository.findByUserIdAndRecordedAtBetweenOrderByRecordedAtDesc(eq(42L), any(), any())).thenReturn(readings);
        when(alerts.findByUserIdAndActiveTrue(42L)).thenReturn(List.of());
        when(settings.getSampleSeconds()).thenReturn(15);
        return new EnergyMonitoringApplicationService(repository, deviceRepository, alerts,
                testMock(EnergyReadingRecorderService.class), settings, testMock(DomainEventPublisher.class))
                .getDashboardSummary(42L);
    }

    private <T> T testMock(Class<T> type) {
        // These collaborators do not require JVM agent attachment or final-class mocking.
        return mock(type, withSettings().mockMaker(MockMakers.SUBCLASS));
    }

    private Device device(Long id, String room, String power, DeviceStatus status) {
        Device device = new Device();
        ReflectionTestUtils.setField(device, "id", id);
        device.setUserId(42L);
        device.setName("Demo " + id);
        device.setRoom(room);
        device.setType("OTHER");
        device.setPowerWatts(power == null ? null : new BigDecimal(power));
        device.setStatus(status);
        return device;
    }

    private EnergyReading reading(Long deviceId, String power) {
        EnergyReading reading = new EnergyReading();
        reading.setDeviceId(deviceId);
        reading.setUserId(42L);
        reading.setDeviceName("Demo " + deviceId);
        reading.setWatts(new BigDecimal(power));
        reading.setRecordedAt(LocalDateTime.now());
        reading.setKilowattHours(new BigDecimal("0.01"));
        reading.setEstimatedCost(new BigDecimal("0.0075"));
        reading.setStatus(EnergyReadingStatus.NORMAL);
        return reading;
    }
}
