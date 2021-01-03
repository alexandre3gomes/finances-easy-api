package com.finances.controller;

import lombok.NonNull;
import com.finances.bo.SavingsBo;
import com.finances.entity.Savings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/savings")
public class SavingsController {


    @NonNull
    private final SavingsBo savingsBo;

    @Autowired
    public SavingsController(SavingsBo savingsBo) {
        this.savingsBo = savingsBo;
    }

    @PostMapping("")
    public ResponseEntity<Savings> create(@RequestBody Savings sav) {
        return new ResponseEntity<>(savingsBo.create(sav), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        savingsBo.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Savings> get(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(savingsBo.get(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<Savings>> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdDate") String order,
                                              @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return new ResponseEntity<>(savingsBo.list(PageRequest.of(page, size, Sort.by(direction, order))),
                HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Savings> update(@RequestBody Savings sav) {
        return new ResponseEntity<>(savingsBo.update(sav), HttpStatus.OK);
    }

}
