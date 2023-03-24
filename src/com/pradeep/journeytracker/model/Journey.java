package com.pradeep.journeytracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
public abstract class Journey {

    @Getter
    private final String id;

    @Getter
    @Setter
    private JourneyState journeyState;

    @Getter
    @Setter
    private Stage startStage;

    @Getter
    private final Event startEvent;

    public Journey(@NonNull final String id, @NonNull final Stage startStage, @NonNull final Event startEvent) {
        this.id = id;
        this.journeyState = JourneyState.CREATED;
        this.startStage = startStage;
        this.startEvent = startEvent;
    }

    public Boolean isJourneyValid() {
        return journeyState.equals(JourneyState.ACTIVE);
    }
}
