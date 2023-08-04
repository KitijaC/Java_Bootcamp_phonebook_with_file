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
        this.displayContacts(this.contacts);
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void findContact(){
        String contactNameToFind = this.getUserInput("Enter the name or number to find");

       this.displayContacts(this.findContactsByValue(contactNameToFind));
    }

    private ArrayList<Contact> findContactsByValue(String value) {
        ArrayList<Contact> foundContacts = new ArrayList<>();

        for (Contact currentContact: this.contacts){
            if (this.contactMatches(currentContact, value)) {
                foundContacts.add(currentContact);
            }
        }
       return foundContacts;
    }

    private void displayContacts(ArrayList<Contact> contacts) {
        StringBuilder message = new StringBuilder();

        try {
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
        } catch (Exception exception) {
            message.append(exception.getMessage());
        }
        this.displayMessage(message.toString());
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
                                .toLowerCase()
                                .trim()
                                .replace(" ", "")
                );
    }

    public void removeContact() {
        try {
            String contactToFind = this.getUserInput("Please enter the name to remove contact:");
            ArrayList<Contact> contacts = this.findContactsByValue(contactToFind);

            if (contacts.isEmpty()) {
                this.displayMessage("No contacts found matching " + contactToFind);
                return;
            }

            Contact selectedContact = (Contact) JOptionPane.showInputDialog(
                    null,
                    "Select a contact to remove",
                    "Select contact",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    contacts.toArray(),
                    contacts.toArray()[0]
            );
            this.contacts.removeIf(currentContact -> currentContact.getId().equals(selectedContact.getId()));
            this.contactRepository.overwriteContactsFile(this.contacts);

            this.displayMessage("Contact updated successfully!");
            this.reloadContacts();
        } catch (Exception ex){
            this.displayMessage(ex.getMessage());
        }

    }

    public void updateContact(){
        String contactToFind = this.getUserInput("Please enter the name contact to update:");
        ArrayList<Contact> contacts = this.findContactsByValue(contactToFind);

        if (contacts.isEmpty()) {
            this.displayMessage("No contacts found matching " + contactToFind);
            return;
        }
        Contact selectedContact = (Contact) JOptionPane.showInputDialog(
                null,
                "Select a contact to update",
                "Select contact",
                JOptionPane.QUESTION_MESSAGE,
                null,
                contacts.toArray(),
                contacts.toArray()[0]
        );

        this.contacts.removeIf(currentContact -> currentContact.getId().equals(selectedContact.getId()));

        try{
            Contact updatedInfo = this.collectContactInfo();

            /*
            if (updatedInfo.getLastName() == null) {
                selectedContact.setLastName(updatedInfo.getLastName());
            } else {
                selectedContact.setLastName(selectedContact.getLastName());
            }
            */

            // update the array with the latest contact info
            for(Contact contact: this.contacts) {
                if (contact.getId().equals(selectedContact.getId())){
                    contact.setLastName(updatedInfo.getLastName() == null ? selectedContact.getLastName() : updatedInfo.getLastName());
                    contact.setFirstName(updatedInfo.getFirstName() == null ? selectedContact.getFirstName() : updatedInfo.getFirstName());
                    contact.setEmail(updatedInfo.getEmail() == null ? selectedContact.getEmail() : updatedInfo.getEmail());
                    contact.setPhoneNumber(updatedInfo.getPhoneNumber() == null ? selectedContact.getPhoneNumber() : updatedInfo.getPhoneNumber());

                    break; // this will end the loop because we already found what we are looking for
                }
            }
            // update the file with this latest content
            this.contactRepository.overwriteContactsFile(this.contacts);

            this.displayMessage("Contact updated successfully!");
            this.reloadContacts(); // just to make sure we have the latest contacts in the file
        } catch (Exception e) {
            this.displayMessage(e.getMessage());
        }
    }
}
