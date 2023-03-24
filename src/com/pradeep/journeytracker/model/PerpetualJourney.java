package com.pradeep.journeytracker.model;

import lombok.NonNull;

public class PerpetualJourney extends Journey {

    public PerpetualJourney(@NonNull final String id,
                            @NonNull final Stage startStage,
                            @NonNull final Event startEvent) {
        super(id, startStage, startEvent);
    }
}
