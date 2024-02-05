package agenda;

import utils.Arquivo;

import java.util.ArrayList;
import java.util.Scanner;

public class Agenda {

    public static ArrayList<Contato> contactsArray;
    public static ArrayList<Telefone> phonesArray;

    //Create a new contact
    public static void addContact() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nDigite o nome: ");
        String name = input.nextLine();

        System.out.print("Digite o sobrenome: ");
        String lastName = input.nextLine();

        System.out.print("Digite o DDD do telefone: ");
        String ddd = input.nextLine();

        System.out.print("Digite o número de telefone: ");
        long phoneNumber = input.nextLong();
        input.nextLine();

        //Check if the number exists
        while (Telefone.checkNumber(ddd, phoneNumber)) {
            System.out.println("\nNúmero existente! Tente novamente");

            System.out.print("\nDigite o DDD do telefone: ");
            ddd = input.nextLine();

            System.out.print("Digite o número de telefone: ");
            phoneNumber = input.nextLong();
        }

        //Get contact increment
        Long idC = Contato.getContactInc();

        //Create contact entry
        Contato contact = new Contato(idC, name, lastName);

        //Get phone increment
        Long id = Telefone.getPhoneInc();

        //Create phone entry
        Telefone phone = new Telefone(idC, id, ddd, phoneNumber);

        //Create Telefone list
        ArrayList<Telefone> contactPhones = new ArrayList<>();
        contactPhones.add(phone);

        //Ask for adding more numbers
        int answer;
        do {
            System.out.println("""
                    \nDeseja adicionar outro número?
                    1 - Sim
                    2 - Não
                    """);

            System.out.print("Selecione uma opção: ");
            answer = input.nextInt();
            input.nextLine();

            if (answer == 1) {
                System.out.print("\nDigite o DDD do telefone: ");
                ddd = input.nextLine();

                System.out.print("Digite o número de telefone: ");
                phoneNumber = input.nextLong();

                if (Telefone.checkNumber(ddd, phoneNumber)) {
                    System.out.println("\nNúmero existente! Tente novamente");
                } else {
                    //Create phone entry
                    id++;
                    phone = new Telefone(idC, id, ddd, phoneNumber);
                    contactPhones.add(phone);
                }
            } else if (answer != 2)
                System.out.println("\nOpção inválida selecionada!");

        } while (answer != 2);

        //Write phones to contact
        contact.setTelefones(contactPhones);

        //Add the new contact to the list
        contactsArray.add(contact);

        //Add the new phones to the list
        phonesArray.addAll(contactPhones);

        //Write contact and phones to file
        Arquivo.writeContact(contact);
        Arquivo.writePhones(contactPhones);
    }

    //Remove a contact
    public static void removeContact() {
        if (contactsArray.isEmpty()) {
            System.out.println("\nNão existem IDs para remoção");
            return;
        }

        Scanner input = new Scanner(System.in);

        System.out.print("\nDigite o ID a ser removido: ");
        long id = input.nextLong();
        input.nextLine();

        int contactIdx = Contato.checkId(id);

        while (contactIdx == -1) {
            System.out.print("\nID não encontrado! Digite novamente: ");
            id = input.nextLong();
            input.nextLine();

            contactIdx = Contato.checkId(id);
        }

        //Remove the contact
        contactsArray.remove(contactIdx);

        //Remove the phone numbers
        ArrayList<Telefone> contactPhones = Contato.getContactPhones(id);

        int phoneIdx;
        for (Telefone phone : contactPhones) {
            phoneIdx = Telefone.checkId(phone.getId());
            if (phoneIdx != -1)
                phonesArray.remove(phoneIdx);
        }

        //Rewrite the files with the modified lists
        Arquivo.rewriteFiles();
    }

    //Edit contact information
    public static void editContact() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nDigite o ID para edição: ");
        long id = input.nextLong();
        input.nextLine();

        int contactIdx = Contato.checkId(id);

        while (contactIdx == -1) {
            System.out.print("\nID não encontrado! Digite novamente: ");
            id = input.nextLong();
            input.nextLine();

            contactIdx = Contato.checkId(id);
        }

        Contato contact = contactsArray.get(contactIdx);

        int menuOption;

        do {
            System.out.println("""
                    \nO que deseja editar?
                    1 - Nome
                    2 - Sobrenome
                    3 - Telefones
                    4 - Finalizar
                    """);

            System.out.print("Selecione uma opção: ");
            menuOption = input.nextInt();
            input.nextLine();

            if (menuOption < 1 || menuOption > 4)
                System.out.println("Opção inválida selecionada!");

            if (menuOption == 1) {
                System.out.printf("\nNome atual: %s\nNovo nome: ", contact.getNome());
                String name = input.nextLine();
                contact.setNome(name);

            } else if (menuOption == 2) {
                System.out.printf("\nSobrenome atual:%s\nNovo sobrenome: ", contact.getSobreNome());
                String lastName = input.nextLine();
                contact.setSobreNome(lastName);

            } else if (menuOption == 3) {
                int subOption;

                do {
                    System.out.println("""
                            \nO que deseja fazer?
                            1 - Adicionar um número
                            2 - Remover um número
                            3 - Editar um número
                            4 - Finalizar
                            """);

                    System.out.print("Selecione uma opção: ");
                    subOption = input.nextInt();
                    input.nextLine();

                    if (subOption < 1 || subOption > 4)
                        System.out.println("Opção inválida selecionada!");

                    //Get phones for the contact
                    ArrayList<Telefone> contactPhones = Contato.getContactPhones(id);

                    if (subOption == 1) {
                        addPhones(contact, contactPhones);

                    } else if (subOption == 2) {
                        removePhones(contactPhones);

                    } else if (subOption == 3) {
                        editPhones(contactPhones);
                    }
                } while (subOption != 4);
            }
        } while (menuOption != 4);

        //Rewrite the files with the modified lists
        Arquivo.rewriteFiles();
    }

    //Add phone numbers to a contact
    public static void addPhones(Contato contact, ArrayList<Telefone> contactPhones) {
        Scanner input = new Scanner(System.in);

        System.out.print("\nDigite o DDD do telefone: ");
        String ddd = input.nextLine();

        System.out.print("Digite o número de telefone: ");
        long phoneNumber = input.nextLong();
        input.nextLine();

        //Check if the number exists
        while (Telefone.checkNumber(ddd, phoneNumber)) {
            System.out.println("\nNúmero existente! Tente novamente");

            System.out.print("\nDigite o DDD do telefone: ");
            ddd = input.nextLine();

            System.out.print("Digite o número de telefone: ");
            phoneNumber = input.nextLong();
        }

        //Get phone increment
        Long phoneId = Telefone.getPhoneInc();

        //Create phone entry
        Telefone phone = new Telefone(contact.getId(), phoneId, ddd, phoneNumber);

        //Add phone to list
        contactPhones.add(phone);

        //Update list on contact
        contact.setTelefones(contactPhones);

        //Write new phone number to file
        Arquivo.writePhone(phone);
    }

    //Remove phone numbers from a contact
    public static void removePhones(ArrayList<Telefone> contactPhones) {
        if (contactPhones.size() <= 1) {
            System.out.println("Apenas um número disponível. Edite o número ou remova o contato");
            return;
        }

        Scanner input = new Scanner(System.in);

        System.out.print("\nQual número deseja remover?\n\nId |   Número");
        for (Telefone phone : contactPhones) {
            System.out.printf("\n%d  |   %s %d", phone.getId(), phone.getDdd(), phone.getNumero());
        }

        System.out.print("\nSelecione uma opção: ");
        long phoneId = input.nextLong();
        input.nextLine();

        int phoneIdx = Telefone.checkId(phoneId);

        while (phoneIdx == -1) {
            System.out.print("\nID não encontrado! Digite novamente: ");
            phoneId = input.nextLong();
            input.nextLine();

            phoneIdx = Telefone.checkId(phoneId);
        }

        phonesArray.remove(phoneIdx);

        //Rewrite the files with the modified lists
        Arquivo.rewriteFiles();
    }

    //Edit phone numbers from a contact
    public static void editPhones(ArrayList<Telefone> contactPhones) {
        Scanner input = new Scanner(System.in);

        System.out.print("\nQual número deseja editar?\n\nId |   Número");
        for (Telefone phone : contactPhones) {
            System.out.printf("\n%d  |   %s %d", phone.getId(), phone.getDdd(), phone.getNumero());
        }

        System.out.print("\n\nSelecione uma opção: ");
        long phoneId = input.nextLong();
        input.nextLine();

        int phoneIdx = Telefone.checkId(phoneId);

        while (phoneIdx == -1) {
            System.out.print("\nID não encontrado! Digite novamente: ");
            phoneId = input.nextLong();
            input.nextLine();

            phoneIdx = Telefone.checkId(phoneId);
        }

        Telefone phone = phonesArray.get(phoneIdx);

        System.out.print("\nDigite o DDD do telefone: ");
        String ddd = input.nextLine();

        System.out.print("Digite o número de telefone: ");
        long phoneNumber = input.nextLong();
        input.nextLine();

        //Check if the number exists
        while (Telefone.checkNumber(ddd, phoneNumber)) {
            System.out.println("\nNúmero existente! Tente novamente");

            System.out.print("\nDigite o DDD do telefone: ");
            ddd = input.nextLine();

            System.out.print("Digite o número de telefone: ");
            phoneNumber = input.nextLong();
        }

        phone.setDdd(ddd);
        phone.setNumero(phoneNumber);

        //Rewrite the files with the modified lists
        Arquivo.rewriteFiles();
    }

    //View contact information
    public static void viewContact() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nDigite o ID para visualização: ");
        long id = input.nextLong();
        input.nextLine();

        int contactIdx = Contato.checkId(id);

        while (contactIdx == -1) {
            System.out.print("\nID não encontrado! Digite novamente: ");
            id = input.nextLong();
            input.nextLine();

            contactIdx = Contato.checkId(id);
        }

        Contato contact = contactsArray.get(contactIdx);

        System.out.println("\n>>>> Informação do contato <<<<");
        System.out.printf("Nome: %s\nSobrenome: %s\nTelefones", contact.getNome(), contact.getSobreNome());

        ArrayList<Telefone> contactPhones = Contato.getContactPhones(id);
        for (Telefone phone : contactPhones)
            System.out.printf("\n%s %d", phone.getDdd(), phone.getNumero());

        System.out.println("\n\nPressione enter para continuar");
        input.nextLine();
    }
}