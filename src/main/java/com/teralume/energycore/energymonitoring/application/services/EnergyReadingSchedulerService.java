package com.teralume.energycore.energymonitoring.application.services;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.model.DeviceStatus;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyReadingSchedulerService implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyReadingSchedulerService.class);

    private final DeviceRepository deviceRepository;
    private final EnergyReadingRecorderService recorderService;
    private final EnergySamplingSettingsService settingsService;

    public void recordActiveDevicesConsumption() {
        List<Device> activeDevices = deviceRepository.findAll()
                .stream()
                .filter(device -> device.getStatus() == DeviceStatus.ON)
                .toList();

        activeDevices.forEach(device -> {
            try {
                recorderService.recordActiveDeviceInterval(device);
            } catch (RuntimeException exception) {
                LOGGER.warn("Could not record scheduled energy reading for device {}", device.getId(), exception);
            }
        });
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                this::recordActiveDevicesConsumption,
                context -> {
                    Instant lastCompletion = context.lastCompletion();
                    Instant nextRunBase = lastCompletion != null ? lastCompletion : Instant.now();
                    return nextRunBase.plusSeconds(settingsService.getSampleSeconds());
                }
        );
    }
}
