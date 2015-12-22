package akkaguiceutils;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.function.Function;

public interface GuiceUtils extends Actor {
    default LoggingAdapter logger() {
        return Logging.getLogger(this.system(), this);
    }

    default ActorSystem system() {
        return context().system();
    }

    default <T extends Actor> Props props(Class<T> actorClass) {
        return GuiceExtension.get(system()).props(actorClass);
    }

    default <A extends Actor, F> Props props(
            Class<A> actorClass, Class<F> factoryClass,
            Function<F, A> factoryFunction) {
        return GuiceExtension.get(system()).props(actorClass, factoryClass, factoryFunction);
    }

}
