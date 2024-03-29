package inn;

import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ErrorLogger {
    private final Logger logger =  Logger.getLogger(getClass().getName());
        public ErrorLogger() {
            try {String errorName = "G:\\My Drive\\InnRegister\\InnRegister\\error logs\\errors.txt";
                FileHandler fileHandler = new FileHandler(errorName, true);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
                logger.addHandler(fileHandler);
           }catch (Exception ignored) {}

            }

            public void log(String message) {
                Date date = new Date();
                logger.severe(date + " - " + message);
            }



}
