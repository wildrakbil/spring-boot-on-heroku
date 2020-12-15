package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.*;
import com.stefanini.springboot.app.view.dto.*;
import org.springframework.stereotype.Component;


@Component(value = "mapper")
public class Mapper implements IMapper {
    @Override
    public UserDTO mapUser(User in) {
        if (in == null) {
            return null;
        }
        UserDTO out = new UserDTO();
        out.setId(in.getId());
        out.setRole(mapRole(in.getRole()));
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setCreateday(in.getCreateday());
        out.setUpdateat(in.getUpdateat());
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setIdentification(in.getIdentification());
        out.setPhone(in.getPhone());
        out.setAddress(in.getAddress());
        return out;
    }

    @Override
    public User mapUser(UserDTO in) {
        if (in == null) {
            return null;
        }
        User out = new User();
        out.setId(in.getId());
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setIdentification(in.getIdentification());
        out.setRole(mapRole(in.getRole()));
        return out;
    }

    @Override
    public RoleDTO mapRole(Role in) {
        if (in == null) {
            return null;
        }
        RoleDTO out = new RoleDTO();
        //out.setId(in.getId());
        out.setName(in.getName());
        //out.setPermits(in.getPermits());
        return out;
    }

    @Override
    public Role mapRole(RoleDTO in) {
        if (in == null) {
            return null;
        }
        Role out = new Role();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setPermits(in.getPermits());
        return out;
    }

    @Override
    public Schedule mapSchedule(ScheduleDTO in) {
        if (in == null) {
            return null;
        }
        Schedule out = new Schedule();
        out.setId(in.getId());
        out.setDay(in.getDay());
        out.setStartTime(in.getStartTime());
        out.setEndTime(in.getEndTime());
        return out;
    }

    @Override
    public ScheduleDTO mapSchedule(Schedule in) {
        if (in == null) {
            return null;
        }
        ScheduleDTO out = new ScheduleDTO();
        out.setId(in.getId());
        out.setDay(in.getDay());
        out.setStartTime(in.getStartTime());
        out.setEndTime(in.getEndTime());
        return out;
    }

    @Override
    public Service mapService(ServiceDTO in) {
        if (in == null) {
            return null;
        }
        Service out = new Service();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setDescription(in.getDescription());
        out.setPathImg(in.getPathImg());
        out.setState(in.getState());
        return out;
    }

    @Override
    public ServiceDTO mapService(Service in) {
        if (in == null) {
            return null;
        }
        ServiceDTO out = new ServiceDTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setDescription(in.getDescription());
        out.setPathImg(in.getPathImg());
        out.setState(in.getState());
        return out;
    }

    @Override
    public Booking mapBooking(BookingDTO in) {
        if (in == null) {
            return null;
        }
        Booking out = new Booking();
        out.setId(in.getId());
        out.setUser(mapUser(in.getUser()));
        out.setService(mapService(in.getService()));
        out.setDay(in.getDay());
        out.setStartTime(in.getStartTime());
        return out;
    }

    @Override
    public BookingDTO mapBooking(Booking in) {
        if (in == null) {
            return null;
        }
        BookingDTO out = new BookingDTO();
        out.setId(in.getId());
        out.setUser(mapUser(in.getUser()));
        out.setService(mapService(in.getService()));
        out.setDay(in.getDay());
        out.setStartTime(in.getStartTime());
        return out;
    }

    @Override
    public Billing mapBilling(BillingDTO in) {
        if (in == null) {
            return null;
        }
        Billing out = new Billing();
        out.setId(in.getId());
        out.setBooking(mapBooking(in.getBooking()));
        out.setPayment(mapPayment(in.getPayment()));
        out.setDescription(in.getDescription());
        out.setDate(in.getDate());
        out.setStartTime(in.getStartTime());
        out.setAmount(in.getAmount());
        //out.setState(in.getState());
        return out;
    }

    @Override
    public BillingDTO mapBilling(Billing in) {
        if (in == null) {
            return null;
        }
        BillingDTO out = new BillingDTO();
        out.setId(in.getId());
        out.setBooking(mapBooking(in.getBooking()));
        out.setPayment(mapPayment(in.getPayment()));
        out.setDescription(in.getDescription());
        out.setDate(in.getDate());
        out.setStartTime(in.getStartTime());
        out.setAmount(in.getAmount());
        out.setState(in.getState());
        return out;
    }

    @Override
    public Payment mapPayment(PaymentDTO in) {
        if (in == null) {
            return null;
        }
        Payment out = new Payment();
        out.setId(in.getId());
        out.setType(in.getType());
        out.setDescription(in.getDescription());
        return out;
    }

    @Override
    public PaymentDTO mapPayment(Payment in) {
        if (in == null) {
            return null;
        }
        PaymentDTO out = new PaymentDTO();
        out.setId(in.getId());
        out.setType(in.getType());
        out.setDescription(in.getDescription());
        return out;
    }
}





























