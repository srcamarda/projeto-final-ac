package agenda;

import utils.Arquivo;

import java.util.ArrayList;

public class Telefone {
    private Long idC;
    private Long id;
    private String ddd;
    private Long numero;

    //Constructor
    public Telefone(Long idC, Long id, String ddd, Long number) {
        this.idC = idC;
        this.id = id;
        this.ddd = ddd;
        this.numero = number;
    }

    //Getters
    public Long getIdC() {
        return idC;
    }

    public Long getId() {
        return id;
    }

    public String getDdd() {
        return ddd;
    }

    public Long getNumero() {
        return numero;
    }

    //Setters
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    //Get the phones in a array string and create Telefone object
    public static ArrayList<Telefone> getPhones() {
        ArrayList<String> lineArray = Arquivo.readFile(Arquivo.fileTelefone);
        ArrayList<Telefone> phonesArray = new ArrayList<>();

        for (String line : lineArray) {
            if (line.isEmpty())
                continue;

            String[] values = line.split(",");
            Telefone phone = new Telefone(Long.valueOf(values[0]), Long.valueOf(values[1]), values[2], Long.valueOf(values[3]));
            phonesArray.add(phone);
        }

        return phonesArray;
    }

    //Get phone id increment
    public static Long getPhoneInc() {
        if (Agenda.phonesArray.isEmpty())
            return 1L;

        Telefone lastphone = Agenda.phonesArray.get(Agenda.phonesArray.size() - 1);
        Long lastId = lastphone.id;

        return lastId + 1;
    }

    //Check if a phone is already on the list
    public static boolean checkNumber(String ddd, Long number) {
        for (Telefone phone : Agenda.phonesArray) {
            if (phone.getDdd().equals(ddd) && phone.getNumero().equals(number))
                return true;
        }
        return false;
    }

    //Check if a ID is present and return its position
    public static int checkId(Long id) {
        for (Telefone phone : Agenda.phonesArray) {
            if (phone.getId().equals(id))
                return Agenda.phonesArray.indexOf(phone);
        }

        return -1;
    }
}