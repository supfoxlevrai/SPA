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
//import java.io.PrintWriter;

public class ControlerTchat {
    private ChatConsoleView cvv;
    private SpaMessageSender out;

    public ControlerTchat(ChatConsoleView ccv, SpaMessageSender out){
        this.cvv = ccv;
        this.out = out;
    }

    private static void pause(int duree){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String SaisieValid(){
        String exp = this.cvv.demanderSaisie("Entrez votre pseudo :");
        while(exp.isEmpty()){
            exp = this.cvv.demanderSaisie("Entrez un pseudo valide :");
        }
        return exp;

    }

    public int demarrer(){
        ControlerTchat.pause(1000);
        System.out.println("Bienvenue sur le Tchat SPA");
        ControlerTchat.pause(1000);

        try{
            String exp = SaisieValid();
            String saisie = "";
            
            pause(1000);
            out.envoyer("SERVEUR ; L'Utilisateur(trice) ["+exp+"] s'est connecté(e) !");
            pause(1);

            /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                this.out.envoyer("SERVEUR ; L'Utilisateur(trice) ["+exp+"]  s'est déconnecté(e) !");
                System.out.println("\nVous avez demandé l'arrêt du programme. Fermeture des ressources...");
                System.out.println("La prochaine fois, veuillez utiliser la commande '$fin' pour quitter le tchat...");
                System.out.println("Fin du Tchat SPA");
            }));*/
            
            while(!saisie.equalsIgnoreCase("$fin")){
                
                saisie = this.cvv.demanderSaisie("");
                // Changement : Remplacement de l'ancien libellé "Envoyer un message" par un prompt minimaliste "$>"
                // pour une meilleure expérience utilisateur. Le prompt "$>" indique que l'utilisateur peut saisir un message à envoyer.

                if(!saisie.equalsIgnoreCase("$fin")) out.envoyer(exp+";"+saisie);
                ControlerTchat.pause(1000);
            }

            if(saisie.equalsIgnoreCase("$fin")) out.envoyer("SERVEUR ; L'Utilisateur(trice) ["+exp+"] s'est déconnecté(e) !");
            
            System.out.println("Fin du Tchat SPA");
            ControlerTchat.pause(1000);
            return 0;
        }
        catch(Exception e){
            System.out.println(e);
            return 1;

        }

    }
}
