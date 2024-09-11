package couponsProjectPhase2;

import couponsProjectPhase2.exceptions.EmailFormatException;
import couponsProjectPhase2.exceptions.NameException;
import couponsProjectPhase2.exceptions.NonexistantObjectException;
import couponsProjectPhase2.exceptions.PasswordFormatException;
import couponsProjectPhase2.factories.DBFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsProjectPhase2Application {

	public static void ma(String[] args) {
		ApplicationContext context =SpringApplication.run(CouponsProjectPhase2Application.class, args);
		DBFactory dbFactory = context.getBean(DBFactory.class);
        try {
            dbFactory.formatAndBuildDB();
        } catch (EmailFormatException | NameException | PasswordFormatException | NonexistantObjectException e) {
			System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CouponsProjectPhase2Application.class, args);
		Test test = context.getBean(Test.class);
        test.testAll();
    }
}
