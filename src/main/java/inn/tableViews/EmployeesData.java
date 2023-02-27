package inn.tableViews;

import javafx.scene.control.CheckBox;

public class EmployeesData {
    private int empId;
   private String fullname, phoneNumber, joinedDate,  address, designation , status, salary;
   private CheckBox archiveButton;

    public EmployeesData(int empId, String fullname, String phoneNumber, String address, String joinedDate, String designation, String status, String salary, CheckBox archiveButton) {
        this.empId = empId;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.joinedDate = joinedDate;
        this.address = address;
        this.designation = designation;
        this.status = status;
        this.salary = salary;
        this.archiveButton = archiveButton;
        archiveButton.setText("🚮");;
        archiveButton.setStyle("-fx-text-fill:#fff; -fx-background-color: #ff0000; -fx-font-weight:bold");
        archiveButton.setMaxWidth(45.00);
    }




    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public CheckBox getArchiveButton() {
        return archiveButton;
    }

    public void setArchiveButton(CheckBox archiveButton) {
        this.archiveButton = archiveButton;
    }
}//END OF CLASS
