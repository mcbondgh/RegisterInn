package inn.tableViews;


public class DepartmentTableView {

    private String departmentName;
    private int departmentId;

    public DepartmentTableView( int departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }


}//END OF CLASS;