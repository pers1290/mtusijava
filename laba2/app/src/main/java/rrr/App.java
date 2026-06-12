package rrr;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;
import java.io.InputStream;
import java.util.Properties;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static void readBuildPassport() {
        try (InputStream input = App.class.getClassLoader()
                .getResourceAsStream("build-passport.properties")) {

            if (input != null) {
                Properties props = new Properties();
                props.load(input);

                System.out.println("\nInfo");
                System.out.println("User: " + props.getProperty("build.user"));
                System.out.println("OS: " + props.getProperty("build.os"));
                System.out.println("Java: " + props.getProperty("build.java.version"));
                System.out.println("Time: " + props.getProperty("build.time"));
                System.out.println(props.getProperty("build.message"));
            } else {
                System.out.println("None");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        readBuildPassport();

        logger.info("PROGRAM START");

        System.out.println("Hello!");

        Scanner scanner = new Scanner(System.in, "UTF-8");
        System.out.print("Enter string: ");

        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            String reversed = StringUtils.reverse(input);
            String capitalized = StringUtils.capitalize(input);

            logger.info("Reversed: {}", reversed);
            logger.info("Capitalized: {}", capitalized);
        } else {
            logger.error("Cant read input");
        }

        logger.info("PROGRAM FINISHED");
        scanner.close();
    }
}