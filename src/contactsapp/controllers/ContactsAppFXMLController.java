/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsapp.controllers;

import contactsapp.Contact;
import contactsapp.ContactsApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Benjamin Staugaard | Benz56
 */
public class ContactsAppFXMLController implements Initializable {

    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    private ListView<Contact> contactsList;

    @FXML
    private TextField firstName, lastName, eMail, phoneNumber;

    @FXML
    private void deleteButtonClickEvent(ActionEvent event) {
        int selectedIndex = this.contactsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            this.contacts.remove(selectedIndex);
        }
    }

    @FXML
    private void addButtonClickEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(ContactsApp.class.getResource("fxml/NewContactFXML.fxml"));
            loader.setControllerFactory(cf -> new NewContactFXMLController(ContactsAppFXMLController.this));

            Stage stage = new Stage();
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    stage.close();
                }
            });
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(ContactsApp.class.getResource("css/CSS.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contacts.add(new Contact("Benjamin", "Staugaard", "123@test.dk", 88888888));
        this.contacts.add(new Contact("Jan", "Mortensen", "532@test.dk", 86630673));
        this.contacts.add(new Contact("Klaus", "Jensen", "663@test.dk", 86739677));
        this.contacts.add(new Contact("Henrik", "Clausen", "936@test.dk", 37024865));
        this.contacts.add(new Contact("John", "Pedersen", "643@test.dk", 73977306));
        this.contacts.add(new Contact("Holger", "Christiansen", "763@test.dk", 85469254));
        this.contacts.add(new Contact("Karsten", "Hansen", "322@test.dk", 89324756));
        this.contactsList.setItems(contacts);

        this.contactsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Update contact. Catches NPE on first selection and addition to empty list. Catches NFE on invalid number input.
            try {
                if (!this.firstName.getText().isEmpty()) {
                    oldValue.setFirstName(this.firstName.getText());
                }
                if (!this.lastName.getText().isEmpty()) {
                    oldValue.setLastName(this.lastName.getText());
                }
                if (!this.eMail.getText().isEmpty()) {
                    oldValue.setEmail(this.eMail.getText());
                }
                if (this.phoneNumber.getText().length() == 8) {
                    oldValue.setPhoneNumber(Integer.parseInt(this.phoneNumber.getText()));
                }
            } catch (NullPointerException | NumberFormatException ignored) {
            }

            // Load selected contact.
            this.firstName.setText(newValue == null ? "" : newValue.getFirstName());
            this.lastName.setText(newValue == null ? "" : newValue.getLastName());
            this.eMail.setText(newValue == null ? "" : newValue.getEmail());
            this.phoneNumber.setText(newValue == null ? "" : String.valueOf(newValue.getPhoneNumber()));
            this.contactsList.refresh();
        });
    }

    public ObservableList<Contact> getContacts() {
        return this.contacts;
    }
}
