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

package spa.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.nio.file.Files;
import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import spa.model.Message;


public class MessageFileDao implements DAOMessage {
    //DAOMessageFichier : ancien nom de la classe (je devais changer le nom de la classe par convention de nommage 😒)
    private File fichier;

    public MessageFileDao(String nomSalon){
        try {
            this.fichier = new File("data/"+nomSalon+".txt");
            if (this.fichier.createNewFile()) {           // Try to create the file
                System.out.println("File created: " + this.fichier.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); // Print error details
        }

    }

    @Override
    public void sauvegarder(Message message) {
            LocalDateTime horodatage = message.getHorodatage();
            String expediteur = message.getExpediteur();
            String contenu = message.getContenu();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/"+this.fichier.getName(), true))) {
            bw.newLine();
            bw.write(horodatage+";"+expediteur+";"+contenu);         
            
            //System.out.println("Ligne enregistrée");
        } catch (IOException e) {
            //System.out.println("Erreur durant l'écriture de la ligne");
        }
    }

    @Override
    public List<Message> chargerToutLesMessages() {
        List<Message> messages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/"+this.fichier.getName()))) {
            System.out.println("Chargement des messages depuis le fichier : " + this.fichier);
            System.out.println("Chargement des messages depuis le fichier : " + this.fichier.getName());
            String ligne;
            

            while ((ligne = br.readLine()) != null) { 

                if(!ligne.trim().isEmpty()){
                    String[] msg = ligne.split(";");

                    String ex = msg[1];
                    String con = msg[2];
                    String heure_minute = msg[0];

                    if(heure_minute == null || heure_minute.trim().isEmpty()) messages.add(new Message(ex, con));
                    else messages.add(new Message(ex, con, heure_minute));
                }
                
            }
            return messages;

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return null;
        }
    }
}
