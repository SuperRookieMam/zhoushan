package com.sofmit.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofmit.health.repository.VaccineRepository;

@RestController
@RequestMapping("ac")
public class Ac {

    @Autowired
    private VaccineRepository vr;

    
    @GetMapping
    public String test() {
        vr.findEByName();
        return "success";
    }

}
