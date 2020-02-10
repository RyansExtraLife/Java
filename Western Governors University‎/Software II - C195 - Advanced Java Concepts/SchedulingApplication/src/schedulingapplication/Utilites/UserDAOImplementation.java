package schedulingapplication.Utilites;

//Import Utiltiy libraries. 
import schedulingapplication.Models.User;
import java.time.ZoneId;

//Import SQL libraries. 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Import JavaFX collections libraries. 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAOImplementation implements UserDAO {
    //Databasename
    private static final String databaseName = "U05kjr";
    
    @Override
    public User getUser(String passedUsername, String passedPassword) throws InvalidUserCredentialsException {
        
        User userQueryResult = new User();
    
        //SQLQuery string. 
        String sqlQueryString = 
                "select userid, userName, password, active, createDate, lastUpdate, lastUpdateBy from "
                + databaseName
                + ".user where userName = ? and password = ?";
        
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try {
            Connection databaseConnection = databaseConnector.establishDatabaseConnection();
            ApplicationLogger.logUserAction("System Log", "Establishing connection with database.");
            PreparedStatement userSQLQuery = databaseConnection.prepareStatement(sqlQueryString);
            userSQLQuery.setString(1, passedUsername);
            userSQLQuery.setString(2, passedPassword);
            ResultSet userQueryResults = userSQLQuery.executeQuery();
            
            if (!userQueryResults.isBeforeFirst()) {
                ApplicationLogger.logUserLoginAttempt(passedUsername, true);
                throw new InvalidUserCredentialsException();
            } else {
                userQueryResults.first();
                if (userQueryResults.getInt("active") == 1) {
                    userQueryResult.setUserId(userQueryResults.getInt("userid"));
                    userQueryResult.setUserName(userQueryResults.getString("userName"));
                    userQueryResult.setPassword(userQueryResults.getString("password"));
                    userQueryResult.setActive(userQueryResults.getInt("active"));
                    userQueryResult.setCreateDate(userQueryResults.getDate("createDate").toLocalDate());
                    userQueryResult.setLastUpdate(userQueryResults.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                    userQueryResult.setLastUpdateBy(userQueryResults.getString("lastUpdateBy"));
                } else {
                    ApplicationLogger.logUserLoginAttempt(passedUsername, false);
                    throw new InvalidUserCredentialsException();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        } finally {
                databaseConnector.closeDatabaseConnection();
                ApplicationLogger.logUserAction("System Log", "Disconnecting with database.");
        }
        return userQueryResult;
    }
    
    @Override
    public ObservableList<User> getActiveUserSerssions() {
        ObservableList<User> currentUserSessions = FXCollections.observableArrayList();
        
        //SQLQuery string. 
        String sqlQueryString = 
                "select userid, userName, password, active,  createDate, lastUpdate, lastUpdateBy "
                + "from " 
                + databaseName
                + ".user where active = ?;";
        
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try {
            Connection databaseConnection = databaseConnector.establishDatabaseConnection();
            ApplicationLogger.logUserAction("System Log", "Establishing connection with database.");
            PreparedStatement userSQLQuery = databaseConnection.prepareStatement(sqlQueryString);
            userSQLQuery.setInt(1, 1);
            ResultSet userQueryResults = userSQLQuery.executeQuery();
            while (userQueryResults.next()) {
                User currentUser = new User();
                currentUser.setUserId(userQueryResults.getInt("userId"));
                currentUser.setUserName(userQueryResults.getString("userName"));
                currentUser.setPassword(userQueryResults.getString("password"));
                currentUser.setActive(userQueryResults.getInt("active"));
                currentUser.setCreateDate(userQueryResults.getDate("createDate").toLocalDate());
                currentUser.setLastUpdate(userQueryResults.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                currentUser.setLastUpdateBy(userQueryResults.getString("lastUpdateBy"));
                currentUserSessions.add(currentUser);
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        } finally {
             databaseConnector.closeDatabaseConnection();
             ApplicationLogger.logUserAction("System Log", "Disconnecting with database.");
        }
        return currentUserSessions;
    }
}
    
