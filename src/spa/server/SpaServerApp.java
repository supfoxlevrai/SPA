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

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;

import spa.model.ChatRoom;
//import spa.observer.ChatObserver;
//import spa.model.Message;

public class SpaServerApp {
    private String nomSalon;
    private ChatRoom sdd;
    private ExecutorService executor;
    private ServerSocket serveur;
    private int port;
    
    private static int NombreUserConnectee;
    private static Set<InetAddress> clients_deja_venu;

    public SpaServerApp(String nomSalon, int port){
        this.nomSalon = nomSalon;
        this.port = port;
        SpaServerApp.NombreUserConnectee = 0;
        SpaServerApp.clients_deja_venu = new HashSet<>();
    }

    public void start(){
        this.sdd = new ChatRoom(this.nomSalon);
        //System.out.println("CREATION DU SALON");

        this.executor = Executors.newCachedThreadPool();
        this.startNetWork();

        for(InetAddress s:SpaServerApp.clients_deja_venu){
            System.out.println(s);
            
        }
    }

    private void startNetWork(){
        System.out.println("START NETWORK");
        try {
            this.serveur = new ServerSocket(this.port);
            
            while(true){
                Socket CLIENT = this.serveur.accept();
                SpaServerApp.NombreUserConnectee++;
                SpaServerApp.clients_deja_venu.add(CLIENT.getInetAddress());

                //System.out.println("UN CLIENT A VENUE");

                SpaClientHandler ch = new SpaClientHandler(this.sdd, CLIENT);
                this.sdd.addObserver(ch);
                this.executor.submit(ch);
                System.out.println("Nombre d'Utilisateur/trice connectées : "+NombreUserConnectee);
                
                //ch.run();
            }

        } catch (IOException io) {
            System.out.println("Server Error: " + io);

        }
    }

    public synchronized static void UtilisateurDeco(Socket s){
        System.out.println("L'utilisateur : "+s+" s'est déconnecté. IP : "+s.getInetAddress());
        SpaServerApp.clients_deja_venu.remove(s.getInetAddress());
        SpaServerApp.NombreUserConnectee--;
        System.out.println("Nombre d'utilisateurs connectés : " + NombreUserConnectee);
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

    private static String[] connexionServeur(String[] args, Scanner src){
        String nomServ = "";
        int port = 0000;

        if(args.length == 0){
            System.out.println("Aucun argument fourni. Vous allez devoir saisir l'adresse IP et le port du serveur manuellement.");

            System.out.println("Nom du Serv :");
            nomServ = src.nextLine();

            while(nomServ.isEmpty()){
                System.out.println("Nom du Serv :");
                nomServ = src.nextLine();
            }

            System.out.println("Port du Serv :");
            port = src.nextInt();

            while (isNumberFormatValid(port+"") == -1) {
                    System.out.println("Port du Serv :");
                    port = src.nextInt();
            }
        } 
        else if(args.length == 1){
            System.out.println("Un seul argument fourni. Vous allez devoir saisir le port du serveur manuellement.");

            if(args[0].isEmpty()){
                System.out.println("Nom du Serv :");
                nomServ = src.nextLine();

                while (nomServ.isEmpty()) {
                    System.out.println("Nom du Serv :");
                    nomServ = src.nextLine();
                }
            }
            else{
                nomServ = args[0];
            }

            System.out.println("Port du Serv :");
            port = src.nextInt();

            while (isNumberFormatValid(port+"") == -1) {
                    System.out.println("Port du Serv :");
                    port = src.nextInt();
            }

        }
        else{
            System.out.println("Deux arguments fournis. Le nom du serveur et sont port seront utilisés tels quels (à moins qu'ils ne soient invalides).");

            if(args[0].isEmpty()){
                System.out.println("Nom du Serv :");
                nomServ = src.nextLine();

                while (nomServ.isEmpty()) {
                    System.out.println("Nom du Serv :");
                    nomServ = src.nextLine();
                }
            }
            else{
                nomServ = args[0];
            }

            if(isNumberFormatValid(port+"") == -1){
                System.out.println("Port du Serv :");
                port = src.nextInt();

                while (isNumberFormatValid(port+"") == -1) {
                    System.out.println("Port du Serv :");
                    port = src.nextInt();
                }
            }
            else{
                port = Integer.parseInt(args[1]);
            }

        }

        return new String[]{nomServ, port+""};
    }

    public static void main(String[] args) {
        Scanner src = new Scanner(System.in);
        String[] connexion = connexionServeur(args, src);
        String nom_serv = connexion[0];
        int port = Integer.parseInt(connexion[1]);

        SpaServerApp spasc = new SpaServerApp(nom_serv, port);
        spasc.start();
        src.close();
    }


}
