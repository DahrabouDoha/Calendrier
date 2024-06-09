package com.app.calendrier1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private String title;
    private String date;
    private String time;
    private String eventType;
    private String meetingType;
    private String remoteUrl;
    private List<String> participantEmails;

}