package agenda;

import utils.Arquivo;

import java.util.*;

public class Contato {
    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    //Constructor
    public Contato(Long id, String name, String lastName) {
        this.id = id;
        this.nome = name;
        this.sobreNome = lastName;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    //Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    //Get the contacts in a array string and create Contato class object
    public static ArrayList<Contato> getContacts() {
        ArrayList<String> lineArray = Arquivo.readFile(Arquivo.fileContato);
        ArrayList<Contato> contactsArray = new ArrayList<>();

        for (String line : lineArray) {
            if (line.isEmpty())
                continue;

            String[] values = line.split(",");
            Contato contact = new Contato(Long.valueOf(values[0]), values[1], values[2]);

            //Search for phone number for the contact
            ArrayList<Telefone> contactPhones = getContactPhones(contact.id);

            if (!contactPhones.isEmpty())
                contact.telefones = contactPhones;

            //Add contact to list
            contactsArray.add(contact);
        }

        return contactsArray;
    }

    //Search for phones of the current contact
    public static ArrayList<Telefone> getContactPhones(Long id) {
        ArrayList<Telefone> allPhones = Telefone.getPhones();
        ArrayList<Telefone> contactPhones = new ArrayList<>();

        for (Telefone phone : allPhones) {
            if (Objects.equals(phone.getIdC(), id))
                contactPhones.add(phone);
        }

        return contactPhones;
    }

    //Get contact id increment
    public static Long getContactInc() {
        if (Agenda.contactsArray.isEmpty())
            return 1L;

        Contato lastContact = Agenda.contactsArray.get(Agenda.contactsArray.size() - 1);
        Long lastId = lastContact.id;

        return lastId + 1;
    }

    //Check if a ID is present and return its position
    public static int checkId(Long id) {
        for (Contato contact : Agenda.contactsArray) {
            if (contact.getId().equals(id)) {
                return Agenda.contactsArray.indexOf(contact);
            }
        }

        return -1;
    }

    //Format to string
    @Override
    public String toString() {
        return id + "   |   " + nome + " " + sobreNome;
    }
}