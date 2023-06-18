package inn.fetchedData;

import java.sql.Timestamp;

public class OvertimeData {
    int overtimeId;
    String title;
    Double cost;
    Timestamp date_created;

    public OvertimeData(int overtimeId, String title, Double cost, Timestamp date_created) {
        this.overtimeId = overtimeId;
        this.title = title;
        this.cost = cost;
        this.date_created = date_created;
    }

    public int getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(int overtimeId) {
        this.overtimeId = overtimeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }
}
