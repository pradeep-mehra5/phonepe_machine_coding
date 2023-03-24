package com.pradeep.journeytracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserJourney {

    private final User user;
    private final Journey journey;
    private Stage stage;

}
