package com.example.TeachUABot.model;

import lombok.Data;

@Data
public class ClubModel {
    private Long id;
    private Integer ageFrom;
    private Integer ageTo;
    private String name;
    private String description;
}
