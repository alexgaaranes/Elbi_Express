module Elbi.Express {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;
    requires jdk.xml.dom;

    opens main to javafx.graphics;
}