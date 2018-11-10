/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsapp.controllers;

import contactsapp.Contact;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Benjamin Staugaard | Benz56
 */
public class NewContactFXMLController implements Initializable {

    private final ContactsAppFXMLController mainWindow;

    @FXML
    private TextField firstName, lastName, eMail, phoneNumber;

    public NewContactFXMLController(ContactsAppFXMLController mainWindow) {
        this.mainWindow = mainWindow;
    }

    @FXML
    private void cancelButtonClickEvent(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void addButtonClickEvent(ActionEvent event) {
        if (this.firstName.getText().isEmpty()) {
            this.firstName.requestFocus();
            return;
        } else if (this.lastName.getText().isEmpty()) {
            this.lastName.requestFocus();
            return;
        } else if (this.eMail.getText().isEmpty()) {
            this.eMail.requestFocus();
            return;
        } else if (this.phoneNumber.getText().isEmpty() || this.phoneNumber.getText().length() != 8) {
            this.phoneNumber.requestFocus();
            return;
        }

        int parsedPhoneNumber;
        try {
            parsedPhoneNumber = Integer.parseInt(this.phoneNumber.getText());
        } catch (NumberFormatException ignored) {
            this.phoneNumber.requestFocus();
            return;
        }

        this.mainWindow.getContacts().add(new Contact(this.firstName.getText(), this.lastName.getText(), this.eMail.getText(), parsedPhoneNumber));
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
