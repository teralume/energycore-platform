package com.teralume.energycore.devicecontrol.application.internal.queryservices;

import com.teralume.energycore.devicecontrol.application.queryservices.DeviceControlQueryService;
import com.teralume.energycore.devicecontrol.application.results.DeviceGroupDetails;
import com.teralume.energycore.devicecontrol.application.results.OperationModePreviewResult;
import com.teralume.energycore.devicecontrol.application.results.RoutineDetails;
import com.teralume.energycore.devicecontrol.application.services.DeviceControlApplicationService;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.OperationMode;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetDeviceGroupsQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetDevicesQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetOperationModesQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetRoutinesQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.PreviewOperationModeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceControlQueryServiceImpl implements DeviceControlQueryService {
    private final DeviceControlApplicationService applicationService;

    @Override
    public List<Device> handle(GetDevicesQuery query) {
        return applicationService.getDevices(query.userId());
    }

    @Override
    public List<RoutineDetails> handle(GetRoutinesQuery query) {
        return applicationService.getRoutines(query.userId());
    }

    @Override
    public List<DeviceGroupDetails> handle(GetDeviceGroupsQuery query) {
        return applicationService.getDeviceGroups(query.userId());
    }

    @Override
    public List<OperationMode> handle(GetOperationModesQuery query) {
        return applicationService.getOperationModes(query.userId());
    }

    @Override
    public OperationModePreviewResult handle(PreviewOperationModeQuery query) {
        return applicationService.previewOperationMode(query.userId(), query.modeId());
    }
}
