package net.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.finance.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExpenseFilterDTO {

    Integer categoryId;
    Date startDate;
    Date endDate;
    String name;
    User user;

}
