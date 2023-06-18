package inn.fetchedData;


import java.sql.Timestamp;

public class IdTypesData {
    private String idTypeName;
    private  int idTypeID;

    private int id;
    private byte status;

    private Timestamp date_created;

    public IdTypesData(int idTypeID, String idTypeName) {
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