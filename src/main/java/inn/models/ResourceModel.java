package inn.models;

import inn.database.DbConnection;
import inn.tableViews.EmployeesTable;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class ResourceModel extends DbConnection {

    //DEFAULT CONSTRUCTOR
    public ResourceModel() {super();}


    /**
     * THIS METHOD WHEN INVOKED WILL INSERT CURRENT RECORDS COLLECTED INTO THE employees TABLE
     */
    public void registerNewEmployee(@NamedArg("Firstname") String firstname, @NamedArg("Lastname") String lastname, @NamedArg("Gender")String gender, @NamedArg("email") String email, @NamedArg("Number") String number, @NamedArg("address")String address, @NamedArg("idType") String idType, @NamedArg("idNumber") String idNumber, @NamedArg("Date") Date dateJoined, String design, double salary) {
        try{
            String insertQuery = "INSERT INTO employees(firstname, lastname, gender, email, phone, digital_address, id_type, id_number, employment_date, designation, salary) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, firstname);
            prepare.setString(2, lastname);
            prepare.setString(3, gender);
            prepare.setString(4, email);
            prepare.setString(5, number);
            prepare.setString(6, address);
            prepare.setString(7, idType);
            prepare.setString(8, idNumber);
            prepare.setDate(9, dateJoined);
            prepare.setString(10, design);
            prepare.setDouble(11, salary);
            prepare.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * THIS METHOD WHEN INVOKED ACCEPTS x NUMBER OF PARAMETERS AND WILL INSERT INTO THE users TABLE THE CURRENTLY PARSED ARGUMENTS
     */
    public void registerNewUser(@NamedArg("emailAddress")String email, @NamedArg("password") String password, @NamedArg("ConfirmPassword") String pass2, @NamedArg("role_id") int roleId) {
        try {
            String insertQuery = "INSERT INTO users(username, password, confirm_password, role_id) VALUES(?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, email);
            prepare.setString(2, password);
            prepare.setString(3, pass2);
            prepare.setInt(4, roleId);
            prepare.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * THIS METHOD WHEN CALLED WILL RETURN ONLY EMPLOYEES WHO'S STATUS == 1 ie ACTIVE EMPLOYEES. AND STORE THE RESULT IN AN OBSERVABLE LIST ;
     *
     */
    public ObservableList<EmployeesTable> activeEmployees = FXCollections.observableArrayList();
    public void fetchActiveEmployees() {
        try {
            String selectQuery = "SELECT * FROM employees WHERE status = 1 ORDER BY lastname ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String fullname = firstname+ " " + lastname;
                String number = result.getString("phone");
                String address = result.getString("digital_address");
                String date = result.getDate("employment_date").toString();
                String salary = String.valueOf(result.getDouble("salary"));
                String status = String.valueOf(result.getByte("status"));
                String currentStatus = null;
                //SIMPLE IF TO SET STATUS = active ? 1 : 0
                if ("1".equals(status)) {
                    currentStatus = "Active";
                }
                String desig = result.getString("designation");
                CheckBox checkBox = new CheckBox();
                activeEmployees.add(new EmployeesTable(fullname, number, address , date, desig, currentStatus, salary, checkBox));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * THIS METHOD WHEN CALLED WILL RETURN ALL EMPLOYEES WHO'S STATUS = 0 ie INACTIVE EMPLOYEES (ARCHIVED) AND STORE THE RESULT IN AN OBSERVABLE LIST;
     * */
    public ObservableList<EmployeesTable> inActiveEmployees = FXCollections.observableArrayList();
    public void fetchInactiveEmployees() {
        try {
            String selectQuery = "SELECT * FROM employees WHERE status = 0 ORDER BY lastname ASC;";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String fullname = firstname+ " " + lastname;
                String number = result.getString("phone");
                String address = result.getString("digital_address");
                String date = result.getDate("employment_date").toString();
                String salary = String.valueOf(result.getDouble("salary"));
                String status = String.valueOf(result.getByte("status"));
                String currentStatus = "Active";
                //SIMPLE IF TO SET STATUS = active ? 1 : 0
                if ("0".equals(status)) {
                    currentStatus = "Inactive";
                }
                String desig = result.getString("designation");
                CheckBox checkBox = new CheckBox();
                inActiveEmployees.add(new EmployeesTable(fullname, number, address , date, desig, currentStatus, salary, checkBox));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }











}//END OF CLASS
