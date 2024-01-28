package utils;

import agenda.*;

public class Menu {
    //Show the options menu
    public static String showMenu() {
        StringBuilder menuStr = new StringBuilder("""
                ##################
                ##### AGENDA #####
                ##################
                                
                """);

        //Check contacts to display
        if (!Agenda.contactsArray.isEmpty()) {
            menuStr.append("""
                    >>>> Contatos <<<<
                    Id  |   Nome
                    """);

            for (Contato contato : Agenda.contactsArray) {
                menuStr.append(contato.toString());
                menuStr.append("\n");
            }
        } else {
            menuStr.append("Sem contatos salvos. Adicione selecionando as opções abaixo.");
            menuStr.append("\n");
        }

        menuStr.append("""
                                
                >>>> Menu <<<<
                1 - Visualizar contato
                2 - Adicionar Contato
                3 - Remover Contato
                4 - Editar Contato
                5 - Sair
                """);

        return menuStr.toString();
    }
}