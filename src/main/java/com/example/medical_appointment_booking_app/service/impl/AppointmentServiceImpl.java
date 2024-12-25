package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.entity.DailyAppointmentStats;
import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AppointmentForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.AppointmentRepository;
import com.example.medical_appointment_booking_app.repository.DailyAppointmentStatsRepository;
import com.example.medical_appointment_booking_app.repository.ScheduleRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import com.example.medical_appointment_booking_app.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DailyAppointmentStatsRepository dailyAppointmentStatsRepository;

    @Override
    public ResponseData<AppointmentDto> createRequest(AppointmentForm form, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("Creating a new appointment");

        Schedule schedule = scheduleRepository.findById(form.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        List<Appointment> listAppointments = schedule.getAppointments();

        if (schedule.isFull()) {
            return new ResponseData<>(400, "List of appointments is full");
        }
        log.info(schedule.getTimeSchedule().getAppointmentDate().toString());
        DailyAppointmentStats dailyAppointmentStats = dailyAppointmentStatsRepository.findByDate(schedule.getTimeSchedule().getAppointmentDate())
                .orElseThrow(() -> new RuntimeException("DailyAppointmentStats not found"));

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setSchedule(schedule);
        appointment.setDoctor(schedule.getUser());
        appointment.setReason(form.getReason());
        appointment.setCreateDate(LocalDate.now());
        appointment.setStatus(Appointment.Status.PENDING);
        appointment.setCome(false);
        appointment.setAppointmentNumber(dailyAppointmentStats.getTotalAppointments()+1);
        appointmentRepository.save(appointment);

        listAppointments.add(appointment);
        schedule.setAppointments(listAppointments);
        scheduleRepository.save(schedule);

        schedule.updateStatus();
        scheduleRepository.save(schedule);

        dailyAppointmentStats.setTotalAppointments(dailyAppointmentStats.getTotalAppointments()+1);
        dailyAppointmentStatsRepository.save(dailyAppointmentStats);

        return new ResponseData<>(200,"appointment created", AppointmentDto.toDto(appointment));
    }
}
