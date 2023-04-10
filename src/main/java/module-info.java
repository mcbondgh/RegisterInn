module inn {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires annotations;
    requires MaterialFX;
    requires jfoenix;

    exports inn;
    exports inn.Controllers;
    exports inn.Controllers.dashboard;
    exports inn.Controllers.settings;
    exports inn.Controllers.accounting;
    exports inn.Controllers.booking;
    exports inn.Controllers.report;
    exports inn.Controllers.messagebox;
    exports inn.prompts;
    exports inn.tableViews;
    exports inn.enumerators;
    exports inn.Controllers.bookingPops;
    exports inn.Controllers.inventory;

    opens inn.Controllers;
    opens inn.Controllers.messagebox;
    opens inn.Controllers.dashboard;
    opens inn.Controllers.settings;
    opens inn.Controllers.booking;
    opens inn.Controllers.report;
    opens inn.Controllers.accounting;
    opens inn.prompts;
    opens inn.tableViews;
    exports inn.Controllers.configurations;
    opens inn.Controllers.configurations;
    opens inn.enumerators;
    opens inn.Controllers.bookingPops;
    opens inn.Controllers.inventory;




}