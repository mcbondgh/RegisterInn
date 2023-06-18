package inn.fetchedData;

import java.sql.Timestamp;

public class UsersData {
    private int id;
    private String username;
    private String password;
    int emp_id;
    private int role_id;
    private int is_default;
    private int status;
    private int added_by;
    Timestamp date_added;
    Timestamp modified_date;

    public UsersData(int id, String username, String password, int emp_id, int role_id, int is_default, int status, int added_by, Timestamp date_added, Timestamp modified_date) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.emp_id = emp_id;
        this.role_id = role_id;
        this.is_default = is_default;
        this.status = status;
        this.added_by = added_by;
        this.date_added = date_added;
        this.modified_date = modified_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }

    public Timestamp getDate_added() {
        return date_added;
    }

    public void setDate_added(Timestamp date_added) {
        this.date_added = date_added;
    }

    public Timestamp getModified_date() {
        return modified_date;
    }

    public void setModified_date(Timestamp modified_date) {
        this.modified_date = modified_date;
    }
}//end of clas
