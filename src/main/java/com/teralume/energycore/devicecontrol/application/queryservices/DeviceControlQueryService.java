package com.teralume.energycore.devicecontrol.application.queryservices;

import com.teralume.energycore.devicecontrol.application.results.DeviceGroupDetails;
import com.teralume.energycore.devicecontrol.application.results.OperationModePreviewResult;
import com.teralume.energycore.devicecontrol.application.results.RoutineDetails;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.OperationMode;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetDeviceGroupsQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetDevicesQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetOperationModesQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.GetRoutinesQuery;
import com.teralume.energycore.devicecontrol.domain.model.queries.PreviewOperationModeQuery;

import java.util.List;

public interface DeviceControlQueryService {
    List<Device> handle(GetDevicesQuery query);
    List<RoutineDetails> handle(GetRoutinesQuery query);
    List<DeviceGroupDetails> handle(GetDeviceGroupsQuery query);
    List<OperationMode> handle(GetOperationModesQuery query);
    OperationModePreviewResult handle(PreviewOperationModeQuery query);
}
