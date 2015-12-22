package akkaguiceutils;

import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;

public class DummyActor extends UntypedActor implements GuiceUtils {
    private final LoggingAdapter logger = logger();

    @Override
    public void preStart() throws InterruptedException {
        logger.info("Starting");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        logger.info("Unhandled message: {}", message);
        unhandled(message);
    }
}
