package com.coforge.assignment;

import com.coforge.assignment.dto.EventDto;
import com.coforge.assignment.model.Event;
import com.coforge.assignment.repository.EventsRepository;
import com.coforge.assignment.service.EventsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest
public class EventServiceMockTest {

    @Mock
    private EventsRepository eventsRepositoryMock;

    @InjectMocks
    private EventsService eventsService;

    @Test
    public void testGetEventById() throws Exception {
        Event event = new Event();
        event.setId(1l);
        event.setName("Test Event");
        event.setLocation("Delhi");
        event.setCost(5000l);
        event.setDuration(5l);
        Mockito.when(eventsRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(event));
        EventDto dto = eventsService.getEventById(123l);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getEventName(),event.getName());
    }

    @Test
    public void testGetEventByIdNotFound(){
        Mockito.when(eventsRepositoryMock.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            eventsService.getEventById(123l);
        });
    }

    @Test
    public void testGetTop3By(){
        Event event1 = new Event();
        event1.setId(1l);
        event1.setName("Test Event");
        event1.setLocation("Delhi");
        event1.setCost(1000l);
        event1.setDuration(10l);

        Event event2 = new Event();
        event2.setId(1l);
        event2.setName("Test Event");
        event2.setLocation("Delhi");
        event2.setCost(2000l);
        event2.setDuration(9l);

        Event event3 = new Event();
        event3.setId(1l);
        event3.setName("Test Event");
        event3.setLocation("Delhi");
        event3.setCost(3000l);
        event3.setDuration(8l);

        Event event4 = new Event();
        event4.setId(1l);
        event4.setName("Test Event");
        event4.setLocation("Delhi");
        event4.setCost(4000l);
        event4.setDuration(7l);

        List<Event> costEvents = Arrays.asList(event3,event2,event1);
        List<Event> durationEvents = Arrays.asList(event2,event3,event4);

        Mockito.when(eventsRepositoryMock.findFirst3ByOrderByCostDesc()).thenReturn(costEvents);
        Mockito.when(eventsRepositoryMock.findFirst3ByOrderByDurationDesc()).thenReturn(durationEvents);

        List<EventDto> costDtos = eventsService.getTop3By("cost");
        Assertions.assertEquals(costDtos.size(),3);
        Assertions.assertEquals(costDtos.get(0).getEventCost(),3000);

        List<EventDto> durationDtos = eventsService.getTop3By("duration");
        Assertions.assertEquals(durationDtos.size(),3);
        Assertions.assertEquals(durationDtos.get(0).getEventDuration(),9);

    }

    @Test
    public void testGetTop3ByException(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            eventsService.getTop3By("invalid");
        });
    }

    @Test
    public void testTotalByField(){
        Event event1 = new Event();
        event1.setId(1l);
        event1.setName("Test Event");
        event1.setLocation("Delhi");
        event1.setCost(1000l);
        event1.setDuration(10l);

        Event event2 = new Event();
        event2.setId(1l);
        event2.setName("Test Event");
        event2.setLocation("Delhi");
        event2.setCost(2000l);
        event2.setDuration(9l);

        Event event3 = new Event();
        event3.setId(1l);
        event3.setName("Test Event");
        event3.setLocation("Delhi");
        event3.setCost(3000l);
        event3.setDuration(8l);

        Event event4 = new Event();
        event4.setId(1l);
        event4.setName("Test Event");
        event4.setLocation("Delhi");
        event4.setCost(4000l);
        event4.setDuration(7l);

        List<Event> events = Arrays.asList(event1,event2,event3,event4);
        Mockito.when(eventsRepositoryMock.findAll()).thenReturn(events);

        Long costSum = eventsService.totalByField("cost");
        Assertions.assertEquals(costSum,10000);

        Long durationSum = eventsService.totalByField("duration");
        Assertions.assertEquals(durationSum,34);

    }

    @Test
    public void testGetTotalByFieldException(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            eventsService.totalByField("invalid");
        });
    }
}
