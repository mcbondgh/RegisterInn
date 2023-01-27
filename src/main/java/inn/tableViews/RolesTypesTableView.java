package inn.tableViews;


public class RolesTypesTableView {

    public RolesTypesTableView() {}

    private String roleName;
    private int roleId;

    public RolesTypesTableView(int roleId, String name) {
        this.roleId = roleId;
        this.roleName = name;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int   roleId) {
        this.roleId = roleId;
    }






} // END OF CLASS.......




