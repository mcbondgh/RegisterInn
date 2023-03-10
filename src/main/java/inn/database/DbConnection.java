package inn.database;

import inn.ErrorLogger;
import inn.multiStage.MultiStages;
import inn.tableViews.*;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class DbConnection {
    public DbConnection() {}

    protected Connection CONNECTOR() throws SQLException {
        Connection driverConnection = null;
        try {
            String SERVER_NAME = "Druglord";
            String PASSWORD = "1244656800";
            String URL = "jdbc:mysql://127.0.0.1:3308/inn_register";

            driverConnection = DriverManager.getConnection(URL, SERVER_NAME, PASSWORD);
//        String URL = "jdbc:mysql://104.238.222.166:3306/kwegyira_inn_register";
//        jdbc:mysql://104.238.222.166:3306/?user=kwegyira

        }catch (SQLException ex) {
            MultiStages multiStages = new MultiStages();
//            multiStages.wrongDateTimeStage();
            ErrorLogger errorLogger = new ErrorLogger();
            errorLogger.log(ex.getLocalizedMessage());
        }

        return driverConnection;
    }

    /*
    ----------------------------------------------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------------------------------------------
     */
    MultiStages multiStagesOBJ = new MultiStages();
    ButtonType YES = ButtonType.YES;

    protected Statement stmt = null;
    protected PreparedStatement prepare = null;

    protected ResultSet result = null;



    //THIS METHOD WHEN CALL WILL RETURN ALL COLUMNS FROM THE business_info TABLE;
    public ArrayList<Object> fetchBusinessInfo(){
        ArrayList<Object> businessInfo = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM business_info;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                businessInfo.add(0,result.getString("bsi_name"));//0
                businessInfo.add(1, result.getString("alias"));//1
                businessInfo.add(2,result.getString("bsi_email"));//2
                businessInfo.add(3,result.getString("bsi_address"));//3
                businessInfo.add(4,result.getInt("bsi_number"));//4
                businessInfo.add(5,result.getInt("bsi_alt_number"));//5
                businessInfo.add(6,result.getDate("bsi_registration_date"));//6
                businessInfo.add(7,result.getInt("bsi_workers"));//7
                businessInfo.add(8, result.getBlob("hero_image"));//8
                businessInfo.add(9,result.getString("bsi_description"));//9
                businessInfo.add(10,result.getString("mng_name"));//10
                businessInfo.add(11,result.getInt("mng_number"));//11
                businessInfo.add(12, result.getString("mng_email"));//12
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessInfo;
    }

    //THIS METHOD WHEN INVOKED WILL UPDATE THE SYSTEM GENERAL FIELDS WITH THE SPECIFIED NEW DETAILS OF THE ORGANIZATION.
    public void updateBusinessInfo(@NamedArg("businessName") String bsi_name, @NamedArg("alias")String alias,  @NamedArg("businessEmail") String email, @NamedArg("businessAddress") String address, @NamedArg("bsinessNumner") int bsi_num,
                                   @NamedArg("otherNumber") int alt_num, @NamedArg("date") LocalDate date, InputStream hero_image, @NamedArg("totalWorkers") int workers, @NamedArg("description") String desc, @NamedArg("mng_name") String mng_name, @NamedArg("mng_number") int mng_number, @NamedArg("mng_email") String mng_email) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(YES);
        try{
                String updateQuery = "UPDATE business_info SET bsi_name = ?, alias = ?, bsi_email = ?, bsi_address = ?, bsi_number = ?, bsi_alt_number = ?, bsi_registration_date = ?, hero_image = ?, bsi_workers = ?, bsi_description = ?, mng_name = ?, mng_number = ?, mng_email = ?, updated_date = DEFAULT; ";
                prepare = CONNECTOR().prepareStatement(updateQuery);
                prepare.setString(1, bsi_name);
                prepare.setString(2, alias);
                prepare.setString(3, email);
                prepare.setString(4, address);
                prepare.setInt(5, bsi_num);
                prepare.setInt(6, alt_num);
                prepare.setDate(7, Date.valueOf(date));
                prepare.setBlob(8, hero_image);
                prepare.setInt(9, workers);
                prepare.setString(10, desc);
                prepare.setString(11, mng_name);
                prepare.setInt(12, mng_number);
                prepare.setString(13, mng_email);
                prepare.execute();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                multiStagesOBJ.showSuccessPrompt();
//                success.setTitle("Update Successful");
//                success.setHeaderText("PERFECT, RECORDS UPDATED SUCCESSFULLY");
//                success.showAndWait();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Exception");
                error.setHeaderText("UPDATE METHOD WAS UNABLE TO EXECUTE.");
                error.showAndWait();
                e.printStackTrace();
            }
    }



    //THIS METHOD WHEN INVOKED WILL RETURN ALL NAMES UNDER THE name COLUMN IN THE roles TABLE.
    public ObservableList<String> fetchUserRoles() throws SQLException {
        //ARRAYLIST TO STORE ALL NAMES OF roles IN THE roles TABLE e,g Admin, Receptionist, Manager etc.
        ObservableList<String> UserRoles = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT DISTINCT(name) FROM roles WHERE(is_default = 0) ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String roleName = result.getString(1);
                UserRoles.add(roleName);
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserRoles;
    }



    //THIS METHOD WHEN INVOKED TAKES A role_name AS AN ARGUMENT AND RETURNS THE id ASSOCIATED WITH SAME.
    public int fetchUserRoleID(String userRole) throws SQLException {
        int role_id = 0;
        try {
            String selectQuery = "SELECT id FROM roles WHERE(name = '"+ userRole +"');";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if (result.next()) {
                role_id = result.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role_id;
    }

    //THIS METHOD WHEN INVOKED TAKES A roleId AS AN ARGUMENT AND RETURNS THE roleName ASSOCIATED WITH SAME.
    public String fetchUserRoleName(int role_id) throws SQLException {
        String roleName = null;
        try {
            String selectQuery = "SELECT name FROM roles WHERE(id = '"+ role_id +"');";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if (result.next()) {
                roleName = result.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleName;
    }

    //FETCH EMPLOYEE NAMES ONLY FROM THE employees TABLE.
    public ObservableList<String> fetchEmployeeFullnames() {
        ObservableList<String> Employees = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT firstname, lastname FROM employees WHERE(status = 1) ORDER BY lastname ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String firstname = result.getString(1);
                String lastname = result.getString(2);
                String fullname = lastname + ' ' + firstname;
                Employees.add(fullname);
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Employees;
    }

    //THIS METHOD WHEN INVOKED TAKES A CONCATENATED (firstname + lastname) AS AN ARGUMENT AND RETURNS ALL ASSOCIATED RECORDS employees table.
    public ArrayList<Object> fetchFullEmployeeDetails(@NamedArg("Fullname")String fullname) {
        ArrayList<Object> employees = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM employees WHERE(status = 1 AND CONCAT(lastname,' ', firstname) = '"+ fullname +"');";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int id = result.getInt("id");//0
                String firstname = result.getString("firstname");//1
                String lastname = result.getString("lastname");//2
                String gender = result.getString("gender");//3
                String email = result.getString("email");//4
                String phone = result.getString("phone");//5
                String address = result.getString("digital_address");//6
                String idType = result.getString("id_type");//7
                String idNumber = result.getString("id_number");//8
                Date empDate = result.getDate("employment_date");//9
                String desig = result.getString("designation");//10
                Blob photo = result.getBlob("photo");//11
                double salary = result.getDouble("salary");//12
                int added_by = result.getInt("added_by");//13
                Timestamp updatedDate = result.getTimestamp("modified_date");//14
                Object[] values = {id, firstname, lastname, gender, email, phone, address, idType, idNumber, empDate, desig, photo, salary, added_by, updatedDate };
                for (int i = 0; i < values.length; i++) {
                        employees.add(i, values[i]);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
   }


    //FETCHES THE name COLUMN FROM THE id_type TABLE
    public ObservableList<String> fetchIdTypes() {
        ObservableList<String> idtypes = FXCollections.observableArrayList();
        try{
            String selectQuery = "SELECT DISTINCT(name) FROM id_types ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String items = result.getString(1);
                idtypes.add(items);
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idtypes;
    }

    //FETCHES THE name COLUMN FROM THE roomsCategory TABLE
    public ObservableList<RoomsCategoryData> fetchCategories() {
        ObservableList<RoomsCategoryData> categoryType = FXCollections.observableArrayList();
        try{
            String selectQuery = "SELECT * FROM roomsCategory ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int roomId = result.getInt("id");
                String categoryName = result.getString("name");
                byte status = result.getByte("status");
                double price = result.getDouble("price");

                categoryType.add(new RoomsCategoryData(roomId, categoryName, status, price));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryType;
    }

    //FETCH THE name COLUMN FROM THE designation TABLE.
    public ObservableList<String> fetchDesignation() {
        ObservableList<String> idtypes = FXCollections.observableArrayList();
        try{
            String selectQuery = "SELECT DISTINCT(name) FROM designation ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String items = result.getString(1);
                idtypes.add(items);
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idtypes;
    }

    public ArrayList<String> fetchUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        try {
            String selectQuery = "SELECT username FROM users WHERE(is_default = 0);";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                usernames.add(result.getString(1));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return usernames;
    }

    public LinkedList<Integer> fetchEmployeeId() {
        LinkedList<Integer> employeeId = new LinkedList<>();
        try {
            String selectQuery = "SELECT emp_id FROM users WHERE(is_default = 0);";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                employeeId.add(result.getInt(1));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return employeeId;
    }

    public ArrayList<Object> fetchUserLoginsDetails(String username) {
        ArrayList<Object> items = new ArrayList<>();
            try {
                String selectQuery = "SELECT * FROM users WHERE(username = '"+ username +"');";
                stmt = CONNECTOR().createStatement();
                result = stmt.executeQuery(selectQuery);
                while(result.next()) {
                    items.add(0,result.getString("username"));//0
                    items.add(1,result.getString("password"));//1
                    items.add(2,result.getInt("role_id"));//2
                    items.add(3, result.getInt("status"));//3
                }
                stmt.close();
                result.close();
                CONNECTOR().close();
            }catch (Exception e) {
                e.printStackTrace();
            }

        return items;
    }

    /** THIS METHOD WHEN CALLED RETURNS AN INTEGER OF THE TOTAL ROW COUNT OF ALL EMPLOYEES FROM THE employees Table*/
    public int countTotalEmployees() {
        int countValue = 0;
        try {
            String selectQuery = "SELECT COUNT(id) FROM employees;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                countValue += result.getInt(1);
            }
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return countValue;
    }

    /** THIS METHOD WHEN CALLED RETURNS AN INTEGER OF THE TOTAL ROW COUNT OF ONLY ACTIVE ie status=1 EMPLOYEES FROM THE employees Table*/
    public int countActiveEmployees() {
        int countValue = 0;
        try {
            String selectQuery = "SELECT COUNT(id) FROM employees WHERE(status = 1);";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                countValue += result.getInt(1);
            }
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return countValue;
    }

    /** THIS METHOD WHEN CALLED RETURNS AN INTEGER OF THE TOTAL ROW COUNT OF ONLY inactive ie status=0 EMPLOYEES FROM THE employees Table*/
    public int countInactiveEmployees() {
        int countValue = 0;
        try {
            String selectQuery = "SELECT COUNT(id) FROM employees WHERE(status = 0);";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                countValue += result.getInt(1);
            }
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return countValue;
    }


    /**
     * THIS METHOD WHEN INVOKED SHALL RETURN start_date & updated_date FROM THE
     *
     * */
    public ArrayList<Date> fetchExpiryAndUpdatedDates() {
        ArrayList<Date> outputResult = new ArrayList<>();
        try {
            String selectQuery = "SELECT expiry_date, DATE(updated_date) AS date_checker, start_date FROM activation_key;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if (result.next()) {
                outputResult.add(0, result.getDate(1));
                outputResult.add(1, result.getDate(2));
                outputResult.add( 2, result.getDate(3));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return outputResult;
    }



    //THIS METHOD WHEN INVOKED SHALL RETURN AN ARRAY CONTAINING THE CURRENT AUTHENTICATION PASSWORD FROM THE activation_password TABLE.
    public ArrayList<String> getSystemActivationPassword() {
        ArrayList<String> item =  new ArrayList<>();
        try {
            String selectQuery = "SELECT admin_key FROM activation_password;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if(result.next()) {
                item.add(result.getString(1));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    //THIS METHOD WHEN INVOKED SHALL RETURN A STRING REPRESENTATION OF THE CURRENT ACTIVATION KEY FROM THE activation_key TABLE.
    protected String getSystemActivationKey() {
        String keyValue = null;
        try {
            String selectQuery = "SELECT activation_code FROM activation_key";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if (result.next()) {
                keyValue = result.getString(1);
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return keyValue;
    }


    //THIS METHOD WHEN INVOKED SHALL RETURN ONLY ROOM NO.s FROM THE rooms TABLE.
    public ObservableList<String> fetchRoomNoOnly() throws SQLException {
        ObservableList<String> roomItems = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT roomNo FROM rooms";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                roomItems.add( result.getString("roomNo"));
            }
            stmt.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomItems;
    }



    //THIS METHOD RETURNS ALL COLUMNS FROM THE StocksCategory TABLE
    public ObservableList<StocksCategoryData> fetchStockCategories() {
        ObservableList<StocksCategoryData> ListItems = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT * FROM StocksCategory";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int id = result.getInt(1);
                String categoryName = result.getString(2);
                Date dateAdded = result.getDate(3);
                ListItems.add(new StocksCategoryData(id, categoryName, dateAdded));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListItems;
    }

    //THIS METHOD WHEN INVOKED SHALL RETURN ALL VALUES FROM THE Suppliers TABLE.
    public ObservableList<SuppliersData> fetchSuppliers() {
        ObservableList<SuppliersData> ListItems = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT * FROM Suppliers;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int id = result.getInt("id");
                int status = result.getInt("status");
                String name = result.getString("supplierName");
                String contact = result.getString("contact");
                String location = result.getString("location");
                Date date = result.getDate("DateCreated");
                ListItems.add(new SuppliersData(id, status, name, contact, location, date));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ListItems;
    }

    public ObservableList<StoresTableData.BrandsTableData> fetchProductBrands() {
        ObservableList<StoresTableData.BrandsTableData> returnStores = FXCollections.observableArrayList();
        try {
            String SelectQuery = "SELECT * FROM ProductBrand;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(SelectQuery);
            while(result.next()) {
                int id = result.getInt(1);
                String brandName = result.getString(2);
                Timestamp dateCreated  = result.getTimestamp(3);
                returnStores.add(new StoresTableData.BrandsTableData(id, brandName, dateCreated));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return  returnStores;
    }

    public ObservableList<StoresTableData> fetchStores() {
        ObservableList<StoresTableData> returnStores = FXCollections.observableArrayList();

        try {
           String SelectQuery = "SELECT * FROM stores;";
           stmt = CONNECTOR().createStatement();
           result = stmt.executeQuery(SelectQuery);
           while(result.next()) {
               int id = result.getInt(1);
               String storeName = result.getString(2);
               String description = result.getString(3);
               returnStores.add(new StoresTableData(id, storeName, description));
           }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return  returnStores;
    }

        public ObservableList<ProductsStockData> fetchProductDetails(){
        ObservableList<ProductsStockData> products = FXCollections.observableArrayList();
        try{
            String selectQuery = "SELECT * FROM ProductStock WHERE(DeleteStatus = 0)";
            stmt = CONNECTOR().createStatement();
            result  = stmt.executeQuery(selectQuery);

            while (result.next()) {
                int rowId = result.getInt(1);
                String productName = result.getString(2);
                String ProductType = result.getString(3);
                String productBrand = result.getString(4);
                String productCategory = result.getString(5);
                String productSupplier = result.getString(6);
                String notes = result.getString(7);
                Date expiryDate = result.getDate(8);
                byte storeId = result.getByte(9);
                byte activeStatus = result.getByte(10);
                byte deleteStatus = result.getByte(11);
                byte addedBy = result.getByte(12);
                Timestamp dateCreated = result.getTimestamp(13);

                Label statusValue = new Label();
                switch(activeStatus) {
                    case 0 -> {
                        statusValue.setText("Not Active");
                        statusValue.setStyle("-fx-text-fill:red");
                    }
                    case 1 -> {
                        statusValue.setStyle("-fx-text-fill:green");
                        statusValue.setText("Active");
                    }
                }
//                    String statusValue = activeStatus == 0 ? "Not Active" : "Active";
                products.add(new ProductsStockData(rowId, productName, ProductType, productBrand, productCategory, productSupplier, notes, expiryDate, storeId, statusValue, deleteStatus, addedBy, dateCreated));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return products;
    }

    //THIS METHOD WHEN INVOKED SHALL RETURN A PRODUCT id AND Product type FROM THE productStock
    //BASED ON THE PRODUCT NAME PARSED AS AN ARGUMENT.
    public ArrayList<Object> getProductIdAndType(@NamedArg("productname") String productName){
        ArrayList<Object> productValues = new ArrayList<>();
        try {
            String selectQuery = "SELECT Id, ProductType FROM ProductStock WHERE(ProductName = '"+ productName +"')";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if(result.next()) {
                int id = result.getInt("Id");
                String type = result.getString("ProductType");
                productValues.add(id);
                productValues.add(type);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return productValues;
    }

    public ArrayList<String> getProductTypeByName(@NamedArg("Product Name") String productName){
        ArrayList<String> fetchedValues = new ArrayList<>();
        try {
            String selectQuery = "SELECT ProductType FROM ProductStock WHERE(ProductName = '"+productName+"')";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
//                fetchedValues.add(result.getString("ProductName"));//0
                fetchedValues.add(result.getString("ProductType"));//1
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fetchedValues;
    }

    public ObservableList<StockLevelData> fetchStockLevelDetails() {
        ObservableList<StockLevelData> stockLevelData = FXCollections.observableArrayList();

        try {
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery( "SELECT\n" +
                    "    sl.id, ProductName, StockLevel, CurrentQty, PresentUnitQty, PresentPackQty, PresentPackPerQty, PreviousUnitQty, PreviousPackQty, PreviousPackPerQty, BeforeUnitQty, BeforePackQty, BeforePackQty, BeforePerPackQty, StockGuage, UpdatedBy, UpdatedDate\n" +
                    "    FROM productstock AS ps\n" +
                    "    INNER JOIN stocklevels as sl\n" +
                    "    ON sl.ProductId = ps.id \n" +
                    "    WHERE ps.DeleteStatus = 0;");
            while (result.next()) {
                int id = result.getInt("sl.id");
                String ProductId = result.getString("ProductName");
                int StockLevel = result.getInt("StockLevel");
                int CurrentQty = result.getInt("CurrentQty");
                int PresentUnitQty = result.getInt("PresentUnitQty");
                int PresentPackQty = result.getInt("PresentPackQty");
                int PresentPackPerQty = result.getInt("PresentPackPerQty");
                int PreviousUnitQty = result.getInt("PreviousUnitQty");
                int PreviousPackQty = result.getInt("PreviousPackQty");
                int PreviousPackPerQty = result.getInt("PreviousPackPerQty");
                int BeforeUnitQty = result.getInt("BeforeUnitQty");
                int BeforePackQty = result.getInt("BeforePackQty");
                int BeforePerPackQty = result.getInt("BeforePerPackQty");
                int StockGuage = result.getInt("StockGuage");
                String UpdatedBy = result.getString("UpdatedBy");
                Timestamp UpdatedDate = result.getTimestamp("UpdatedDate");

                stockLevelData.add(new StockLevelData(id, ProductId, StockLevel, CurrentQty, PresentUnitQty, PresentPackQty, PresentPackPerQty, PreviousUnitQty, PreviousPackQty, PreviousPackPerQty, BeforeUnitQty, BeforePackQty, BeforePerPackQty,StockGuage, UpdatedBy, UpdatedDate ));

            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stockLevelData;
    }


//    public static void main(String[] args) throws SQLException {
//        DbConnection con = new DbConnection();
//        System.out.println("connecting....");
//        try  {
//            con.CONNECTOR();
//            System.out.println("connected....");
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}//END OF CLASS
