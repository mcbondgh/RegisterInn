package inn.models;

import inn.database.DbConnection;
import javafx.scene.control.Alert;

public class EmpProfileModel extends DbConnection {


    //THIS METHOD WHEN INVOKED WILL ACCEPT AN EMPLOYEE'S DETAILS AS AN ARGUMENT AND UPDATE SAME RECORD IN THE employees TABLE BASED ON employee's id.
    public void updateEmployeeRecord(int emp_id, String firstN, String lastN, String gen, String mail, String number, String address, String id_tpye, String idNo, String desg, double sal) {
        try {
            String updateQuery = "UPDATE employees SET firstname =?, lastname = ?, gender = ?, email = ?, phone = ?, digital_address = ?, id_type = ?," +
                    "id_number = ?, designation= ?, salary = ?, modified_date = DEFAULT WHERE( id = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setString(1, firstN);
            prepare.setString(2, lastN);
            prepare.setString(3, gen);
            prepare.setString(4, mail);
            prepare.setString(5, number);
            prepare.setString(6, address);
            prepare.setString(7, id_tpye);
            prepare.setString(8, idNo);
            prepare.setString(9, desg);
            prepare.setDouble(10, sal);
            prepare.setInt(11, emp_id);

        }catch (Exception e) {
            Alert alert  = new Alert(Alert.AlertType.ERROR, "update for specified employee failed, please report to the system admin");
            alert.setTitle("FAILED UPDATE");
            alert.setHeaderText("AN ERROR OCCURRED WHILE TRYING TO UPDATE EMPLOYEE'S DETAILS.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void deleteEmployeeRecord(int emp_id) {
        try {
            String deleteQuery = "DELETE FROM employees WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            prepare.setInt(1, emp_id);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}//END OF CLASS
