package com.example.TeachUABot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
public class ChallengeModel {
    private Long id;
    private String name;
    private String title;
    private String description;
    private Long sortNumber;
    private Boolean isActive;
}
