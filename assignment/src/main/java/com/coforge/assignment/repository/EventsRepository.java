package com.coforge.assignment.repository;

import com.coforge.assignment.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventsRepository extends JpaRepository<Event,Long> {

    List<Event> findFirst3ByOrderByDurationDesc();

    List<Event> findFirst3ByOrderByCostDesc();

}
