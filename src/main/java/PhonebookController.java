import exceptions.ContactCreationCancelledException;

import javax.swing.*;
import java.util.ArrayList;

public class PhonebookController {
private final ContactRepository contactRepository = new ContactRepository();

    public void collectAndAddContact(){
        try {
            this.contactRepository.addToContactsFile(collectContactInfo());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private Contact collectContactInfo() throws ContactCreationCancelledException {
        Contact contact = new Contact();
        // collect info here
        contact.setFirstName(this.getUserInput("Enter the first name"));
        contact.setLastName(this.getUserInput("Enter the last name"));
        contact.setPhoneNumber(this.getUserInput("Enter the phone number"));
        contact.setEmail(this.getUserInput("Enter the Email (Press enter to skip)"));
        if (
                contact.getFirstName() == null || contact.getPhoneNumber() == null ||
                contact.getFirstName().isBlank() || contact.getPhoneNumber().isBlank()
        ) {
            int userChoice = JOptionPane.showConfirmDialog(null, "Contact is missing name or phone number, " +
                    "do you want to start again?");
            if (userChoice == JOptionPane.YES_OPTION) {
                return this.collectContactInfo();
            }
            throw new ContactCreationCancelledException("Contact creation failed");
        };
        return contact;
    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    public void displayAllContacts(){
        StringBuilder message = new StringBuilder();
        ArrayList<Contact> contacts = this.contactRepository.getContacts();

        message.append("Name\t" + "Phone\t" + "Email\n");

        if(contacts.isEmpty()) {
            message.append("No contacts to display");
        } else {
            for (Contact contact: contacts){
                message.append(contact.getFirstName() + " " + contact.getLastName()).append("\t")
                        .append(contact.getPhoneNumber()).append("\t")
                        .append(contact.getEmail()).append("\n");
            }
        }

        this.displayMessage(message.toString());
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void findContact(){

    }public void removeContact(){

    }
}
