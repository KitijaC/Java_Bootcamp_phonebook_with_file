import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class ContactRepository {

    private final String contactFileName = "contacts.csv";

    public ContactRepository() {
        try {
            this.createContactsFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createContactsFile() throws IOException {
        File contactsFile = new File(this.contactFileName);
        contactsFile.createNewFile();
    }

    public void addToContactsFile(Contact contact) throws IOException {
        FileWriter contactsFile = new FileWriter(this.contactFileName, true);
        contactsFile.write(contact.toString());
        contactsFile.close();
    }

    public ArrayList<Contact> getContacts() throws FileNotFoundException {
        File contactsFile = new File(this.contactFileName);
        Scanner reader = new Scanner(contactsFile);

        ArrayList<Contact> contacts = new ArrayList<>();

        while (reader.hasNextLine()){
            String contactRowString = reader.nextLine();
            String[] singleContactDetailAsArray = contactRowString.split(","); // break down string containing single contact info into smaller values
            //if (singleContactDetailsAsArray.length < 5) continue; // skip an item in a loop
            Contact contact = this.validateAndConvertToContact(singleContactDetailAsArray);
            contacts.add(contact);
        }

        return contacts;
    }

    private Contact validateAndConvertToContact(String[] singleContactDetailAsArray) {
        Contact contact = new Contact();
        contact.setId(UUID.fromString(singleContactDetailAsArray[0]));
        contact.setFirstName(singleContactDetailAsArray[1]);
        contact.setLastName(singleContactDetailAsArray[2]);
        contact.setPhoneNumber(singleContactDetailAsArray[3]);

        if (singleContactDetailAsArray.length > 4){
            contact.setEmail(singleContactDetailAsArray[4]);
        }
        return contact;
    }
}
