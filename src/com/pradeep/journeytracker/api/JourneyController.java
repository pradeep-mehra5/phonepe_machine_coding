package com.pradeep.journeytracker.api;

import com.pradeep.journeytracker.model.Journey;
import com.pradeep.journeytracker.model.JourneyState;
import com.pradeep.journeytracker.model.Event;
import com.pradeep.journeytracker.service.JourneyService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class JourneyController {

    private final JourneyService journeyService;
    public void createJourney(@NonNull final Journey journey) {
        this.journeyService.createJourney(journey);
    }

    public void updateState(@NonNull final String journeyId, @NonNull final JourneyState journeyState) {
        this.journeyService.updateJourneyState(journeyId, journeyState);
    }

    public Journey getJourney(@NonNull final String journeyId) {
        return this.journeyService.getJourneyById(journeyId);
    }
}
