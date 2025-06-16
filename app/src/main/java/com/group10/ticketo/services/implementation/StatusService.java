package com.group10.ticketo.services.implementation;

import com.group10.ticketo.dtos.StatusDTO;
import com.group10.ticketo.entities.Status;
import com.group10.ticketo.repositories.IStatusRepository;
import com.group10.ticketo.services.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService implements IStatusService {

    @Autowired
    private IStatusRepository statusRepository;

    public Status findByName(String statusName) {
        return statusRepository.findByName(statusName);
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public Status createStatus(StatusDTO dto) {
        Status status = new Status();
        status.setName(dto.getName());
        return statusRepository.save(status);
    }
}