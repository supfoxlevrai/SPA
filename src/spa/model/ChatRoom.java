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

import java.util.ArrayList;
import java.util.List;

import spa.dao.*; //DAOMESSAGE + MESSAGE FILE DAO
import spa.observer.ChatObserver;

public class ChatRoom{
    //SalonDeDiscussion : ancien nom de la classe
    
    private String nom;
    private List<Message> messageS;
    private DAOMessage dao;
    private static final ArrayList<ChatObserver> observer = new ArrayList<>();

    public ChatRoom(String n){
        this.nom = n;
        this.dao = new MessageFileDao(this.nom);
        this.messageS = this.dao.chargerToutLesMessages() == null ? new ArrayList<>() : this.dao.chargerToutLesMessages();
    
    }

    public String getNom(){
        return this.nom;
    }

    public List<Message> getHistorique(){
        return messageS;
    }

    public Message getMessage(int i){
        if(i >= 0 && i < this.messageS.size()) return this.messageS.get(i);
        return null;
    }

    public void ajouterMessage(String expediteur, String contenu){
        Message m = new Message(expediteur, contenu);

        this.messageS.add(m);
        this.UpdateMessages(m);
        this.dao.sauvegarder(m);
    }

    public void addObserver(ChatObserver ot){
        ChatRoom.observer.add(ot);
    }

    public void addObserver(ChatObserver... OT){
        if(OT == null) return;

        for(ChatObserver ot:OT) this.addObserver(ot);

    }

    private void UpdateMessages(Message m){
        for(ChatObserver ot:ChatRoom.observer) ot.onNouveauMsg(m);
    }

    @Override
    public String toString() {
        return "SalonDeDiscussion [nom=" + nom + ", messageS=" + messageS + ", dao=" + dao + "]";
    }
}