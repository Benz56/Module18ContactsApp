/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsapp.controllers;

import contactsapp.Contact;
import java.net.URL;
import java.util.ResourceBundle;
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
    private Button cancelButton, addButton;

    @FXML
    private TextField firstName, lastName, eMail, phoneNumber;

    public NewContactFXMLController(ContactsAppFXMLController mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnAction(event -> {
            ((Stage) cancelButton.getScene().getWindow()).close();
        });

        addButton.setOnAction(event -> {
            if (firstName.getText().isEmpty()) {
                firstName.requestFocus();
                return;
            } else if (lastName.getText().isEmpty()) {
                lastName.requestFocus();
                return;
            } else if (eMail.getText().isEmpty()) {
                eMail.requestFocus();
                return;
            } else if (phoneNumber.getText().isEmpty() || phoneNumber.getText().length() != 8) {
                phoneNumber.requestFocus();
                return;
            }

            int parsedPhoneNumber;
            try {
                parsedPhoneNumber = Integer.parseInt(phoneNumber.getText());
            } catch (NumberFormatException e) {
                phoneNumber.requestFocus();
                return;
            }

            mainWindow.getContacts().add(new Contact(firstName.getText(), lastName.getText(), eMail.getText(), parsedPhoneNumber));
            ((Stage) cancelButton.getScene().getWindow()).close();
        });
    }
}
