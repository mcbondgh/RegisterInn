package inn.fetchedData;

public class RequestedItemsData {

    private int request_id;
    private int stock_id;
    private String internal_item_name;
    private String request_status;
    private int requested_quantity;
    private String requested_date, username;


    public RequestedItemsData(int request_id, int stock_id, String internal_item_name, String request_status, int requested_quantity, String requested_date, String username) {
        this.request_id = request_id;
        this.stock_id = stock_id;
        this.internal_item_name = internal_item_name;
        this.request_status = request_status;
        this.requested_quantity = requested_quantity;
        this.requested_date = requested_date;
        this.username = username;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getInternal_item_name() {
        return internal_item_name;
    }

    public void setInternal_item_name(String internal_item_name) {
        this.internal_item_name = internal_item_name;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public int getRequested_quantity() {
        return requested_quantity;
    }

    public void setRequested_quantity(int requested_quantity) {
        this.requested_quantity = requested_quantity;
    }

    public String getRequested_date() {
        return requested_date;
    }

    public void setRequested_date(String requested_date) {
        this.requested_date = requested_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}//end of clsss
