package com.pradeep.journeytracker.model;

import com.pradeep.journeytracker.exception.InvalidTransitionException;
import com.pradeep.journeytracker.exception.InvalidUserException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Stage {

    private final String id;
    private final String name;
    private final Journey journey;
    private final Map<Event, Stage> nextStages;

    public Stage(@NonNull final String id,
                 @NonNull final String name,
                 @NonNull final Journey journey) {
        this.id = id;
        this.name = name;
        this.journey = journey;
        this.nextStages = new HashMap<>();
    }

    public Stage nextStage(@NonNull final Event inputEvent) {
        if(!journey.isJourneyValid()) {
            throw new InvalidTransitionException("Transition not permitted in non active journey");
        }
        if(nextStages.isEmpty()) {
            throw new InvalidTransitionException("Transition not permitted from terminal stage");
        }
        if(!nextStages.containsKey(inputEvent)) {
            throw new InvalidTransitionException("No possible transition for the event");
        }
        return nextStages.get(inputEvent);
    }

    public void addNextStage(@NonNull final Event event, @NonNull final Stage stage) {
        if(nextStages.containsKey(event)) {
            throw new InvalidTransitionException("next stage already present for the event");
        }

        nextStages.put(event, stage);
    }
}
