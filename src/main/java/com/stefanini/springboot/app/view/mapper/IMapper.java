package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.*;
import com.stefanini.springboot.app.view.dto.*;

public interface IMapper {

    UserDTO mapUser(User in);
    User mapUser(UserDTO in);
    RoleDTO mapRole(Role in);
    Role mapRole(RoleDTO in);
    Schedule mapSchedule(ScheduleDTO in);
    ScheduleDTO mapSchedule(Schedule in);
    Service mapService(ServiceDTO in);
    ServiceDTO mapService(Service in);
    Booking mapBooking(BookingDTO in);
    BookingDTO mapBooking(Booking in);
}
