package akkaguiceutils;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Injector;

public class GuiceActorProducer<T extends Actor> implements IndirectActorProducer {
    private final Injector injector;
    private final Class<T> actorClass;

    public GuiceActorProducer(Injector injector, Class<T> actorClass) {
        this.injector = injector;
        this.actorClass = actorClass;
    }

    public Actor produce() {
        return injector.getInstance(actorClass);
    }

    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}
