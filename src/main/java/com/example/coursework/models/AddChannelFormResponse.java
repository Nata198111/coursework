package com.example.coursework.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddChannelFormResponse {
    private String title;
    private String description;
}
