package schedulingapplication.Utilites;

//Import application data models.
import schedulingapplication.Models.Address;
import schedulingapplication.Models.AppointmentByType;
import schedulingapplication.Models.City;
import schedulingapplication.Models.Country;
import schedulingapplication.Models.Appointment;
import schedulingapplication.Models.Customer;
import schedulingapplication.Models.User;

//Import java sql libraries. 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

//Import java time libraries. 
import java.time.LocalDate;
import java.time.ZoneId;

//Import java misc. utitlies.
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentDAOImplementation implements AppointmentDAO {
    
    private static final String databaseName = "U05kjr";
    
    //Returns an ArrayList with the differnt appointment types by month. 
    @Override
    public ArrayList<ObservableList> getAppointmentTypeByMonth(ArrayList<ObservableList> passedDataList, String passedYear) {
        System.out.print(passedYear);
        //SQL Query for all appointments by type and month.
        String getAppointmentTypeByMonthQuery = 
                "select MONTH(start) as month, type, count(*) as count from appointment "
                + "where YEAR(start) = ? "
                + "group by month, type;";

        //Create a database connection and getAppointmentsByWeekQuery appointment table with sql getAppointmentsByWeekQuery. 
        ApplicationDatabaseConnector databaseConnectionInstance = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnectionInstance.establishDatabaseConnection()) {
            
            //Create a precompiled SQL statement to pass getAppointmentsByWeekQuery to database.
            PreparedStatement getAppointmentsByMonthQuery = databaseConnection.prepareStatement(getAppointmentTypeByMonthQuery);
            getAppointmentsByMonthQuery.setString(1, passedYear);
            
            //Create a resultset variable to store results from exacuted getAppointmentsByWeekQuery statement.
            ResultSet queryResultSet = getAppointmentsByMonthQuery.executeQuery();
            
            if (!queryResultSet.isBeforeFirst()) {
                return null;
            } else {
                //While loop to cycle thorugh each returned apppointmentQueryResults. 
                while (queryResultSet.next()) {
                    //For reach type create a new vairble that stores the total count and type. 
                    AppointmentByType appointmentTypeData = new AppointmentByType();
                    appointmentTypeData.setAppointmentTotal(queryResultSet.getInt("count"));
                    appointmentTypeData.setAppointmentType(queryResultSet.getString("type"));
                    //Append the passed datalist with the appointmentType data. 
                    passedDataList.get((queryResultSet.getInt("month") - 1)).add(appointmentTypeData);
                }
            }
        } catch (SQLException excpetion) {
            excpetion.printStackTrace(System.out);
        }
        return passedDataList;
    }
      
    //Returns an Obseravble list with appointments by Month.
    @Override
    public ObservableList<Appointment> getAppointmentsByMonth(LocalDate passedDate) {
        //SQL Query for all appointments by type and month.
        
        String getAppointmentByMonthQuery = 
                "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate,  us.lastUpdate as userLastUpd, us.lastUpdateBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + databaseName  + ".appointment ap "
                + "inner join " + databaseName  + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + databaseName  + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + databaseName  + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + databaseName  + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + databaseName  + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.start like '" + passedDate.getYear() + "-" + getMonthFromDate(passedDate) + "%'"
                + "order by ap.start;";

        ObservableList<Appointment> apppointmentQueryResults = getAppointments(getAppointmentByMonthQuery);
        return apppointmentQueryResults;
    }

    //Returns an Observable list with appointsments by week. 
    @Override
    public ObservableList<Appointment> getAppointmentsByWeek(LocalDate passedDate) {
        String getAppointmentsByWeekQuery = 
                "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.lastUpdate as userLastUpd, us.lastUpdateBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " 
                + databaseName  
                + ".appointment ap "
                + "inner join " 
                + databaseName  
                + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " 
                + databaseName 
                + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " 
                + databaseName  
                + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " 
                + databaseName  
                + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " 
                + databaseName  
                + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.start >= '" 
                + passedDate + "'"
                + "and ap.start <= '" 
                + passedDate.plusDays(6) 
                + "'"
                + "order by ap.start;";

        ObservableList<Appointment> apppointmentQueryResults = getAppointments(getAppointmentsByWeekQuery);
        return apppointmentQueryResults;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByDay(LocalDate passedDate) {
        String getAppointmentsByDayQuery = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate,  us.lastUpdate as userLastUpd, us.lastUpdateBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " 
                + databaseName 
                + ".appointment ap "
                + "inner join " 
                + databaseName  
                + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " 
                + databaseName  
                + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " 
                + databaseName  
                + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join "
                + databaseName  
                + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " 
                + databaseName  
                + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.start >= '" 
                + passedDate 
                + "'"
                + "and ap.start <= '" 
                + passedDate.plusDays(1) 
                + "'"
                + "order by ap.start;";

        ObservableList<Appointment> apppointmentQueryResults = getAppointments(getAppointmentsByDayQuery);
        return apppointmentQueryResults;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByUser(User passedUser) {
        String query = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.lastUpdate as userLastUpd, us.lastUpdateBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " 
                + databaseName  
                + ".appointment ap "
                + "inner join " 
                + databaseName  
                + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " 
                + databaseName 
                + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " 
                + databaseName  
                + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " 
                + databaseName 
                + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " 
                + databaseName  
                + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.userId = " 
                + passedUser.getUserId() + " "
                + "order by ap.start;";

        ObservableList<Appointment> apppointmentQueryResults = getAppointments(query);
        return apppointmentQueryResults;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByCustomer(Customer passedCustomer) {
        String getAppointmentsByCustomerQuery = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.lastUpdate as userLastUpd, us.lastUpdateBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + databaseName  + ".appointment ap "
                + "inner join " + databaseName  + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + databaseName  + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + databaseName  + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + databaseName  + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + databaseName  + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.customerId = " + passedCustomer.getCustomerId() + " "
                + "order by ap.start;";

        ObservableList<Appointment> apppointmentQueryResults = getAppointments(getAppointmentsByCustomerQuery);
        return apppointmentQueryResults;
    }

    private ObservableList<Appointment> getAppointments(String passedQuery) {
        ObservableList<Appointment> queryResult = FXCollections.observableArrayList();
        ApplicationDatabaseConnector databaseConnectionInstance = ApplicationDatabaseConnector.getConnectionInstance();
        try {
            Connection databaseConnection = databaseConnectionInstance.establishDatabaseConnection();
            PreparedStatement getAppointmentsQuery = databaseConnection.prepareStatement(passedQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet queryResultSet = getAppointmentsQuery.executeQuery();
            while (queryResultSet.next()) {
                User user = new User();
                Country country = new Country();
                City city = new City();
                Address address = new Address();
                Customer customer = new Customer();
                Appointment appointment = new Appointment();

                user.setUserId(queryResultSet.getInt("userId"));
                user.setUserName(queryResultSet.getString("userName"));
                user.setPassword(queryResultSet.getString("userPwd"));
                user.setActive(queryResultSet.getInt("userActive"));
                user.setCreateDate(queryResultSet.getDate("userCreate").toLocalDate());
                user.setLastUpdate(queryResultSet.getTimestamp("userLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                user.setLastUpdateBy(queryResultSet.getString("userLastUpdBy"));

                country.setCountryId(queryResultSet.getInt("coId"));
                country.setCountry(queryResultSet.getString("coCountry"));
                country.setCreateDate(queryResultSet.getDate("coCreate").toLocalDate());
                country.setCreatedBy(queryResultSet.getString("coCreatedBy"));
                country.setLastUpdate(queryResultSet.getTimestamp("coLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                country.setLastUpdateBy(queryResultSet.getString("coLastUpdBy"));

                city.setCityId(queryResultSet.getInt("cityId"));
                city.setCity(queryResultSet.getString("cityCity"));
                city.setCountry(country);
                city.setCreateDate(queryResultSet.getDate("cityCreate").toLocalDate());
                city.setCreatedBy(queryResultSet.getString("cityCreatedBy"));
                city.setLastUpdate(queryResultSet.getTimestamp("cityLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                city.setLastUpdateBy(queryResultSet.getString("cityLastUpdBy"));

                address.setAddressId(queryResultSet.getInt("addId"));
                address.setAddress(queryResultSet.getString("addAdd1"));
                address.setAddress2(queryResultSet.getString("addAdd2"));
                address.setCity(city);
                address.setPostalCode(queryResultSet.getString("addPostal"));
                address.setPhone(queryResultSet.getString("addPhone"));
                address.setCreateDate(queryResultSet.getDate("addCreate").toLocalDate());
                address.setCreatedBy(queryResultSet.getString("addCreatedBy"));
                address.setLastUpdate(queryResultSet.getTimestamp("addLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                address.setLastUpdateBy(queryResultSet.getString("addLastUpdBy"));

                customer.setCustomerId(queryResultSet.getInt("custId"));
                customer.setCustomerName(queryResultSet.getString("custName"));
                customer.setAddress(address);
                customer.setActive(queryResultSet.getInt("custActive"));
                customer.setCreateDate(queryResultSet.getDate("custCreate").toLocalDate());
                customer.setCreatedBy(queryResultSet.getString("custCreatedBy"));
                customer.setLastUpdate(queryResultSet.getTimestamp("custLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                customer.setLastUpdateBy(queryResultSet.getString("custLastUpdBy"));
                
                appointment.setAppointmentId(queryResultSet.getInt("aptId"));
                appointment.setCustomer(customer);
                appointment.setUser(user);
                appointment.setTitle(queryResultSet.getString("aptTitle"));
                appointment.setDescription(queryResultSet.getString("aptDesc"));
                appointment.setLocation(queryResultSet.getString("aptLoc"));
                appointment.setContact(queryResultSet.getString("aptCon"));
                appointment.setType(queryResultSet.getString("aptType"));
                appointment.setUrl(queryResultSet.getString("aptUrl"));
                appointment.setStart(queryResultSet.getTimestamp("aptStart").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                appointment.setEnd(queryResultSet.getTimestamp("aptEnd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                appointment.setCreateDate(queryResultSet.getDate("aptCreate").toLocalDate());
                appointment.setCreatedBy(queryResultSet.getString("aptCreatedBy"));
                appointment.setLastUpdate(queryResultSet.getTimestamp("aptLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                appointment.setLastUpdateBy(queryResultSet.getString("aptLastUpdBy"));

                queryResult.add(appointment);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            databaseConnectionInstance.closeDatabaseConnection();
        }

        return queryResult;
    }

    @Override
    public boolean updateAppointment(Appointment passedAppointment, Appointment passedAppointmentForDeletion) {
        boolean updateResult = false; 
        updateResult = deleteAppointment(passedAppointmentForDeletion);
        if(updateResult){
            updateResult = addAppointment(passedAppointment);
        }
        return updateResult;
    }

    @Override
    public boolean deleteAppointment(Appointment passedAppointment) {
        boolean result = false;
        String query = 
                "delete from " 
                + databaseName   
                + ".appointment where appointmentid = ?;";
        
        ApplicationDatabaseConnector databaseConnectionInstance = ApplicationDatabaseConnector.getConnectionInstance();
        try {
            Connection databaseConnection = databaseConnectionInstance.establishDatabaseConnection();
            PreparedStatement deleteAppointmentQuery = databaseConnection.prepareStatement(query);
            deleteAppointmentQuery.setInt(1, passedAppointment.getAppointmentId());
            int indicator = deleteAppointmentQuery.executeUpdate();
            result = indicator > 0;
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        } finally {
            databaseConnectionInstance.closeDatabaseConnection();
        }

        return result;
    }

    @Override
    public boolean addAppointment(Appointment passedAppointment) {
        boolean result = false;
        String query = "insert into " 
                + databaseName 
                + ".appointment "
                + "(customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy, userid, type) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        ApplicationDatabaseConnector databaseConnectionInstance = ApplicationDatabaseConnector.getConnectionInstance();
        try {
            Connection databaseConnection = databaseConnectionInstance.establishDatabaseConnection();
            PreparedStatement addAppointmentQuery = databaseConnection.prepareStatement(query);
            addAppointmentQuery.setInt(1, passedAppointment.getCustomer().getCustomerId());
            addAppointmentQuery.setString(2, passedAppointment.getTitle());
            addAppointmentQuery.setString(3, passedAppointment.getDescription());
            addAppointmentQuery.setString(4, passedAppointment.getLocation());
            addAppointmentQuery.setString(5, passedAppointment.getContact());
            addAppointmentQuery.setString(6, passedAppointment.getUrl());
            addAppointmentQuery.setTimestamp(7, Timestamp.valueOf(passedAppointment.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            addAppointmentQuery.setTimestamp(8, Timestamp.valueOf(passedAppointment.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            addAppointmentQuery.setDate(9, Date.valueOf(passedAppointment.getCreateDate()));
            addAppointmentQuery.setString(10, passedAppointment.getCreatedBy());
            addAppointmentQuery.setTimestamp(11, Timestamp.valueOf(passedAppointment.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            addAppointmentQuery.setString(12, passedAppointment.getLastUpdateBy());
            addAppointmentQuery.setInt(13, passedAppointment.getUser().getUserId());
            addAppointmentQuery.setString(14, passedAppointment.getType());

            int indicator = addAppointmentQuery.executeUpdate();
            result = indicator > 0;
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        } finally {
            databaseConnectionInstance.closeDatabaseConnection();
        }
        return result;
    }

    @Override
    public ObservableList<String> getYearList() {
        String query = "select distinct YEAR(start) as year from appointment order by year desc;";

        ObservableList<String> yearList = FXCollections.observableArrayList();
        ApplicationDatabaseConnector databaseConnectionInstance = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnectionInstance.establishDatabaseConnection()) {
            PreparedStatement getYearListQuery = databaseConnection.prepareStatement(query);
            ResultSet getYearListResultSet = getYearListQuery.executeQuery();
            while (getYearListResultSet.next()) {
                yearList.add(getYearListResultSet.getString("year"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }

        return yearList;
    }

    private String getMonthFromDate(LocalDate passedDate) {
        int candidate = passedDate.getMonthValue();
        if (candidate < 10) {
            return "0" + candidate;
        } else {
            return String.valueOf(candidate);
        }
    }
}
