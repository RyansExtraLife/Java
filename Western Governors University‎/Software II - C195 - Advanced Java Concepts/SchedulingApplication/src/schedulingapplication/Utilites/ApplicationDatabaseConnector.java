package schedulingapplication.Utilites;

//Import Java SQL packages.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationDatabaseConnector {
    //MySQL WGU Database Connection String
    private static final String applicationDatabaseName = "U05kjr";
    private static final String appliationDatabaseURL = "jdbc:mysql://52.206.157.109/" + applicationDatabaseName;
    private static final String databaseUsername = "U05kjr";
    private static final String databasePassword = "53688530797";
    private static final String databaseDriver = "com.mysql.jdbc.Driver";
    
    //Local Variables
    private static ApplicationDatabaseConnector connectionInstance;
    static Connection databaseConnection;

    //Class Constructor
    private ApplicationDatabaseConnector(){
        try{
            Class.forName(databaseDriver);
        }catch (ClassNotFoundException excpetion){
            
        }
    }
    
    //Initialize database connection instance. 
    public static ApplicationDatabaseConnector getConnectionInstance() {
        if (connectionInstance== null) {
            connectionInstance = new ApplicationDatabaseConnector();
        }
        return connectionInstance;
    }
    
    //Initizlie the connection to the database. 
    public Connection establishDatabaseConnection(){
        try{
            databaseConnection = DriverManager.getConnection(appliationDatabaseURL, databaseUsername, databasePassword);
        }catch (SQLException excpetion){
            excpetion.printStackTrace(System.out);
        }
        return databaseConnection; 
    }
    
    //Close the connection to the database. 
    public void closeDatabaseConnection(){
        try{
            databaseConnection.close();
        } catch (SQLException excpetion) {
            excpetion.printStackTrace(System.out);
        }
    }
}
