package inn.tableViews;

import java.sql.Timestamp;

public class MessageTemplatesData {
    int templateId;
    String templateTitle, templateBody;
    String addedBy;
    Timestamp dateCreated, dateModified;

    public MessageTemplatesData(int templateId, String templateTitle, String templateBody, String addedBy, Timestamp dateCreated, Timestamp dateModified) {
        this.templateId = templateId;
        this.templateTitle = templateTitle;
        this.templateBody = templateBody;
        this.addedBy = addedBy;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateBody() {
        return templateBody;
    }

    public void setTemplateBody(String templateBody) {
        this.templateBody = templateBody;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }
}//end of class
