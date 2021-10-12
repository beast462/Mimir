package net.beast462.int2204.mimir.application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML
    private WebView web;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        web.getEngine().load("https://google.com");
    }
}
