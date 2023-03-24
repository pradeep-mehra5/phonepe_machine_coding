package com.pradeep.journeytracker.service;

import com.pradeep.journeytracker.exception.InvalidJourneyException;
import com.pradeep.journeytracker.exception.InvalidUserException;
import com.pradeep.journeytracker.model.*;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class UserJourneyService {


    // map from user id to set of journeys
    private final Map<User, List<UserJourney>> userJourneys;

    public UserJourneyService() {
        this.userJourneys = new HashMap<>();
    }

    public Boolean isUserInJourney(@NonNull final User user, @NonNull final Journey journey) {
        return userJourneys.get(user)
                .stream()
                .map(UserJourney::getJourney)
                .collect(Collectors.toSet())
                .contains(journey);
    }

    public Stage getCurrentStageForUser(@NonNull final User user, @NonNull final Journey journey) {
        if(!userJourneys.containsKey(user)) {
            throw new InvalidUserException("User not present in any journey");
        }
        return userJourneys.get(user)
                .stream()
                .filter((uj) -> uj.getJourney().equals(journey))
                .map(UserJourney::getStage)
                .findFirst()
                .get();
    }

    public void createUserJourney(@NonNull final User user, @NonNull final Journey journey) {
        if(!JourneyState.ACTIVE.equals(journey.getJourneyState())) {
            throw new InvalidJourneyException("onboarding not supported in non active journey");
        }
        final UserJourney userJourney = new UserJourney(user, journey, journey.getStartStage());
        if(Objects.isNull(userJourneys.get(user))) {
            userJourneys.put(user, new ArrayList<>());
        }
        userJourneys.get(user).add(userJourney);
    }

    public List<UserJourney> getUserJourneys(@NonNull final User user) {
        if(!userJourneys.containsKey(user)) {
            throw new InvalidUserException("User not present in any journey");
        }
        return userJourneys.get(user);
    }

    public void makeTransitionForUser(@NonNull final User user, @NonNull final Event event) {
        List<UserJourney> uj = userJourneys.get(user);
        for(UserJourney userJourney : uj) {
            Stage nextStage = userJourney.getStage().nextStage(event);
            if(!Objects.isNull(nextStage)) {
                System.out.println("transition found for user in journey id : " + userJourney.getJourney().getId());
                userJourney.setStage(nextStage);
                return;
            }
        }
        System.out.println("no transition found for user in active journeys");
    }
}
