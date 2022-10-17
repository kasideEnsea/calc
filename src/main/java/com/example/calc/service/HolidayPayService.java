package com.example.calc.service;

import com.example.calc.exception.WebException;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayPayService {
    private HolidayManager holidayManager = HolidayManager.getInstance(HolidayCalendar.RUSSIA);


    public double calculateHolidayPay(double averageSalary, int daysCount) {
        if (averageSalary<0){
            throw new WebException("Salary can't be negative", HttpStatus.BAD_REQUEST);
        }
        double holidayPay = averageSalaryPerDay(averageSalary)*daysCount;
        return Precision.round(holidayPay, 2);
    }

    public double calculateHolidayPay(double averageSalary, LocalDate firstDay, LocalDate lastDay) {
        if (firstDay.isAfter(lastDay)){
            throw new WebException("Wrong date order", HttpStatus.BAD_REQUEST);
        }
        if (averageSalary<0){
            throw new WebException("Salary can't be negative", HttpStatus.BAD_REQUEST);
        }
        int businessDayCount = 0;
        List<LocalDate> dates = firstDay.datesUntil(lastDay).collect(Collectors.toList());
        dates.add(lastDay);
        for (LocalDate date: dates
             ) {
            if (!holidayManager.isHoliday(date)
                    && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                    && !date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                businessDayCount++;
            }
        }
        double holidayPay = averageSalaryPerDay(averageSalary)*businessDayCount;
        return Precision.round(holidayPay, 2);
    }

    private double averageSalaryPerDay(double averageSalaryPerMonth){
        //Среднемесячное число рабочих дней - 20.67
        return averageSalaryPerMonth/20.67;
    }
}
