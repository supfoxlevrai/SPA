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

package spa.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import spa.model.ChatRoom;
import spa.observer.ChatObserver;
import spa.model.Message;

public class SpaClientHandler implements Runnable, ChatObserver {
    private Socket socket;
    private ChatRoom salon;
    private PrintWriter out;
    private BufferedReader read;


    public SpaClientHandler(ChatRoom sdd, Socket socket){
        this.salon = sdd;
        this.socket = socket;
        //System.out.println("Client Handler créé");

        try{
            this.out = new PrintWriter(this.socket.getOutputStream(), true);//pour que le client reçoive le message
            this.read = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));//pour que le serveur reçoive le message
            //System.out.println("Boom");

        }catch(IOException io){
            System.out.println("Côté SpaClientHandler :\n"+io);
        
        }catch(Exception e){
            System.out.println("Côté SpaClientHandler :\n"+e);
        }
        
    }

    @Override
    public void onNouveauMsg(Message m) {
        System.out.println("Client Handler A");
        this.out.println(m.getExpediteur()+";"+m.getContenu()+";"+m.getHorodatage());
        //this.pseudo = m.getExpediteur();
    }

    @Override
    public void run() {
        this.read();
        
    }

    private void read(){
        String line;
        try{
            while ((line = this.read.readLine()) != null) {
                String[] lines = line.split(";");
                
                String contenu = (lines.length > 1) ? lines[1] : " ";

                this.salon.ajouterMessage(lines[0], contenu);

                // Changement : avant, lorsque le client envoyait une chaîne invalide ou vide (""), 
                // le split ne créait pas le second élément du tableau.
                // Cela provoquait une erreur ArrayIndexOutOfBoundsException qui faisait sortir 
                // du thread de lecture du serveur. Le client se retrouvait alors désynchronisé : 
                // incapable d'envoyer de nouveaux messages, mais toujours connecté en réception ("oreille à la porte").
                // Maintenant, si le client envoie une chaîne invalide ou vide, le serveur considère 
                // que le message est vide et continue à écouter les messages suivants.
            }

        } catch (IOException /*| ArrayIndexOutOfBoundsException*/ e) {
            System.out.println("Une erreur est survenue lors de la lecture des messages");
        }
        finally{
            System.out.println('a');
            SpaServerApp.UtilisateurDeco(socket);
        }
    }
    
}
