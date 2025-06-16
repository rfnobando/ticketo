package com.group10.ticketo.services;

import com.group10.ticketo.dtos.StatusDTO;
import com.group10.ticketo.entities.Status;

import java.util.List;

public interface IStatusService {
     Status findByName(String statusName);
     List<Status> getAllStatuses();
     Status createStatus(StatusDTO dto);
}
