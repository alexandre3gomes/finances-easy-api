package net.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.finance.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseFilterDTO {

    Integer categoryId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String name;
    User user;

}
