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

import java.util.List;
import java.util.Scanner;

import spa.model.Message;
import spa.observer.ChatObserver;

public class ChatConsoleView implements ChatObserver {
    private Scanner src;

    public ChatConsoleView() {
        this.src = new Scanner(System.in);
    }

    public void afficherHistorique(List<Message> messages){
        for(Message m:messages){
            System.out.println(m);
        }
    }

    public String demanderSaisie(String label){
        System.out.print(label);
        //this.src = new Scanner(System.in);
        //Réinstancier new Scanner(System.in) à chaque appel de méthode n'est pas idéal en Java. 
        // Il vaut mieux déclarer this.src = new Scanner(System.in); une seule fois dans le constructeur 
        // et réutiliser la même instance dans demanderSaisie

        return src.nextLine();
    }

    @Override
    public void onNouveauMsg(Message m) {
       System.out.println("\r"+m);
       System.out.print("$> ");
       //ajout de \r pour revenir au début de la ligne et écraser le prompt précédent, puis affichage du prompt "$> "
       //  pour que l'utilisateur puisse continuer à saisir son message.
    }

    public void affichageText(String texte){
        System.out.println(texte);
    }

    public void fermerScanner(){
        this.src.close();
    }
}
