package com.example.calc.controller;

import com.example.calc.service.HolidayPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
public class HolidayPayController {
    private final HolidayPayService holidayPayService;

    @GetMapping("/{averageSalary}/{daysCount}")
    public double getHolidayPay(@PathVariable double averageSalary, @PathVariable int daysCount){
        return holidayPayService.calculateHolidayPay(averageSalary, daysCount);
    }

    //Даты в формате 01-01-2022
    @GetMapping("/{averageSalary}/{firstDay}/{lastDay}")
    public double getHolidayPay(@PathVariable double averageSalary,
                                @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate firstDay,
                                @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate lastDay) {
        return holidayPayService.calculateHolidayPay(averageSalary, firstDay, lastDay);
    }
}
