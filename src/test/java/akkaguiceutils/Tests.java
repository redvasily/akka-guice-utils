package akkaguiceutils;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class Tests {
    private ActorSystem actorSystem;

    @Before
    public void before() {
        Injector injector = Guice.createInjector(new TestModule());
        actorSystem = injector.getInstance(ActorSystem.class);
    }

    @After
    public void after() {
        actorSystem.terminate();
    }

    @Test
    public void propsCreation() {
        assertThat(GuiceExtension.get(actorSystem).props(DummyActor.class))
                .isInstanceOf(Props.class);
    }

    @Test
    public void basicCreation() throws Exception {
        ActorRef actor = actorSystem.actorOf(
                GuiceExtension.get(actorSystem).props(AdditionActor.class), "basic");

        Timeout timeout = new Timeout(Duration.create(100, TimeUnit.MILLISECONDS));
        Future<Object> future = Patterns.ask(actor, new AdditionRequest(1, 2), timeout);
        Integer result = (Integer) Await.result(future, timeout.duration());
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void parameterizedCreation() throws Exception {
        ActorRef actor = actorSystem.actorOf(
                GuiceExtension.get(actorSystem)
                        .props(ParameterizedAdditionActor.class,
                                ParameterizedAdditionActor.Factory.class,
                                factory -> factory.create(1)),
                "parameterized");

        Timeout timeout = new Timeout(Duration.create(100, TimeUnit.MILLISECONDS));
        Future<Object> future = Patterns.ask(actor, 3, timeout);
        Integer result = (Integer) Await.result(future, timeout.duration());
        assertThat(result).isEqualTo(4);
    }
}
