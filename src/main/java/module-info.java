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
    requires materialfx;

    exports inn;
    exports inn.controllers;
    exports inn.controllers.dashboard;
    exports inn.controllers.settings;
    exports inn.controllers.report;
    exports inn.controllers.messagebox;
    exports inn.prompts;
    exports inn.tableViews;
    exports inn.enumerators;
    exports inn.controllers.bookingPops;
    exports inn.controllers.inventory;

    opens inn.controllers;
    opens inn.controllers.messagebox;
    opens inn.controllers.dashboard;
    opens inn.controllers.settings;
    opens inn.controllers.booking;
    opens inn.controllers.report;
    opens inn.prompts;
    opens inn.tableViews;

    opens inn.controllers.configurations;
    opens inn.enumerators;
    opens inn.controllers.bookingPops;
    opens inn.controllers.inventory;




}