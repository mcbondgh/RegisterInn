package inn.database;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DbConnection {
    public DbConnection() {}
    ButtonType YES = ButtonType.YES;

    protected Statement stmt = null;
    protected PreparedStatement prepare = null;
    protected ResultSet result = null;

    protected Connection CONNECTOR() throws SQLException {
        String SERVER_NAME = "Druglord";
        String PASSWORD = "1244656800";
        String URL = "jdbc:mysql://127.0.0.1:3308/inn_register";
        return DriverManager.getConnection(URL, SERVER_NAME, PASSWORD);
    }

  /*
  ----------------------------------------------------------------------------------------------------------------------
  ----------------------------------------------------------------------------------------------------------------------
   */

    //THIS METHOD WHEN CALL WILL RETURN ALL COLUMNS FROM THE business_info TABLE;
    public ArrayList<Object> fetchBusinessInfo(){
        ArrayList<Object> businessInfo = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM business_info;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                businessInfo.add(0,result.getString("bsi_name"));//0
                businessInfo.add(1,result.getString("bsi_email"));//1
                businessInfo.add(2,result.getString("bsi_address"));
                businessInfo.add(3,result.getInt("bsi_number"));
                businessInfo.add(4,result.getInt("bsi_alt_number"));
                businessInfo.add(5,result.getDate("bsi_registration_date"));
                businessInfo.add(6,result.getInt("bsi_workers"));
                businessInfo.add(7,result.getString("bsi_description"));
                businessInfo.add(8,result.getString("mng_name"));
                businessInfo.add(9,result.getInt("mng_number"));
                businessInfo.add(10, result.getString("mng_email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessInfo;
    }

    //THIS METHOD WHEN INVOKED WILL UPDATE THE SYSTEM GENERAL FIELDS WITH THE SPECIFIED NEW DETAILS OF THE ORGANIZATION.
    public void updateBusinessInfo(@NamedArg("businessName") String bsi_name, @NamedArg("businessEmail") String email, @NamedArg("businessAddress") String address, @NamedArg("bsinessNumner") int bsi_num,
     @NamedArg("otherNumber") int alt_num, @NamedArg("date") LocalDate date, @NamedArg("totalWorkers") int workers, @NamedArg("description") String desc, @NamedArg("mng_name") String mng_name, @NamedArg("mng_number") int mng_number, @NamedArg("mng_email") String mng_email) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(YES);
        try{
                String updateQuery = "UPDATE business_info SET bsi_name = ?, bsi_email = ?, bsi_address = ?, bsi_number = ?, bsi_alt_number = ?, bsi_registration_date = ?, bsi_workers = ?, bsi_description = ?, mng_name = ?, mng_number = ?, mng_email = ?, updated_date = DEFAULT; ";
                prepare = CONNECTOR().prepareStatement(updateQuery);
                prepare.setString(1, bsi_name);
                prepare.setString(2, email);
                prepare.setString(3, address);
                prepare.setInt(4, bsi_num);
                prepare.setInt(5, alt_num);
                prepare.setDate(6, Date.valueOf(date));
                prepare.setInt(7, workers);
                prepare.setString(8, desc);
                prepare.setString(9, mng_name);
                prepare.setInt(10, mng_number);
                prepare.setString(11, mng_email);
                prepare.execute();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Update Successful");
                success.setHeaderText("PERFECT, RECORDS UPDATED SUCCESSFULLY");
                success.showAndWait();

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
            String selectQuery = "SELECT DISTINCT(name) FROM roles ORDER BY name ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String roleName = result.getString(1);
                UserRoles.add(roleName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserRoles;
    }

    //THIS METHOD WHEN INVOKED TAKES A role_name AS AN ARGUMENT AND RETURNS THE id ASSOCIATED WITH SAME.
    public int fetchUserRoleID(String userRole) throws SQLException {
        int role_id = 0;
        try {
            String selectQuery = "SELECT id FROM roles WHERE name = '"+ userRole +"';";
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

    //FETCH EMPLOYEE NAMES ONLY FROM THE employees TABLE.
    public ObservableList<String> fetchEmployeeFullnames() {
        ObservableList<String> Employees = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT firstname, lastname FROM employees ORDER BY lastname ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String firstname = result.getString(1);
                String lastname = result.getString(2);
                String fullname = lastname + " " + firstname;
                Employees.add(fullname);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Employees;
    }

    //THIS METHOD WHEN INVOKED RETURNS ALL COLUMNS FORM THE employees table.
    public ArrayList<Object> fetchFullEmployeeDetails() {
        ArrayList<Object> employees = new ArrayList<>();




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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idtypes;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idtypes;
    }

    public ArrayList<String> fetchUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        try {
            String selectQuery = "SELECT DISTINCT(username) FROM users;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                usernames.add(result.getString(1));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return usernames;
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

















}//END OF CLASS
