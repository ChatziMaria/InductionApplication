package gr.knowledge.induction.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public @interface EnableEncryptableProperties {

    public class MailerApplication {
        public static void main(String[] args) {
            SpringApplication.run(MailerApplication.class, args);
        }
    }

}
