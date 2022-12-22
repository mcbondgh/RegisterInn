package inn.tableViews;

import javafx.scene.control.CheckBox;

public class ArchivedTable {
    private int empId1;
    private String fullname1, phoneNumber1, joinedDate1, address1, designation1, status1, salary1;
    private CheckBox restoreButton;

    public ArchivedTable(int empId1, String fullname1, String phoneNumber1, String address1, String joinedDate1, String designation1, String status1, String salary1, CheckBox restoreButton) {
        this.empId1 = empId1;
        this.fullname1 = fullname1;
        this.phoneNumber1 = phoneNumber1;
        this.address1 = address1;
        this.joinedDate1 = joinedDate1;
        this.designation1 = designation1;
        this.status1 = status1;
        this.salary1 = salary1;
        this.restoreButton = restoreButton;
        restoreButton.setText("🔁");
        restoreButton.setStyle("-fx-background-color:#00ce00; -fx-text-fill:#fff; -fx-font-weight:bold");
        restoreButton.setMaxWidth(45.00);

    }

    public int getEmpId1() {
        return empId1;
    }

    public void setEmpId1(int empId1) {
        this.empId1 = empId1;
    }

    public String getFullname1() {
        return fullname1;
    }

    public void setFullname1(String fullname1) {
        this.fullname1 = fullname1;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getJoinedDate1() {
        return joinedDate1;
    }

    public void setJoinedDate1(String joinedDate1) {
        this.joinedDate1 = joinedDate1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getDesignation1() {
        return designation1;
    }

    public void setDesignation1(String designation1) {
        this.designation1 = designation1;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getSalary1() {
        return salary1;
    }

    public void setSalary1(String salary1) {
        this.salary1 = salary1;
    }

    public CheckBox getRestoreButton() {
        return restoreButton;
    }

    public void setRestoreButton(CheckBox restoreButton) {
        this.restoreButton = restoreButton;
    }
}//END OF CLASS

