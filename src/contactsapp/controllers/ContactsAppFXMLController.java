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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button deleteButton, addButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contacts.add(new Contact("Benjamin", "Staugaard", "123@test.dk", 88888888));
        contacts.add(new Contact("Jan", "Mortensen", "532@test.dk", 86630673));
        contacts.add(new Contact("Klaus", "Jensen", "663@test.dk", 86739677));
        contacts.add(new Contact("Henrik", "Clausen", "936@test.dk", 37024865));
        contacts.add(new Contact("John", "Pedersen", "643@test.dk", 73977306));
        contacts.add(new Contact("Holger", "Christiansen", "763@test.dk", 85469254));
        contacts.add(new Contact("Karsten", "Hansen", "322@test.dk", 89324756));
        contactsList.setItems(contacts);

        contactsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Update contact. Catches NPE on first selection. Catches NFE on invalid number input.
            try {
                oldValue.setFirstName(firstName.getText());
                oldValue.setLastName(lastName.getText());
                oldValue.setEmail(eMail.getText());
                if (phoneNumber.getText().length() == 8) {
                    oldValue.setPhoneNumber(Integer.parseInt(phoneNumber.getText()));
                }
            } catch (NullPointerException | NumberFormatException ignored) {
            }

            // Load selected contact.
            firstName.setText(newValue == null ? "" : newValue.getFirstName());
            lastName.setText(newValue == null ? "" : newValue.getLastName());
            eMail.setText(newValue == null ? "" : newValue.getEmail());
            phoneNumber.setText(newValue == null ? "" : String.valueOf(newValue.getPhoneNumber()));
            contactsList.refresh();
        });

        deleteButton.setOnAction(event -> {
            int selectedIndex = contactsList.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                contacts.remove(selectedIndex);
            }
        });

        addButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(ContactsApp.class.getResource("fxml/NewContactFXML.fxml"));
                loader.setControllerFactory(cf -> new NewContactFXMLController(ContactsAppFXMLController.this));

                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (IOException e) {
            }
        });
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }
}
