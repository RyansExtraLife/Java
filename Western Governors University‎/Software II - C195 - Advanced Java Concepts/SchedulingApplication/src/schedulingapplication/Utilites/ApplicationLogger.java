package schedulingapplication.Utilites;

//Import I/O .ibraries. 
import java.io.BufferedWriter;
import java.io.IOException;

//Import Java file path libraries. 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//Import Java time libraries. 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationLogger {
    //Local Variables
    private static final Path APPLICATION_LOG_FILE = Paths.get("src/schedulingapplication/Logging/" + LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ".txt");
    private static final String TIMESTAMP_PATTERN = "MM/dd/yyyy hh:mm:ss:AAAAAAAA a";

    //Create a new log file for application
    private static void createNewLogFile(String passedLog) {
        try (BufferedWriter logWriter = Files.newBufferedWriter(APPLICATION_LOG_FILE, StandardOpenOption.CREATE)){
            logWriter.write(passedLog);
        } catch (IOException exception) {

        }
    }
    
    //Append the current log file with a log
    private static void appendLogFile(String passedLog) {
        try (BufferedWriter logWriter = Files.newBufferedWriter(APPLICATION_LOG_FILE, StandardOpenOption.APPEND)) {
            logWriter.write(passedLog);
        } catch (IOException exception) {

        }
    }
    
    //Append the current log file with user login attempt, or create new log to record userAction. 
    public static void logUserAction(String userName, String userAction) {
        if (Files.exists(APPLICATION_LOG_FILE)) {
            appendLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " : " + userAction + "\n");
        } else {
            createNewLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " :  " + userAction + "\n");
        }
    }
    
    //Append the current log file with user login attempt, or create new log to record userAction. 
    public static void logApplicationAction(String applicationAction) {
        if (Files.exists(APPLICATION_LOG_FILE)) {
            appendLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + "System Log" + " : " + applicationAction + "\n");
        } else {
            createNewLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + "System Log" + " :  " + applicationAction + "\n");
        }
    }
    
    //Append the current log with sucess/failure attempt at login or create a log to record action. 
    public static void logUserLoginAttempt(String userName, boolean valid) {
        if (Files.exists(APPLICATION_LOG_FILE)) {
            if (valid) {
                appendLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " : Sucessfully logged in. \n");
            } else {
                appendLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " : Failed to login with provided credentials. \n");
            }
        } else {
            if (valid) {
                createNewLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " : Successfully logged in. \n");
            } else {
                createNewLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " : Failed to login with provided credentials. \n");
            }
        }
    }
    
    //Append the current log with user signout or create a log to record action. 
    public static void logUserSignOut(String userName) {
        if (Files.exists(APPLICATION_LOG_FILE)) {
            appendLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " : Logged out of session. \n");
        } else {
            createNewLogFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + " : " + userName + " : Logged out of sesions. \n");
        }
    }
}
