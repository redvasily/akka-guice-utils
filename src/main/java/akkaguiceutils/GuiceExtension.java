package akkaguiceutils;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Extension;
import akka.actor.Props;
import com.google.inject.Injector;

import java.util.function.Function;

public class GuiceExtension implements Extension {
    private volatile Injector injector;

    public void initialize(Injector injector) {
        this.injector = injector;
    }

    public <T extends Actor> Props props(Class<T> actorClass) {
        return Props.create(GuiceActorProducer.class,
                injector, actorClass);
    }

    public <A extends Actor, F> Props props(
            Class<A> actorClass,
            Class<F> factoryClass,
            Function<F, A> factoryFunction) {
        return Props.create(GuiceFactoryActorProducer.class,
                injector, actorClass, factoryClass, factoryFunction);
    }

    public static GuiceExtension get(ActorSystem system) {
        return GuiceExtensionId.GuiceExtProvider.get(system);
    }
}