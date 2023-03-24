import com.pradeep.journeytracker.api.JourneyController;
import com.pradeep.journeytracker.api.UserController;
import com.pradeep.journeytracker.model.Event;
import com.pradeep.journeytracker.model.Journey;
import com.pradeep.journeytracker.model.Stage;
import com.pradeep.journeytracker.model.TimeBoundJourney;
import com.pradeep.journeytracker.service.JourneyService;
import com.pradeep.journeytracker.service.UserJourneyService;
import com.pradeep.journeytracker.service.UserService;

import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        // initialising services
        final JourneyService journeyService = new JourneyService();
        final UserService userService = new UserService();
        final UserJourneyService userJourneyService = new UserJourneyService();

        // initialising api methods
        final JourneyController journeyController = new JourneyController(journeyService);
        final UserController userController = new UserController(userService, journeyService, userJourneyService);

        // creating a demo journey
        final Date currentDate = new Date();
        Date tomorrowDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(tomorrowDate);
        c.add(Calendar.DATE, 1);
        tomorrowDate = c.getTime();
        final Journey timeBoundJourney = TimeBoundJourney
                .builder()
                .id("journey1")
                .build();

        // creating demo stage
        final Stage stageA = new Stage("stage1", "USER_REGISTRATION_COMPLETE", timeBoundJourney);
        final Stage stageB = new Stage("stage1", "USER_OPENED_RECHARGE_PAGE", timeBoundJourney);
        final Stage stageC = new Stage("stage1", "USER_SELECTING_OPERATOR", timeBoundJourney);
        final Stage stageD = new Stage("stage1", "USER_SELECTING_PLAN", timeBoundJourney);
        final Stage stageE = new Stage("stage1", "USER_MADE_PAYMENT", timeBoundJourney);

        // creating path from stages
        stageA.addNextStage(Event.USER_REGISTRATION_COMPLETE, stageB);
        stageB.addNextStage(Event.USER_OPENED_RECHARGE_PAGE, stageC);
        stageB.addNextStage(Event.USER_SELECTING_OPERATOR, stageD);
        stageB.addNextStage(Event.USER_SELECTING_PLAN, stageE);

        timeBoundJourney.setStartStage(stageA);

        // optional features :
        //1. Provide a way where we can send SMS to a user as soon as any stage transition happens for that user
        // (onboarding to the journey or moving from one stage to another).
        // It should have the capability to decide for which stage we want to provide this feature.
        // TODO : Use observer pattern here

        //2. Add support for recurring journey, where a single user can onboard the journey multiple times.
        // TODO :
    }
}