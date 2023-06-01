package com.example.coursework.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddProgrammesFormResponse {
    private String title;
    private String description;
    private String time;
}
