package com.example.musicmanager.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionMessage {
    private Date date;
    private String message;
    private String description;

}
