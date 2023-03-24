package com.pradeep.journeytracker.service;

import com.pradeep.journeytracker.exception.InvalidJourneyException;
import com.pradeep.journeytracker.model.Event;
import com.pradeep.journeytracker.model.Journey;
import com.pradeep.journeytracker.model.JourneyState;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JourneyService {

    private final Map<String, Journey> journeys;

    public JourneyService() {
        this.journeys = new HashMap<>();
    }

    public void createJourney(@NonNull final Journey journey) {
        if(journeys.containsKey(journey.getId())) {
            throw new InvalidJourneyException("A journey with same id already present in the system");
        }
        journeys.put(journey.getId(), journey);
    }

    public void updateJourneyState(@NonNull final String journeyId, @NonNull final JourneyState journeyState) {
        if(!journeys.containsKey(journeyId)) {
            throw new InvalidJourneyException("Journey not found in the system");
        }
        final Journey journey = journeys.get(journeyId);
        final JourneyState currentJourneyState = journey.getJourneyState();
        journey.setJourneyState(journeyState);
        System.out.println("Changed journey state for journey id : "+ journeyId +
                " from " + currentJourneyState + " to " + journeyState);
    }

    public Journey getJourneyById(@NonNull final String journeyId) {
        if(!journeys.containsKey(journeyId)) {
            throw new InvalidJourneyException("No journey found for the id");
        }
        return journeys.get(journeyId);
    }

    public Optional<Journey> getPossibleOnboardingJourney(@NonNull final Event event) {
        for(Map.Entry<String, Journey> journeyEntry : journeys.entrySet()) {
            if(event.equals(journeyEntry.getValue().getStartEvent())) {
                return Optional.of(journeyEntry.getValue());
            }
        }
        return Optional.empty();
    }
}
