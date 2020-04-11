package net.finance.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeriodValueDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal plannedValue;
    private BigDecimal actualValue;

}
