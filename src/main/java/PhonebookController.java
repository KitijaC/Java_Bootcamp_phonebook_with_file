import javax.swing.*;

public class PhonebookController {


    public void collectAndAddContact(){
        try {
            Contact contact = collectContactInfo();
            System.out.println(contact);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private Contact collectContactInfo() throws Exception {
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
                this.collectAndAddContact();
            }
            //throw new Exception("Contact creation failed");
        };
        return contact;
    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    public void displayAllContacts(){

    }public void findContact(){

    }public void removeContact(){

    }
}
