package com.pradeep.journeytracker.api;

import com.pradeep.journeytracker.model.*;
import com.pradeep.journeytracker.service.JourneyService;
import com.pradeep.journeytracker.service.UserJourneyService;
import com.pradeep.journeytracker.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JourneyService journeyService;
    private final UserJourneyService userJourneyService;

    public User createUser(@NonNull final String name) {
        return this.userService.createUser(name);
    }

    public void evaluate(@NonNull final String userId, @NonNull final Payload payload) {
        final User user = this.userService.getUserById(userId);
        final Optional<Journey> journeyOptional = this.journeyService.getPossibleOnboardingJourney(payload.getEvent());

        // new onboarding is possible
        if(journeyOptional.isPresent()) {
            this.userJourneyService.createUserJourney(user, journeyOptional.get());
            return;
        }

        // check for progress in any existing journey
        this.userJourneyService.makeTransitionForUser(user, payload.getEvent());
    }

    public Stage getCurrentStage(@NonNull final String userId, @NonNull final String journeyId) {
        final User user = this.userService.getUserById(userId);
        final Journey journey = this.journeyService.getJourneyById(journeyId);
        return this.userJourneyService.getCurrentStageForUser(user, journey);
    }

    public Boolean isOnboarded(@NonNull final String userId, @NonNull final String journeyId) {
        final Journey journey = this.journeyService.getJourneyById(journeyId);
        final User user = this.userService.getUserById(userId);
        return this.userJourneyService.isUserInJourney(user, journey);
    }
}
