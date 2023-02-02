package com.coforge.assignment.service;

import com.coforge.assignment.dto.EventDto;
import com.coforge.assignment.model.Event;
import com.coforge.assignment.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    public EventDto getEventById(Long id) throws Exception {
        try{
            return eventsRepository.findById(id).map(event-> getEventDto(event))
                    .orElseThrow(() -> new NoSuchElementException("Event not found with given id "+id));
        }catch (NoSuchElementException ex){
            throw ex;
        }catch (Exception ex){
            throw new Exception("Internal server error");
        }
    }

    public List<EventDto> getTop3By(String field){
        try{
            if(field.equals("cost")){
               return eventsRepository.findFirst3ByOrderByCostDesc().stream().map(event-> getEventDto(event))
                        .collect(Collectors.toList());
            } else if (field.equals("duration")) {
                return eventsRepository.findFirst3ByOrderByDurationDesc().stream().map(event-> getEventDto(event))
                        .collect(Collectors.toList());
            }else{
                throw new RuntimeException("Invalid BY field");
            }
        }catch (Exception ex){
            throw ex;
        }
    }

    public Long totalByField(String field){
        try{
            if(null == field || (!field.equals("cost") && !field.equals("duration"))){
                throw new RuntimeException("Invalid BY field");
            }
            List<Event> events = eventsRepository.findAll();
            return events.stream().mapToLong(event->{
                if(field.equals("cost"))
                    return event.getCost();
                if(field.equals("duration"))
                    return event.getDuration();
                return 0;
            }).sum();
        }catch (Exception ex){
            throw ex;
        }
    }

    private static EventDto getEventDto(Event evnt) {
        EventDto dto = new EventDto();
        dto.setId(evnt.getId());
        dto.setEventName(evnt.getName());
        dto.setEventLocation(evnt.getLocation());
        dto.setEventDuration(evnt.getDuration());
        dto.setEventCost(evnt.getCost());
        return dto;
    }
}
