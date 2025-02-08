package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.TableTimeDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TableTimeForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.ScheduleRepository;
import com.example.medical_appointment_booking_app.repository.TimeScheduleRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import com.example.medical_appointment_booking_app.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimeScheduleRepository timeScheduleRepository;

    @Override
    public ResponseData<String> createSchedule(Principal principal,Long timeScheduleId) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TimeSchedule timeSchedule = timeScheduleRepository.findById(timeScheduleId)
                .orElseThrow(() -> new RuntimeException("TimeSchedule not found"));

        Schedule schedule = new Schedule();
        schedule.setUser(user);
        schedule.setTimeSchedule(timeSchedule);
        scheduleRepository.save(schedule);

        timeSchedule.getSchedules().add(schedule);
        timeScheduleRepository.save(timeSchedule);

        return new ResponseData<>(200, "Schedule created successfully");
    }

    @Override
    public ResponseData<List<ScheduleDto>> getByDoctorIdAndDate(Long doctorId, LocalDate date) {

        List<TimeSchedule> timeSchedules = timeScheduleRepository.findAllByAppointmentDate(date);

        if (timeSchedules.isEmpty()) {
            return new ResponseData<>(200, "No time schedules found for the given date");
        }

        User user = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Schedule> schedules = scheduleRepository.findByTimeScheduleInAndUser(timeSchedules, user);

        if (schedules.isEmpty()) {
            return new ResponseData<>(200, "No schedules found for the given doctor and date");
        }

        List<ScheduleDto> scheduleDtos = schedules.stream()
                .map(ScheduleDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Get successfully", scheduleDtos);
    }

    @Override
    public ResponseData<List<TableTimeDto>> getMyScheduleInWeek(Principal principal, TableTimeForm form) {
        // Lấy user từ Principal (bác sĩ đang đăng nhập)
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy tất cả TimeSchedule trong khoảng thời gian từ startDate đến endDate
        List<TimeSchedule> timeSchedules = timeScheduleRepository.findByAppointmentDateBetween(form.getStartDate(), form.getEndDate());

        if (timeSchedules.isEmpty()) {
            return new ResponseData<>(200, "No time schedules found for the given week", null);
        }

        // Lấy tất cả Schedule của bác sĩ trong khoảng thời gian đó
        List<Schedule> schedules = scheduleRepository.findByTimeScheduleInAndUser(timeSchedules, user);

        if (schedules.isEmpty()) {
            return new ResponseData<>(200, "No schedules found for the given doctor and week", null);
        }

        // Nhóm các TimeSchedule theo ngày
        Map<LocalDate, List<TimeSchedule>> groupedByDate = timeSchedules.stream()
                .collect(Collectors.groupingBy(TimeSchedule::getAppointmentDate));

        // Tạo danh sách TableTimeDto từ các nhóm đã gộp
        List<TableTimeDto> tableTimeDtos = groupedByDate.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<TimeSchedule> schedulesForDate = entry.getValue();

                    // Lấy tất cả Schedule của bác sĩ trong các TimeSchedule cùng ngày
                    List<ScheduleDto> scheduleDtos = schedulesForDate.stream()
                            .flatMap(timeSchedule -> timeSchedule.getSchedules().stream())
                            .filter(schedule -> schedule.getUser().getUserId().equals(user.getUserId())) // Lọc đúng bác sĩ
                            .map(ScheduleDto::toDto)
                            .collect(Collectors.toList());

                    return TableTimeDto.builder()
                            .date(date)
                            .schedules(scheduleDtos)
                            .build();
                })
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Get successfully", tableTimeDtos);
    }

    @Override
    public ResponseData<List<ScheduleDto>> getMySchedule(Principal principal, LocalDate date) {
        List<TimeSchedule> timeSchedules = timeScheduleRepository.findAllByAppointmentDate(date);

        if (timeSchedules.isEmpty()) {
            return new ResponseData<>(200, "No time schedules found for the given date");
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Schedule> schedules = scheduleRepository.findByTimeScheduleInAndUser(timeSchedules, user);

        if (schedules.isEmpty()) {
            return new ResponseData<>(200, "No schedules found for the given doctor and date");
        }

        List<ScheduleDto> scheduleDtos = schedules.stream()
                .map(ScheduleDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Get successfully", scheduleDtos);
    }

    @Override
    public ResponseData<List<AppointmentDto>> getListAppointmentInSchedule(Long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        List<Appointment> appointments = schedule.getAppointments();
        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map(AppointmentDto::toDto)
                .toList();
        return new ResponseData<>(200, "Get successfully", appointmentDtos);
    }


}
