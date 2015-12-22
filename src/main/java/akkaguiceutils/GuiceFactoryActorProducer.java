package akkaguiceutils;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Injector;

import java.util.function.Function;

public class GuiceFactoryActorProducer<A extends Actor, F>
        implements IndirectActorProducer {
    private final Injector injector;
    private final Class<A> actorClass;
    private final Class<F> factoryClass;
    private final Function<F, A> factoryFunction;

    public GuiceFactoryActorProducer(
            Injector injector, Class<A> actorClass,
            Class<F> factoryClass, Function<F, A> factoryFunction) {
        this.injector = injector;
        this.actorClass = actorClass;
        this.factoryClass = factoryClass;
        this.factoryFunction = factoryFunction;
    }

    @Override
    public Actor produce() {
        F factory = injector.getInstance(factoryClass);
        return factoryFunction.apply(factory);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}
