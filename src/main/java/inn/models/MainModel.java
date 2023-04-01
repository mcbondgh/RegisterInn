package inn.models;

import inn.ErrorLogger;
import inn.config.database.DatabaseConfiguration;
import inn.multiStage.MultiStages;
import inn.tableViews.*;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainModel extends DatabaseConfiguration {
    public MainModel() {}

    protected Connection CONNECTOR() throws SQLException {
           return DriverManager.getConnection(URL, SERVER_NAME, PASSWORD);
    }

    /**********************************************************************************************************************/
    MultiStages multiStagesOBJ = new MultiStages();
    ErrorLogger logger;
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
    public ObservableList<IdTypesData> fetchIdTypes() {
        ObservableList<IdTypesData> idtypes = FXCollections.observableArrayList();
        try{
            String selectQuery = "SELECT id, name FROM id_types ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                idtypes.add(new IdTypesData(id, name));
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
    public ObservableList<RoomPricesData> fetchRoomPrices() {
        ObservableList<RoomPricesData> categoryType = FXCollections.observableArrayList();
        try{
            String selectQuery = "SELECT * FROM roomPrices ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int roomId = result.getInt("id");
                String categoryName = result.getString("name");
                byte status = result.getByte("status");
                double price = result.getDouble("price");

                categoryType.add(new RoomPricesData(roomId, categoryName, status, price));
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

    public LinkedList<Integer> getUserId() {
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

    public int getUserIdByUsername(String username) {
        int userId = 0;
        try {
            String selectQuery = "SELECT emp_id FROM users WHERE(is_default = 0 AND username = '" + username + "');";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
               userId = result.getInt(1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
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
    public ObservableList<String> getRoomNoOnly() {
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

    //THIS METHOD WHEN INVOKED SHALL RETURN ALL ROWS IN THE rooms TABLE.
    public ObservableList<RoomsData> fetchRooms() {
        ObservableList<RoomsData> roomItems = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT * FROM rooms";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
               int id = result.getInt("id");
               String roomNo = result.getString("roomNo");
               String CategoryName = result.getString("CategoryName");
               byte status = result.getByte("status");
               byte isBooked = result.getByte("isBooked");
               double standardPrice = result.getDouble("standardPrice");
               Timestamp dateAdded = result.getTimestamp("dateAdded");
               roomItems.add( new RoomsData(id, roomNo, CategoryName, status, isBooked, standardPrice, dateAdded));
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
            String selectQuery = "SELECT * FROM Suppliers WHERE id > 1;";
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
            String selectQuery = """
                     SELECT pi.id, productName, supplyType, CategoryName, supplierName, BrandName, expiryDate,\s
                        StoreName, note, activeStatus, deleteStatus, username, pi.DateCreated
                        FROM productItems as pi
                        INNER JOIN StocksCategory as sc
                    \t\tON pi.categoryId = sc.id
                        INNER JOIN suppliers AS supId
                    \t\tON supplieriD = supId.id
                        JOIN productBrand AS pb
                    \t\tON brandId = pb.id
                    \tJOIN stores AS s
                    \t\tON storeId = s.id
                    \tJOIN users AS us
                    \t\tON us.id = addedBy
                    \tWHERE deleteStatus = 0;""";
            stmt = CONNECTOR().createStatement();
            result  = stmt.executeQuery(selectQuery);
            while (result.next()) {
                int rowId = result.getInt("pi.id");
                String productName = result.getString("productName");
                String ProductType = result.getString("supplyType");
                String productCategory = result.getString("CategoryName");
                String productSupplier = result.getString("supplierName");
                String productBrand = result.getString("BrandName");
                String storeId = result.getString("s.StoreName");
                Date expiryDate = result.getDate("expiryDate");
                String notes = result.getString("note");
                byte activeStatus = result.getByte(10);
                byte deleteStatus = result.getByte(11);
                String  addedBy = result.getString("username");
                Timestamp dateCreated = result.getTimestamp("pi.DateCreated");

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
                products.add(new ProductsStockData(rowId, productName, ProductType, productCategory, productSupplier, productBrand, expiryDate, notes, storeId, statusValue, deleteStatus, addedBy, dateCreated));
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
            String selectQuery = "SELECT Id, supplyType FROM ProductItems WHERE(ProductName = '"+ productName +"')";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            if(result.next()) {
                int id = result.getInt("Id");
                String type = result.getString("supplyType");
                productValues.add(id);
                productValues.add(type);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return productValues;
    }

    public String getProductTypeByName(@NamedArg("Product Name") String productName) throws SQLException {
        String value ="";
         String selectQuery = "SELECT supplyType FROM ProductItems WHERE(ProductName = '"+productName+"')";
         stmt = CONNECTOR().createStatement();
         result = stmt.executeQuery(selectQuery);
//       fetchedValues.add(result.getString("ProductName"));//0
        if (result.next()) {
            value = result.getString("supplyType");
        }
       return value;
    }

    public ObservableList<StockLevelData> fetchStockLevelDetails() {
        ObservableList<StockLevelData> stockLevelData = FXCollections.observableArrayList();
        try {
            String selectStatement = """
                    SELECT\s
                    \tsl.id, pi.productName, stocklevel, currentStockLevel, currentBoxQuantity,
                        currentQuantityPerBox, oldStockLevel, previousStockLevel, previousBoxQuantity, previousQuantityPerBox,
                        gage, username, lastModified
                       FROM StockLevels AS sl
                       INNER JOIN ProductItems AS pi
                    \t\tON productId = pi.id
                       INNER JOIN users as us
                    \t\tON sl.modifiedBy = us.id
                    \tWHERE pi.deleteStatus = 0;""";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectStatement);
            while (result.next()) {
                int stockId = result.getInt("sl.id");
                String productName = result.getString("productName");
                int stockLevel = result.getInt("stocklevel");
                int currentStockLeve = result.getInt("currentStockLevel");
                int currentBoxQuantity = result.getInt("currentBoxQuantity");
                int currentQuantityPerBox = result.getInt("currentQuantityPerBox");
                int oldStockLevel = result.getInt("oldStockLevel");
                int previousStockLevel = result.getInt("previousStockLevel");
                int previousBoxQuantity = result.getInt("previousBoxQuantity");
                int previousQuantityPerBox = result.getInt("previousQuantityPerBox");
                int gage = result.getInt("gage");
                String userName = result.getString("username");
                Timestamp lastModified = result.getTimestamp("lastModified");

                Label productNameLabel = new Label();
                if (stockLevel >= gage ) {
                    productNameLabel.setText(productName);
                    productNameLabel.setStyle("-fx-text-fill:#00a113;");
                    productNameLabel.setPadding(new Insets(2));
                } else if (stockLevel > 0){
                    productNameLabel.setText(productName);
                    productNameLabel.setStyle("-fx-text-fill:#f4a30d;");
                    productNameLabel.setPadding(new Insets(2));
                } else {
                    productNameLabel.setText(productName);
                    productNameLabel.setStyle("-fx-text-fill: #ff1f1f;");
                    productNameLabel.setPadding(new Insets(2));
                }

                stockLevelData.add( new StockLevelData(stockId, productNameLabel, stockLevel, currentStockLeve, currentBoxQuantity, currentQuantityPerBox, oldStockLevel, previousStockLevel, previousBoxQuantity, previousQuantityPerBox, gage, userName, lastModified));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stockLevelData;
    }
    public ObservableList<ProductPricesData> fetchProductPricesDetails() {
        ObservableList<ProductPricesData> productPrices = FXCollections.observableArrayList();
        try {
            String selectQuery = " SELECT productId, productName, purchasePrice, sellingPrice, profitPerItem, previousPurchasePrice, previousSellingPrice, previousProfit, username , dateModified\n" +
                    "    FROM productprices as pp\n" +
                    "    JOIN productItems as pi \n" +
                    "\t\tON productId = pi.id\n" +
                    "\tJOIN users as us\n" +
                    "\t\tON modifiedBy = us.id\n" +
                    "\tWHERE deleteStatus = 0;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                int productId = result.getInt("productId");
                float purchasePrice = result.getFloat("purchasePrice");
                String productName = result.getString("productName");
                float sellingPrice = result.getFloat("sellingPrice");
                float profitPerItem = result.getFloat("profitPerItem");
                float previousPurchasePrice = result.getFloat("previousPurchasePrice");
                float previousSellingPrice = result.getFloat("previousSellingPrice");
                float previousProfit = result.getFloat("previousProfit");
                String username = result.getString("username");
                Timestamp dateModified = result.getTimestamp("dateModified");
                productPrices.add(new ProductPricesData(productId, productName, purchasePrice, sellingPrice, profitPerItem, previousPurchasePrice, previousSellingPrice, previousProfit,username, dateModified));
            }
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
            ex.printStackTrace();
        }
        return productPrices;
    }

    public ObservableList<MessageTemplatesData> fetchMessageTemplates() {
        ObservableList<MessageTemplatesData> messageTemplate = FXCollections.observableArrayList();
        try {
            String selectStatement = "SELECT templateId, templateTitle, templateBody, dateCreated, dateModified, username \n" +
                    "    FROM messagetemplates as mt\n" +
                    "    INNER JOIN users as u\n" +
                    "    ON  u.id = createdBy;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectStatement);
            while(result.next()) {
                int temId = result.getInt("templateId");
                String tempTitle = result.getString("templateTitle");
                String tempBody = result.getString("templateBody");
                Timestamp tempDateCreated = result.getTimestamp("dateCreated");
                Timestamp tempDateModified = result.getTimestamp("dateModified");
                String userName = result.getString("username");
                messageTemplate.add(new MessageTemplatesData(temId, tempTitle, tempBody, userName, tempDateCreated, tempDateModified));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return messageTemplate;
    }


    public ObservableList<SentMessagesData> getAllSentMessages() {
        ObservableList<SentMessagesData> sentMessages = FXCollections.observableArrayList();
        try {
            String selectQuery = " SELECT sm.id, mobileNumber, messageTitle, messageBody, messageStatus, balance, username, sentDate FROM sentmessages AS sm\n" +
                    "    INNER JOIN users AS us\n" +
                    "    ON sm.sendBy = us.id;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                int messageId = result.getInt("sm.id");
                String mobileNumber = result.getNString("mobileNumber");
                String messageTitle = result.getNString("messageTitle");
                String messageBody = result.getString("messageBody");
                int messageStatus = result.getInt("messageStatus");
                int balance = result.getInt("balance");
                String username = result.getNString("username");
                Timestamp sentDate = result.getTimestamp("sentDate");

                Label convertedStatus = new Label();

                if (messageStatus == 1) {
                    convertedStatus.setText("Sent");
                    convertedStatus.setStyle("-fx-text-fill:#00aa0e; -fx-font-weight:bold; fx-alignment;center");
                } else {
                    convertedStatus.setText("Failed");
                    convertedStatus.setStyle("-fx-text-fill:#ff1f1f; -fx-font-weight:bold; fx-alignment;center");
                }
                sentMessages.add(new SentMessagesData(messageId, mobileNumber, messageTitle, messageBody, convertedStatus, balance, username, sentDate));
            }
        }catch (Exception e) {
            logger = new ErrorLogger();
            logger.log(e.getMessage());
        }

        return sentMessages;
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
