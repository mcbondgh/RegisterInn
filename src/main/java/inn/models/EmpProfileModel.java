package inn.models;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DataTruncation;
import java.sql.SQLException;

public class EmpProfileModel extends MainModel {


    //THIS METHOD WHEN INVOKED WILL ACCEPT AN EMPLOYEE'S DETAILS AS AN ARGUMENT AND UPDATE SAME RECORD IN THE employees TABLE BASED ON employee's id.
    public void updateEmployeeRecord(int emp_id, String firstN, String lastN, String gen, String mail, String number, String address, String id_tpye, String idNo, String desg, InputStream image, double sal) throws SQLException, IOException {
        try {
            String updateQuery = "UPDATE employees SET firstname =?, lastname = ?, gender = ?, email = ?, phone = ?, digital_address = ?, id_type = ?," +
                    "id_number = ?, designation= ?, photo = ?, salary = ?, modified_date = DEFAULT WHERE( id = ?)";
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
            prepare.setBlob(10, image);
            prepare.setDouble(11, sal);
            prepare.setInt(12, emp_id);
            prepare.executeUpdate();
        }catch (DataTruncation e) {
            Alert alert  = new Alert(Alert.AlertType.ERROR, "update for specified employee failed, please report to the system admin");
            alert.setTitle("FAILED UPDATE");
            alert.setHeaderText("IMAGE SIZE IS TOO LARGE. IMAGE SHOULD BE 50KB OR BELOW.");
            alert.showAndWait();
            e.printStackTrace();
        } catch (Exception e) {
           throw e;
        }
    }

    public void deleteEmployeeRecord(int emp_id) {
        try {
            String deleteQuery = "DELETE FROM employees WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            prepare.setInt(1, emp_id);
            prepare.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}//END OF CLASS
