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

//import java.net.*;
import java.io.*;
//import java.util.*;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;

import spa.model.Message;

public class SpaServerListener implements Runnable {
    //RecepteurServeurSpa : ancien nom de la classe

    private BufferedReader message;
    private ChatConsoleView affichage;

    public SpaServerListener(BufferedReader txt, ChatConsoleView cvv){
        this.message = txt;
        this.affichage = cvv;
    }

    public void run(){
        try{
            String m = "Yaya Touré";

            while((true)){
                //System.out.println("RECEPTEUR");
                m = message.readLine();
                if(/*m == null || */m.equalsIgnoreCase("$fin")){System.out.println("Un utilisateur est parti");}
                else{
                    //System.out.println("ENVOIE DU MESSAGE");
                    String[] message = Message.toSplit(m);
                    String expediteur = message[0];
                    String contenu = message[1];
                    
                    this.affichage.onNouveauMsg(new Message(expediteur, contenu));
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

