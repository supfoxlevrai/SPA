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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class SpaClient {

    private static boolean isMessageValid(String s){
        if(s == null || s.isEmpty()) return false;
        return true;
    }

    private static int isNumberFormatValid(String s){
        int port;
        try {
            port = Integer.parseInt(s);
            return port;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String[] connexionClient(ChatConsoleView ccv, String[] args){
        String host = "";
        int port = -1;

        if(args.length == 0){
            System.out.println("Aucun argument fourni. Vous allez devoir saisir l'adresse IP et le port du serveur manuellement.");
            host = ccv.demanderSaisie("Adresse IP du serveur : ");

            while(!isMessageValid(host)){
                host = ccv.demanderSaisie("VEUILLEZ ENTREZ UNE ADRESSE IP VALIDE : ");
            }

            port = isNumberFormatValid(ccv.demanderSaisie("Port du serveur (ex: 9287) : "));
            while(port < 0){
                port = isNumberFormatValid(ccv.demanderSaisie("Port du serveur (ex: 9287) : "));
            }
        }
        else if(args.length == 1) {
            System.out.println("Un seul argument fourni. Vous allez devoir saisir le port du serveur manuellement (et l'adresse IP si ce dernier est invalide).");

            host = isMessageValid(args[0]) ? args[0] : ccv.demanderSaisie("VEUILLEZ ENTREZ UNE ADRESSE IP VALIDE : ");

            while(!isMessageValid(host)){
                host = ccv.demanderSaisie("VEUILLEZ ENTREZ UNE ADRESSE IP VALIDE : ");
            }

            port = isNumberFormatValid(ccv.demanderSaisie("Port du serveur (ex: 9287) : "));
            while(port < 0){
                port = isNumberFormatValid(ccv.demanderSaisie("Port du serveur (ex: 9287) : "));
            }

        }
        else{
            System.out.println("Deux arguments fournis. L'adresse IP et le port du serveur seront utilisés tels quels (à moins qu'ils ne soient invalides).");

            host = isMessageValid(args[0]) ? args[0] : ccv.demanderSaisie("VEUILLEZ ENTREZ UNE ADRESSE IP VALIDE : ");

            while(!isMessageValid(host)){
                host = ccv.demanderSaisie("VEUILLEZ ENTREZ UNE ADRESSE IP VALIDE : ");
            }


            port = isNumberFormatValid(args[1]) != -1 ? isNumberFormatValid(args[1]) : isNumberFormatValid(ccv.demanderSaisie("Port du serveur (ex: 9287) : "));
            
            while(port < 0){
                port = isNumberFormatValid(ccv.demanderSaisie("Port du serveur (ex: 9287) : "));
            }
        }

        return new String[]{host, port+""};
    }
    
    public static void main(String[] args) {

        try {
            //System.out.println("CREATION SOCKET");
            System.out.println("CREATION DU CLIENT...");
            
            ChatConsoleView ccv = new ChatConsoleView();
            String[] connexion = SpaClient.connexionClient(ccv, args);
            //Ajout d'une méthode pour gérer la connexion du client au serveur, en utilisant les arguments passés en ligne de commande 
            // ou en demandant à l'utilisateur de saisir l'adresse IP et le port du serveur.
            String host = connexion[0];
            int port = Integer.parseInt(connexion[1]);
            
            Socket s = new Socket(host, port);
            
            System.out.println("INSTALLATION...");


            //System.out.println(REPONSE);

            //Pour envoyer
            OutputStream out = s.getOutputStream();
            PrintWriter out2 = new PrintWriter(out, true);

            //Pour écouter/lire
            InputStream text = s.getInputStream();
            InputStreamReader text2 = new InputStreamReader(text);
            BufferedReader text3 = new BufferedReader(text2);

            ///L'écoute
            SpaServerListener rss = new SpaServerListener(text3, ccv);

            Thread t = new Thread(rss);
            t.start();
            
            //L'envoie
            SpaMessageSender env = new SpaMessageSender(out2);

            //System.out.println("CREATION DU CONTROLEUR");
            ControlerTchat ct = new ControlerTchat(ccv, env);
            //System.out.println("DEMARRAGE");
            ct.demarrer();
            s.close();
            ccv.fermerScanner();
        }
        catch(java.net.ConnectException e){
            System.out.println(e);
            System.out.println("Traduction : Le serveur que vous essayez de vous connecter est soit inexistant ou hors-service !");
            System.out.println("Veuillez reesyez plus tard OU contacter la personne possèdant le serveur !");
        }
        catch(java.net.SocketException e){
            System.out.println(e);
            System.out.println("Traduction : Le serveur que vous essayez de vous connecter est soit inexistant ou hors-service !");
            System.out.println("Veuillez reesyez plus tard OU contacter la personne possèdant le serveur !");
        }
        catch(Exception e){
            System.out.println(e);
        } 
    }
}
