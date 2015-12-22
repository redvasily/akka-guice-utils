package akkaguiceutils;

import akka.actor.*;

public class GuiceExtensionId extends
        AbstractExtensionId<GuiceExtension> {

    public static GuiceExtensionId GuiceExtProvider = new GuiceExtensionId();

    public GuiceExtension createExtension(ExtendedActorSystem system) {
        return new GuiceExtension();
    }
}
