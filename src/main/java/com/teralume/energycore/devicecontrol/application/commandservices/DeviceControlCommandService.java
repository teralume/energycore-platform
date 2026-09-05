package com.teralume.energycore.devicecontrol.application.commandservices;

import com.teralume.energycore.devicecontrol.application.results.DeviceGroupDetails;
import com.teralume.energycore.devicecontrol.application.results.OperationModeActivationResult;
import com.teralume.energycore.devicecontrol.application.results.RoutineDetails;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.OperationMode;
import com.teralume.energycore.devicecontrol.domain.model.commands.ActivateOperationModeCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.ArchiveOperationModeCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.CreateDeviceCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.CreateDeviceGroupCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.CreateOperationModeCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.CreateRoutineCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.DeleteDeviceCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.DeleteDeviceGroupCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.DeleteRoutineCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.ExecuteGroupActionCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.ExecuteRoutineCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.PairDeviceCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.ToggleDeviceCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateDeviceCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateDeviceGroupCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateDeviceStatusCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateRoutineStatusCommand;

public interface DeviceControlCommandService {
    Device handle(CreateDeviceCommand command);
    Device handle(PairDeviceCommand command);
    Device handle(UpdateDeviceCommand command);
    Device handle(ToggleDeviceCommand command);
    Device handle(Long deviceId, Long userId, UpdateDeviceStatusCommand command);
    void handle(DeleteDeviceCommand command);
    RoutineDetails handle(CreateRoutineCommand command);
    RoutineDetails handle(Long routineId, Long userId, UpdateRoutineStatusCommand command);
    RoutineDetails handle(ExecuteRoutineCommand command);
    void handle(DeleteRoutineCommand command);
    DeviceGroupDetails handle(CreateDeviceGroupCommand command);
    DeviceGroupDetails handle(Long userId, Long groupId, UpdateDeviceGroupCommand command);
    void handle(Long userId, Long groupId, ExecuteGroupActionCommand command);
    void handle(DeleteDeviceGroupCommand command);
    OperationMode handle(CreateOperationModeCommand command);
    OperationModeActivationResult handle(ActivateOperationModeCommand command);
    void handle(ArchiveOperationModeCommand command);
}
