package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.Status;
import com.group10.ticketo.repositories.IStatusRepository;
import com.group10.ticketo.services.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService implements IStatusService {

    @Autowired
    private IStatusRepository statusRepository;
    public Status findByName(String statusName){
        return statusRepository.findByName(statusName);
    }
}
