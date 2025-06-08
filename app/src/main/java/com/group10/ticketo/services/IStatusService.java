package com.group10.ticketo.services;

import com.group10.ticketo.entities.Status;

public interface IStatusService {
     Status findByName(String statusName);
}
