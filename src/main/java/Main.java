import javax.swing.*;

public class Main {

    private final PhonebookController phonebookController = new PhonebookController();
    public static void main(String[] args) {
        Main main  = new Main();
        main.start();
    }

    void start() {
        String userChoice = "";

        while(!userChoice.equals("5")) {
            userChoice = JOptionPane.showInputDialog(
                    new StringBuilder()
                            .append("Welcome to Phonebook, please choose an option")
                            .append("\n1. Add new Contact")
                            .append("\n2. See Contact List")
                            .append("\n3. Find Contact")
                            .append("\n4. Remove Contact")
                            .append("\n5. Exit")
                            .toString()
            );

            switch (userChoice) {
                case "1":
                    // collect and add contact
                    this.phonebookController.collectAndAddContact();
                    break;
                case "2":
                    // see contact list
                    break;
                case "3":
                    // find contact
                    break;
                case "4":
                    // remove contact
                    break;
            }
        }
    }
}
