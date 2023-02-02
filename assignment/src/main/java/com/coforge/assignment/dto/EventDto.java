package com.coforge.assignment.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String eventName;
    private String eventLocation;
    private Long eventDuration;
    private Long eventCost;
}
