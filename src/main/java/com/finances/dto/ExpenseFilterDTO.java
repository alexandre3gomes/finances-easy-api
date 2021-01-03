package com.finances.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.finances.entity.User;

import java.time.LocalDateTime;

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
