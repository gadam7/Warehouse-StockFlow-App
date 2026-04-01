package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.Enum.EventType;
import com.adamidis.learning.warehousestockflow.Model.UserEvent;

import java.util.Collection;

public interface EventService {
    Collection<UserEvent> getEventsByUserId(Long userId);
    void addUserEvent(String email, EventType eventType, String device, String ipAddress);
    void addUserEvent(Long userId, EventType eventType, String device, String ipAddress);
}
