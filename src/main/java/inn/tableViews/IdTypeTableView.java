package inn.tableViews;


public class IdTypeTableView {
    private String idTypeName;
    private  int idTypeID;

    public IdTypeTableView( int idTypeID, String idTypeName) {
        this.idTypeID = idTypeID;
        this.idTypeName = idTypeName;

    }

    public String getIdTypeName() {
        return idTypeName;
    }

    public void setIdTypeName(String idTypeName) {
        this.idTypeName = idTypeName;
    }

    public int getIdTypeID() {
        return idTypeID;
    }

    public void setIdTypeID(int idTypeID) {
        this.idTypeID = idTypeID;
    }
}//END OF CLASS