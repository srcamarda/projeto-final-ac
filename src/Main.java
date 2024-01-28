import agenda.*;
import utils.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Call method to create the files
        Arquivo.createFiles();

        //Get contacts and phones
        Agenda.contactsArray = Contato.getContacts();
        Agenda.phonesArray = Telefone.getPhones();

        int menuOption;

        do {
            //Show the initial menu
            System.out.println(Menu.showMenu());
            System.out.print("Selecione uma opção: ");

            menuOption = input.nextInt();
            input.nextLine();

            if (menuOption < 1 || menuOption > 5)
                System.out.println("Opção inválida selecionada!");

            switch (menuOption) {
                case 1: //View contact
                    Agenda.viewContact();
                    break;
                case 2: //Add contact
                    Agenda.addContact();
                    break;
                case 3: //Remove contact
                    Agenda.removeContact();
                    break;
                case 4: //Edit contact
                    Agenda.editContact();
                    break;
            }

        } while (menuOption != 5);

        input.close();
    }
}