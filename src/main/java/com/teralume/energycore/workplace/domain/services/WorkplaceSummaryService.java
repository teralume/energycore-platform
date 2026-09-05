package com.teralume.energycore.workplace.domain.services;

import com.teralume.energycore.workplace.domain.model.aggregates.DeviceAssignment;
import com.teralume.energycore.workplace.domain.model.aggregates.Location;
import com.teralume.energycore.workplace.domain.model.aggregates.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkplaceSummaryService {

    public int countLocations(List<Location> locations) {
        return locations == null ? 0 : locations.size();
    }

    public int countRooms(List<Room> rooms) {
        return rooms == null ? 0 : rooms.size();
    }

    public int countAssignments(List<DeviceAssignment> assignments) {
        return assignments == null ? 0 : assignments.size();
    }
}