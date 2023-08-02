import exceptions.ContactCreationCancelledException;

import javax.swing.*;
import java.util.ArrayList;

public class PhonebookController {
private final ContactRepository contactRepository = new ContactRepository();
    ArrayList<Contact> contacts = new ArrayList<>();

    public PhonebookController() {
        this.reloadContacts();
    }
    public void collectAndAddContact(){
        try {
            this.contactRepository.addToContactsFile(collectContactInfo());
            this.reloadContacts();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private void reloadContacts() {
        try {
            this.contacts = this.contactRepository.getContacts();
        } catch (Exception e){
            e.printStackTrace();
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
                contact.getFirstName() == null || contact.getPhoneNumber() == null || contact.getLastName() == null ||
                contact.getFirstName().isBlank() || contact.getPhoneNumber().isBlank() || contact.getLastName().isBlank()
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

        try {
            message.append("Name\t" + "Phone\t" + "Email\n");

            if(this.contacts.isEmpty()) {
                message.append("No contacts to display");
            } else {
                for (Contact contact: this.contacts){
                    message.append(contact.getFirstName() + " " + contact.getLastName()).append("\t")
                            .append(contact.getPhoneNumber()).append("\t")
                            .append(contact.getEmail()).append("\n");
                }
            }
        } catch (Exception exception) {
            message.append(exception.getMessage());
        }
        this.displayMessage(message.toString());
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void findContact(){
        String contactNameToFind = this.getUserInput("Enter the name or number to find");

        ArrayList<Contact> foundContacts = new ArrayList<>();

        for (Contact currentContact: this.contacts){
            if (this.contactMatches(currentContact, contactNameToFind)) {
                foundContacts.add(currentContact);
            }
        }
        System.out.println(foundContacts);
    }

    private boolean contactMatches(Contact currentContact, String contactNameToFind) {
        // "zino" contains "zi" or "adidi" contains "zi"
        // "zino adidi" contains "zi"

        /* simplified version of the code below:
        String cleanedContact = currentContact.toString().trim().toLowerCase();
        cleanedContact = cleanedContact.replace(",", "")
        * if(cleanedContact.contain(contactNameToFind.trim().toLowerCase())){
            return true
        } else {
            return false;
        }
        * */
        return currentContact
                .toString() // e9a9ebd1-f55e-4c46-bf76-a28663aef610, dddd, sssss, 22546468, dddd@mail.com
                .toLowerCase() // convert all letters to small letters
                .trim() // remove all spaces
                .replace(",", "") // remove comma
                .contains( // we check if this cleaned value contains what user typed
                        contactNameToFind
                                .trim()
                                .toLowerCase()
                );
    }

    public void removeContact(){

    }
}
