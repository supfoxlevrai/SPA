/*
 * Copyright (c) 2026 Nathanaël Dos Santos. Tous droits réservés.
 * NE PAS MODIFIER NI RETIRER LES NOTICES DE COPYRIGHT DE CET EN-TÊTE.
 *
 * Ce code est un logiciel libre ; vous pouvez le redistribuer et/ou le modifier
 * selon les termes de la Licence Publique Générale GNU version 3 (GPLv3) telle
 * que publiée par la Free Software Foundation.
 *
 * Ce programme est distribué dans l'espoir qu'il sera utile, mais SANS AUCUNE
 * GARANTIE ; sans même la garantie implicite de commercialisation ou
 * d'adaptation à un usage particulier. Consultez la Licence GPL pour plus de détails.
 *
 * Vous devriez avoir reçu une copie de la Licence Publique Générale GNU
 * avec ce programme. Si ce n'est pas le cas, voir <https://www.gnu.org/licenses/>.
 *
 * Contact (pro) : Nathanaël Dos Santos <nathanael.dossantos@edu.univ-paris13.fr>
 * Contact (perso) :  <supfoxlevrai@gmail.com>
 */

package spa.client;

//import spa.model.ChatRoom;
//import spa.model.Message;

public class SpaLauncherTest {
    private static void pause(int duree){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*private static void TchatSimulation(String expedit1, String expedit2, String nomDuSalon, String[][] conversation){
        String expediteur1, expediteur2;
        expediteur1 = expedit1;
        expediteur2 = expedit2;

        String[] contenu_exp1 = conversation[0];
        String[] contenu_exp2 = conversation[1];

        ChatRoom spa = new ChatRoom(nomDuSalon);
        for(Message m:spa.getHistorique()) System.out.println(m);

        for(int i = 0; i < contenu_exp2.length ;i++){
            pause(2000);

            spa.ajouterMessage(expediteur1, contenu_exp1[i]);

            pause(1000);
            spa.ajouterMessage(expediteur2, contenu_exp2[i]);
        }

        for(Message m:spa.getHistorique()) System.out.println(m);

    }*/

    public static void main(String[] args) {
        /*String Nathanael, Eloise;
        Nathanael = "Nathanaël";
        Eloise = "Éloïse";
        String[] contenu_Nath = {"Salut !", "ça va ?", "Je vais bien merci !", "Rien écoute, et toi ?", "ok ok, t'as prévu quelque chose ce soir?", "Non, ça te dirais d'aller chez Jo", "Carré, je t'attends près de l'arrêt de bus alors"}; 
        String[] contenu_Elo = {"Coucouuu", "Ca vaaaa nikel ett ?", "Tu fais quoi ?", "En train de jouer... ", "Non, et toi", "Ok, pourquoi pas, j'invite Jin alors", "Okk j'arrv"};

        String[][] conv = {contenu_Nath,contenu_Elo };
        TchatSimulation(Nathanael, Eloise, "SPA", conv);*/

        System.out.println("Lancement du Test en cours...");
        int i = 0;
        while(i < 10){
            pause(1000);
            System.out.println("Test en cours...");

            if(i%3 == 0) System.out.println("Recherche de test...");
            i++;
        }
        
        System.out.println("Aucun Test n'est actuellement implémenté dans la classe SpaLauncherTest.java");

        System.out.println("Test terminé !");
    }
}
