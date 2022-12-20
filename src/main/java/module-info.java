module inn {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires annotations;

    exports inn;
    exports inn.Controllers;
    exports inn.Controllers.dashboard;
    exports inn.Controllers.settings;
    exports inn.Controllers.accounting;
    exports inn.Controllers.booking;
    exports inn.Controllers.report;
    exports inn.prompts;
    exports inn.tableViews;


    opens inn.Controllers;
    opens inn.Controllers.dashboard;
    opens inn.Controllers.settings;
    opens inn.Controllers.booking;
    opens inn.Controllers.report;
    opens inn.Controllers.accounting;
    opens inn.prompts;
    opens inn.tableViews;


}