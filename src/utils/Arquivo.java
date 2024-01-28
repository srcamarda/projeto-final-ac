package utils;

import agenda.*;

import java.io.*;
import java.util.ArrayList;

public class Arquivo {
    //Set file paths
    static String localDir = System.getProperty("user.dir");
    public static final String PATH_TELEFONE = localDir + java.io.File.separator + "telefones.txt";
    public static final String PATH_CONTATO = localDir + java.io.File.separator + "contatos.txt";

    //Declare files
    public static File fileTelefone;
    public static File fileContato;

    //Create the database files
    public static void createFiles() {
        //Create files (only created if needed)
        fileTelefone = new File(Arquivo.PATH_TELEFONE);
        fileContato = new File(Arquivo.PATH_CONTATO);

        try {
            fileTelefone.createNewFile();
            fileContato.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Read the specified file and returns a array string
    public static ArrayList<String> readFile(File file) {
        ArrayList<String> lineArray = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String line = bf.readLine();

            while (line != null) {
                lineArray.add(line);
                line = bf.readLine();
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lineArray;
    }

    //Write a single contact to the file
    public static void writeContact(Contato contact) {
        try {
            String line = String.format("%d,%s,%s", contact.getId(), contact.getNome(), contact.getSobreNome());
            FileWriter fileWriter = new FileWriter(fileContato, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            //Only append new line if file is not empty
            if (fileContato.length() != 0)
                bw.newLine();

            bw.write(line);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Write multiple contacts to the file
    public static void writeContacts(ArrayList<Contato> contacts) {
        for (Contato contact : contacts) {
            writeContact(contact);
        }
    }

    //Write a single phone to the file
    public static void writePhone(Telefone phone) {
        try {
            String line = String.format("%d,%d,%s,%d", phone.getIdC(), phone.getId(), phone.getDdd(), phone.getNumero());
            FileWriter fileWriter = new FileWriter(fileTelefone, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            //Only append new line if file is not empty
            if (fileTelefone.length() != 0)
                bw.newLine();

            bw.write(line);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Write multiple phones to the file
    public static void writePhones(ArrayList<Telefone> contactPhones) {
        for (Telefone phone : contactPhones) {
            writePhone(phone);
        }
    }

    //Delete and rewrite the files
    public static void rewriteFiles() {
        //Delete existing files
        Arquivo.fileContato.delete();
        Arquivo.fileTelefone.delete();

        //Create the files again
        Arquivo.createFiles();

        //Write the contacts
        Arquivo.writeContacts(Agenda.contactsArray);
        Arquivo.writePhones(Agenda.phonesArray);
    }
}