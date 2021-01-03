package com.finances.controller;

import lombok.NonNull;
import com.finances.bo.ExpenseBo;
import com.finances.bo.UserBo;
import com.finances.dto.ExpenseFilterDTO;
import com.finances.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @NonNull
    private final ExpenseBo expenseBo;

    @NonNull
    private final UserBo userBo;

    @Autowired
    public ExpenseController(ExpenseBo expenseBo, UserBo userBo) {
        this.expenseBo = expenseBo;
        this.userBo = userBo;
    }

    @PostMapping("")
    public ResponseEntity<Expense> create(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseBo.create(expense), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        expenseBo.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> get(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(expenseBo.get(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<Expense>> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "expireAt") String order,
                                              @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                              @RequestParam(name = "category", required = false) Integer category,
                                              @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                              @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                              @RequestParam(name = "name", required = false) String name,
                                              @RequestParam(name = "user", required = false) Integer user) {
        ExpenseFilterDTO expFilter = new ExpenseFilterDTO(category, startDate, endDate, name, user != null ? userBo.get(user) : null);
        return new ResponseEntity<>(expenseBo.list(expFilter, PageRequest.of(page, size,  Sort.by(direction, order))),
                HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Expense> update(@RequestBody Expense dev) {
        return new ResponseEntity<>(expenseBo.update(dev), HttpStatus.OK);
    }

}
