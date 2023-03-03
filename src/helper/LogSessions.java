package helper;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * method to log sessions and place the logs in a login activity text file
 */

public class LogSessions {

    private static final String loginTextFile = "login_activity.txt";

    public static void recordLoginAttempt(String userName, Boolean success) throws IOException {
        try {

            BufferedWriter sessionLogger = new BufferedWriter(new FileWriter(loginTextFile, true));

            //ZonedDateTime loginTime=ZonedDateTime.now(ZoneOffset.UTC);

            String loginTimeString= LocalDateTime.now().toString();

            String successOrFail;

            if(success){
                 successOrFail="Successful";
            }
            else{
                successOrFail="Unsuccessful";

            }


            sessionLogger.append( "LOGIN ATTEMPT BY: " + userName + " at " +loginTimeString +
                    " WAS : " + successOrFail+ "\n");

            sessionLogger.flush();
            sessionLogger.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }

    }
}
