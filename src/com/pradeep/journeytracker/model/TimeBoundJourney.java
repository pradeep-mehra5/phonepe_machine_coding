package com.pradeep.journeytracker.model;

import lombok.NonNull;

import java.time.Instant;
import java.util.Date;

public class TimeBoundJourney extends Journey {

    private final Date startDate;
    private final Date endDate;

    public TimeBoundJourney(@NonNull final String id,
                            @NonNull final Date startDate,
                            @NonNull final Date endDate,
                            @NonNull final Stage startStage,
                            @NonNull final Event startEvent) {
        super(id, startStage, startEvent);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Override
    public Boolean isJourneyValid() {
        final Instant startDateInstant = startDate.toInstant();
        final Instant endDateInstant = endDate.toInstant();
        final Instant currentInstant = new Date().toInstant();
        Boolean isTimelineValid = startDateInstant.isBefore(currentInstant) && currentInstant.isBefore(endDateInstant);
        if(isTimelineValid) {
            return super.isJourneyValid();
        }
        return false;
    }
}
