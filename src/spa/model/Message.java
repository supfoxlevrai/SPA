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

package spa.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message{
    private String expediteur;
    private String contenu;
    private LocalDateTime horodatage;

    public Message(String ex, String con){
        this.expediteur = ex;
        this.contenu = con;
        this.horodatage = LocalDateTime.now();
    }

    public Message(String ex, String con, String ldt){
        this(ex, con);
        this.horodatage = LocalDateTime.parse(ldt);
    }

    /*
    public Message(String m){
        //On admet que le String "m" est du format => horodatage;expediteur;contenu
        String[] msg = toSplit(m);
        this(msg[1], msg[2], msg[0]);
    }
    */

    public String getExpediteur() {
        return expediteur;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getHorodatage() {
        return horodatage;
    }

    public static String[] toSplit(String m){
        //On admet que le String "m" est du format => horodatage;expediteur;contenu

        return m.split(";");
    }

    public String toString(){
        DateTimeFormatter heure_minute = DateTimeFormatter.ofPattern("HH:mm");

        return "["+this.horodatage.format(heure_minute)+"] "+this.expediteur+": "+this.contenu;
    }

}