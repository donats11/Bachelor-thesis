package loginApp;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@QuarkusMain
public class Application {
    public static void main(String... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {
        Logger logger = LoggerFactory.getLogger(MyApp.class);

        @Override
        public int run(String... args) {
            logger.info("Service started");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
