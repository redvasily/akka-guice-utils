package akkaguiceutils;

import akka.actor.AbstractActor;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class ParameterizedAdditionActor extends AbstractActor
        implements GuiceUtils {
    private final LoggingAdapter logger = logger();

    public interface Factory {
        ParameterizedAdditionActor create(int increment);
    }

    @Inject
    public ParameterizedAdditionActor(AdditionService additionService,
                                      @Assisted int increment) {
        receive(ReceiveBuilder
                .match(Integer.class, a -> {
                    sender().tell(additionService.add(a, increment), self());
                })
                .matchAny(message -> {
                    logger.info("Unhandled message: {}", message);
                    unhandled(message);
                })
                .build());
    }
}
