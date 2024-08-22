package com.nqt.spring_boot_espada_store.service.cleanup;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Date;
import java.util.EnumSet;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nqt.spring_boot_espada_store.repository.OrderRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderCleanupService {

    OrderRepository orderRepository;

    @NonFinal
    static EnumSet<Month> TARGET_MONTHS = EnumSet.of(Month.MARCH, Month.JUNE, Month.SEPTEMBER, Month.DECEMBER);

    @Scheduled(cron = "0 0 0 L 3,6,9,12 ?")
    public void deleteOrdersOnLastDayOfTargetMonths() {
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();

        if (TARGET_MONTHS.contains(currentMonth) && isLastDayOfMonth(today)) {
            orderRepository.deleteOrdersByOrderingDateBefore(new Date());
        }
    }

    private boolean isLastDayOfMonth(LocalDate date) {
        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
        return date.getDayOfMonth() == yearMonth.lengthOfMonth();
    }
}
