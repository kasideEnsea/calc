package com.example.calc;

import com.example.calc.exception.WebException;
import com.example.calc.service.HolidayPayService;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class HolidayPayServiceTest {

    @Test
    public void holidayPayService_VALID() {
        HolidayPayService holidayPayService = new HolidayPayService();
        double actual = holidayPayService.calculateHolidayPay(15000, LocalDate.of(2022, Month.MARCH, 1),
                                                 LocalDate.of(2022, Month.MARCH, 10));
        double expected = (15000/20.67)*7;
        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test(expected= WebException.class)
    public void holidayPayService_negativeSalary() {
        HolidayPayService holidayPayService = new HolidayPayService();
        holidayPayService.calculateHolidayPay(-15000, LocalDate.of(2022, Month.MARCH, 1),
                LocalDate.of(2022, Month.MARCH, 10));
    }

    @Test(expected= WebException.class)
    public void holidayPayService_wrongDateOrder() {
        HolidayPayService holidayPayService = new HolidayPayService();
        holidayPayService.calculateHolidayPay(15000, LocalDate.of(2022, Month.MARCH, 10),
                LocalDate.of(2022, Month.MARCH, 1));

    }

    @Test
    public void holidayPayService_vacationConsistsOfWeekends() {
        HolidayPayService holidayPayService = new HolidayPayService();
        double actual = holidayPayService.calculateHolidayPay(15000, LocalDate.of(2022, Month.OCTOBER, 22),
                LocalDate.of(2022, Month.OCTOBER, 23));
        double expected = 0;
        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void holidayPayService_oneDayVacation() {
        HolidayPayService holidayPayService = new HolidayPayService();
        double actual = holidayPayService.calculateHolidayPay(15000, LocalDate.of(2022, Month.OCTOBER, 18),
                LocalDate.of(2022, Month.OCTOBER, 18));
        double expected = 15000/20.67;
        Assert.assertEquals(expected, actual, 0.01);
    }
}
