package com.coforge.assignment.controller;

import com.coforge.assignment.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/event")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok().body(eventsService.getEventById(id));
        }catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/top3")
    public ResponseEntity getTop3Events(@RequestParam(name = "by") String by) {
        try {
            return ResponseEntity.ok().body(eventsService.getTop3By(by));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/total")
    public ResponseEntity getTotalByField(@RequestParam(name = "by") String by) {
        try {
            return ResponseEntity.ok().body(eventsService.totalByField(by));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
