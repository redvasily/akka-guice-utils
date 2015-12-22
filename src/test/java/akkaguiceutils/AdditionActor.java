package akkaguiceutils;

import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import akka.japi.Pair;

import javax.inject.Inject;

public class AdditionActor extends UntypedActor implements GuiceUtils {
    private final LoggingAdapter logger = logger();
    private final AdditionService additionService;

    @Inject
    public AdditionActor(AdditionService additionService) {
        this.additionService = additionService;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof AdditionRequest) {
            AdditionRequest request = (AdditionRequest) message;
            sender().tell(additionService.add(request.a, request.b), self());
        } else {
            logger.info("Unhandled message: {}", message);
            unhandled(message);
        }
    }
}
