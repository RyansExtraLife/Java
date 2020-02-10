package schedulingapplication.Utilites;

//Import Utiltiy libraries. 
import schedulingapplication.Models.Address;
import schedulingapplication.Models.City;
import schedulingapplication.Models.Country;
import schedulingapplication.Models.Customer;
import java.time.ZoneId;

//Import SQL libraries.
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

//Import JavaFX collections libraries. 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CustomerDAOImplementation implements CustomerDAO {
    
    //Varible that stores database name.
    private static final String databaseName = "U05kjr";
    
    @Override
    public boolean addCustomer(Customer passedCustomer) {
        boolean querySucess = false;
        
        //Calls testCountry and returns a coutnry ID
        int countryId = testCountry(passedCustomer.getAddress().getCity().getCountry());
        passedCustomer.getAddress().getCity().getCountry().setCountryId(countryId);
        
        //Calls testCity and returns a city ID
        int cityId = testCity(passedCustomer.getAddress().getCity());   
        passedCustomer.getAddress().getCity().setCityId(cityId);   
        
        //Calls testAddress and returns a city ID
        int addressId = testAddress(passedCustomer.getAddress());   
        passedCustomer.getAddress().setAddressId(addressId);

        //SQL String to add customer to database. 
        String sqlQueryString = 
                "insert into " 
                + databaseName
                + ".customer "
                + "(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
                + "values (?, ?, ?, ?, ?, ?, ?);";

        //Connect to database and execute query.
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnector.establishDatabaseConnection()) {
            PreparedStatement addCustomerSQLQuery = databaseConnection.prepareStatement(sqlQueryString);
            
            addCustomerSQLQuery.setString(1, passedCustomer.getCustomerName());
            addCustomerSQLQuery.setInt(2, addressId);
            addCustomerSQLQuery.setInt(3, passedCustomer.getActive());
            addCustomerSQLQuery.setDate(4, Date.valueOf(passedCustomer.getCreateDate()));
            addCustomerSQLQuery.setString(5, passedCustomer.getCreatedBy());
            addCustomerSQLQuery.setTimestamp(6, Timestamp.valueOf(passedCustomer.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            addCustomerSQLQuery.setString(7, passedCustomer.getLastUpdateBy());

            int querySuccessResult = addCustomerSQLQuery.executeUpdate();
            querySucess = querySuccessResult > 0;
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }
        return querySucess;
    }

    @Override
    public boolean updateCustomer(Customer passedCustomer) {
        boolean querySucess = false;
        
        //Calls testCountry and returns a coutnry ID
        int countryId = testCountry(passedCustomer.getAddress().getCity().getCountry());
        passedCustomer.getAddress().getCity().getCountry().setCountryId(countryId);
        
        //Calls testCity and returns a city ID
        int cityId = testCity(passedCustomer.getAddress().getCity());
        passedCustomer.getAddress().getCity().setCityId(cityId);
        
        //Calls testAddress and returns a city ID
        int addressId = testAddress(passedCustomer.getAddress());
        passedCustomer.getAddress().setAddressId(addressId);

        //SQL String used to update a customer in database. 
        String sqlQueryString = 
                "update " 
                + databaseName  
                + ".customer set "
                + "customerName = ?, "
                + "addressId = ?, "
                + "active = ?, "
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "where customerId = ?;";
        
        //Connect to database and execute query.
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnector.establishDatabaseConnection()) {
            PreparedStatement updateCustomerSQLQuery = databaseConnection.prepareStatement(sqlQueryString);
            updateCustomerSQLQuery.setString(1, passedCustomer.getCustomerName()); // customerName
            updateCustomerSQLQuery.setInt(2, passedCustomer.getAddress().getAddressId()); // addressId
            updateCustomerSQLQuery.setInt(3, passedCustomer.getActive()); // active
            updateCustomerSQLQuery.setTimestamp(4, Timestamp.valueOf(passedCustomer.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));// lastUpdate
            updateCustomerSQLQuery.setString(5, passedCustomer.getLastUpdateBy());// lastUpdateBy
            updateCustomerSQLQuery.setInt(6, passedCustomer.getCustomerId());

            int querySuccessResult = updateCustomerSQLQuery.executeUpdate();
            querySucess = querySuccessResult > 0;
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }
        return querySucess;
    }

    @Override
    public boolean deleteCustomer(Customer passedCustomer) {
        boolean querySucess = false;
        
        //SQL String used to delete a customer in database.
        String sqlQueryString = 
                "delete from " 
                + databaseName 
                + ".customer where customerid = ?;";
        
        //Connect to database and execute query.
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnector.establishDatabaseConnection()) {
            PreparedStatement deleteCustomerQuery = databaseConnection.prepareStatement(sqlQueryString);
            deleteCustomerQuery.setInt(1, passedCustomer.getCustomerId());
            
            int querySuccessResult = deleteCustomerQuery.executeUpdate();
            querySucess = querySuccessResult > 0;
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }
        return querySucess;
    }
      
    @Override
    public ObservableList<Customer> getActiveCustomers() {
        
        //SQL String used to get all active cutsomers in the database.
        String sqlQueryString = 
                "select cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " 
                + databaseName 
                + ".customer cu "
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
                + "on ci.countryId = co.countryId "
                + "where cu.active = 1;";

        //Call getCustomers and assign to ObservableList.
        ObservableList<Customer> activeCustomers = getCustomers(sqlQueryString);
        return activeCustomers;
    }
    
    @Override
    public ObservableList<Customer> getAllCustomers() {
        
        //SQL String used to get all active cutsomers in the database.
        String sqlQueryString = 
                "select cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " 
                + databaseName 
                + ".customer cu "
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
                + "on ci.countryId = co.countryId;";

        //Call getCustomers and assign to ObservableList.
        ObservableList<Customer> customers = getCustomers(sqlQueryString);
        return customers;
    }


    private ObservableList<Customer> getCustomers(String passedQuery) {
        
        ObservableList<Customer> queryResult = FXCollections.observableArrayList();
        
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try {
            Connection databaseConnection = databaseConnector.establishDatabaseConnection();
            ApplicationLogger.logUserAction("System Log", "Establishing connection with database.");
            PreparedStatement customerSQLQuery = databaseConnection.prepareStatement(passedQuery);
            ResultSet customerQueryResults = customerSQLQuery.executeQuery();
            while (customerQueryResults.next()) {
                
                Country tempCountry = new Country();
                tempCountry.setCountryId(customerQueryResults.getInt("coId"));
                tempCountry.setCountry(customerQueryResults.getString("coCountry"));
                tempCountry.setCreateDate(customerQueryResults.getDate("coCreate").toLocalDate());
                tempCountry.setCreatedBy(customerQueryResults.getString("coCreatedBy"));
                tempCountry.setLastUpdate(customerQueryResults.getTimestamp("coLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                tempCountry.setLastUpdateBy(customerQueryResults.getString("coLastUpdBy"));

                City tempCity = new City();
                tempCity.setCityId(customerQueryResults.getInt("cityId"));
                tempCity.setCity(customerQueryResults.getString("cityCity"));
                tempCity.setCountry(tempCountry);
                tempCity.setCreateDate(customerQueryResults.getDate("cityCreate").toLocalDate());
                tempCity.setCreatedBy(customerQueryResults.getString("cityCreatedBy"));
                tempCity.setLastUpdate(customerQueryResults.getTimestamp("cityLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                tempCity.setLastUpdateBy(customerQueryResults.getString("cityLastUpdBy"));

                Address tempAddress = new Address();
                tempAddress.setAddressId(customerQueryResults.getInt("addId"));
                tempAddress.setAddress(customerQueryResults.getString("addAdd1"));
                tempAddress.setAddress2(customerQueryResults.getString("addAdd2"));
                tempAddress.setCity(tempCity);
                tempAddress.setCreateDate(customerQueryResults.getDate("addCreate").toLocalDate());
                tempAddress.setCreatedBy(customerQueryResults.getString("addCreatedBy"));
                tempAddress.setLastUpdate(customerQueryResults.getTimestamp("addLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                tempAddress.setLastUpdateBy(customerQueryResults.getString("addLastUpdBy"));
                tempAddress.setPhone(customerQueryResults.getString("addPhone"));
                tempAddress.setPostalCode(customerQueryResults.getString("addPostal"));

                Customer tempCustomer = new Customer();
                tempCustomer.setCustomerId(customerQueryResults.getInt("custId"));
                tempCustomer.setCustomerName(customerQueryResults.getString("custName"));
                tempCustomer.setActive(customerQueryResults.getInt("custActive"));
                tempCustomer.setAddress(tempAddress);
                tempCustomer.setCreateDate(customerQueryResults.getDate("custCreate").toLocalDate());
                tempCustomer.setCreatedBy(customerQueryResults.getString("custCreatedBy"));
                tempCustomer.setLastUpdate(customerQueryResults.getTimestamp("custLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                tempCustomer.setLastUpdateBy(customerQueryResults.getString("custLastUpdBy"));

                queryResult.add(tempCustomer);
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        } finally {
            databaseConnector.closeDatabaseConnection();
            ApplicationLogger.logUserAction("System Log", "Disconnecting with database.");
        }
        return queryResult;
    }

    private int testAddress(Address address) {
        int addressId = -1;
        String query = "select addressid from " + databaseName + ".address where address = ? and address2 = ? and cityId = ?;";
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnector.establishDatabaseConnection()) {
            PreparedStatement testAddressQuery = databaseConnection.prepareStatement(query);
            testAddressQuery.setString(1, address.getAddress());
            testAddressQuery.setString(2, address.getAddress2());
            testAddressQuery.setInt(3, address.getCity().getCityId());
            ResultSet testAddressQuerryResult = testAddressQuery.executeQuery();
            if (testAddressQuerryResult.next()) {
                addressId = testAddressQuerryResult.getInt("addressid");
                testAddressQuerryResult.close();
                testAddressQuery.close();
            } else {
                
                String insertAddressQuery = "insert into " 
                        + databaseName 
                        + ".address "
                        + "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                
                PreparedStatement insert = databaseConnection.prepareStatement(insertAddressQuery, Statement.RETURN_GENERATED_KEYS);
                insert.setString(1, address.getAddress()); // address
                insert.setString(2, address.getAddress2()); // address2
                insert.setInt(3, address.getCity().getCityId()); // cityId
                insert.setString(4, address.getPostalCode()); // postalCode
                insert.setString(5, address.getPhone()); // phone
                insert.setDate(6, Date.valueOf(address.getCreateDate())); // createDate
                insert.setString(7, address.getCreatedBy()); // createdBy
                insert.setTimestamp(8, Timestamp.valueOf(address.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime())); // lastUpdate
                insert.setString(9, address.getLastUpdateBy()); // lastUpdateBy
                int indicator = insert.executeUpdate();
                if (indicator > 0) {
                    ResultSet generatedKeys = insert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        addressId = generatedKeys.getInt(1);
                    }
                }
                insert.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }

        return addressId;
    }

    private int testCountry(Country country) {
        int countryId = -1;
        String sqlQueryString = 
                "select countryid from " 
                + databaseName 
                + ".country where country = ?;";
        
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnector.establishDatabaseConnection()) {
            PreparedStatement testCountryQuery = databaseConnection.prepareStatement(sqlQueryString);
            testCountryQuery.setString(1, country.getCountry());
            ResultSet queryResultSet = testCountryQuery.executeQuery();
            if (queryResultSet.next()) {
                countryId = queryResultSet.getInt("countryid");
                queryResultSet.close();
                testCountryQuery.close();
            } else {
                String insertQuery = 
                        "insert into " 
                        + databaseName  
                        + ".country "
                        + "(country, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "values (?, ?, ?, ?, ?);";
               
                PreparedStatement insert = databaseConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insert.setString(1, country.getCountry()); // tempCountry
                insert.setDate(2, Date.valueOf(country.getCreateDate())); // createDate
                insert.setString(3, country.getCreatedBy()); // createdBy
                insert.setTimestamp(4, Timestamp.valueOf(country.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime())); // lastUpdate
                insert.setString(5, country.getLastUpdateBy()); // lastUpdateBy
                int indicator = insert.executeUpdate();
                if (indicator > 0) {
                    ResultSet generatedKeys = insert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        countryId = generatedKeys.getInt(1);
                    }
                }
                insert.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }

        return countryId;
    }

    private int testCity(City city) {
        int cityID = -1;
        String query = 
                "select cityid from " 
                + databaseName 
                + ".city where city = ? and countryId = ?";
        
        
        ApplicationDatabaseConnector databaseConnector = ApplicationDatabaseConnector.getConnectionInstance();
        try (Connection databaseConnection = databaseConnector.establishDatabaseConnection()) {
            PreparedStatement testCityQuery = databaseConnection.prepareStatement(query);
            
            testCityQuery.setString(1, city.getCity());
            testCityQuery.setInt(2, city.getCountry().getCountryId());
            ResultSet queryResultSet = testCityQuery.executeQuery();
            if (queryResultSet.next()) {
                cityID = queryResultSet.getInt("cityid");
                queryResultSet.close();
                testCityQuery.close();
            } else {
                String insertQuery = 
                        "insert into " 
                        + databaseName 
                        + ".city "
                        + "(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "values (?, ?, ?, ?, ?, ?);";
                
                
                PreparedStatement createCityQuery = databaseConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                createCityQuery.setString(1, city.getCity());
                createCityQuery.setInt(2, city.getCountry().getCountryId());
                createCityQuery.setDate(3, Date.valueOf(city.getCreateDate()));
                createCityQuery.setString(4, city.getCreatedBy());
                createCityQuery.setTimestamp(5, Timestamp.valueOf(city.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
                createCityQuery.setString(6, city.getLastUpdateBy());
                int indicator = createCityQuery.executeUpdate();
                if (indicator > 0) {
                    ResultSet generatedKeys = createCityQuery.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        cityID = generatedKeys.getInt(1);
                    }
                }
                createCityQuery.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }
        return cityID;
    }
}
