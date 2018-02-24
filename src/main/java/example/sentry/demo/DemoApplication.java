package example.sentry.demo;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        MyClass logTest = new MyClass();
        logTest.aFunction();
        logTest.anotherFunction();
        logTest.usingSentryApiDirectly();
    }


    public static class MyClass {
        Logger logger = LoggerFactory.getLogger(DemoApplication.class);

        void aFunction() {
            // same non formatted message
            logger.warn("same non formatted message.");
            logger.error("same non formatted message.");
            logger.warn("same non formatted message.", new RuntimeException());
            logger.error("same non formatted message.", new RuntimeException());
            logger.warn("same non formatted message.", new RuntimeException("this is an exception"));
            logger.error("same non formatted message.", new RuntimeException("this is an exception"));
            // -------------------
            // different non formatted message with and without exception
            logger.error("different formatted message.");
            logger.error("different formatted message.", new RuntimeException());
            // -------------------
            // same formatted message with different parameters
            logger.error("same formatted message with {}.", "parameter");
            logger.error("same formatted message with {}.", "parameter", new RuntimeException());
            // -------------------
            // different formatted message with different parameters
            logger.error("just a formatted message with {}.", "this parameter");
            logger.error("just another formatted message with {}.", "that parameter", new RuntimeException());
            logger.error("just another formatted message with {}.", "that parameter", new IllegalArgumentException());

        }

        void anotherFunction() {
            logger.error("same non formatted message");
            logger.error("same non formatted message.", new RuntimeException());
        }

        void usingSentryApiDirectly() {
            //SentryClient sentry = SentryClientFactory.sentryClient();
//            SentryClient sentry =
//                    Sentry.init("https://5aa7dfda13a442ebad73b13301669cad:69161009f1d047e59a439c68d9e7f864@sentry.io/290041?http.proxy.host=http.proxy.pegahtech.ir&http.proxy.port=9119");
//            sentry.sendMessage("this is test");
//            sentry.sendException(new RuntimeException("test sentry exception"));

            Sentry.capture(new EventBuilder().build());
            Sentry.capture(new EventBuilder().withLevel(Event.Level.ERROR).withMessage("Using Sentry SDK: a message"));
            Sentry.capture(new EventBuilder().withLevel(Event.Level.ERROR).withMessage("Using Sentry SDK: another message"));
            Sentry.capture(new EventBuilder().withLevel(Event.Level.ERROR).withMessage("Using Sentry SDK: a message").withSentryInterface(new ExceptionInterface(new RuntimeException())));
            Sentry.capture(new EventBuilder().withLevel(Event.Level.ERROR).withSentryInterface(new ExceptionInterface(new RuntimeException())));
            Sentry.capture(new EventBuilder().withLevel(Event.Level.ERROR).withSentryInterface(new ExceptionInterface(new IllegalArgumentException("this is an exception"))));
            Sentry.capture(new EventBuilder().withLevel(Event.Level.ERROR).withSentryInterface(new ExceptionInterface(new IllegalArgumentException("this is another exception"))));
        }
    }
}
