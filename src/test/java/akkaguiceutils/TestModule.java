package akkaguiceutils;

import akka.actor.ActorSystem;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import javax.inject.Singleton;

import static akkaguiceutils.GuiceExtensionId.GuiceExtProvider;

public class TestModule extends AbstractModule {

    @Provides
    @Singleton
    ActorSystem actorSystem(Injector injector) {
        ActorSystem actorSystem = ActorSystem.create();
        GuiceExtProvider.get(actorSystem).initialize(injector);
        return actorSystem;
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .build(ParameterizedAdditionActor.Factory.class));
    }
}
