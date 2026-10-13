# 💧 SPA — Service Pour Annonce

<p align="center"> <img src="https://skillicons.dev/icons?i=java,windows,bash" alt="Java, Windows, Bash" /> </p> <p align="center"> <img src="https://img.shields.io/badge/Architecture-MVC%20%2B%20Observer-blue?style=plastic" alt="Architecture MVC + Observer" /> <img src="https://img.shields.io/badge/R%C3%A9seau-Sockets%20TCP-orange?style=plastic" alt="Réseau Sockets TCP" /> <img src="https://img.shields.io/badge/Licence-MIT-green?style=plastic" alt="Licence MIT" /> </p>

> **SPA** est un tchat réseau multi-clients basé sur une architecture client-serveur robuste en Java. Le projet a été conçu de manière incrémentale en appliquant les bonnes pratiques de conception (POO, MVC, Observer, DAO) jusqu'à la mise en réseau multithreadée.
>
> 💡 *Pourquoi **SPA** ?* Le nom fait référence à la thématique de l'hydratation et du flux de données : on y **pourre** (envoie) des messages dans une **citerne** (serveur) pour les **siroter** (recevoir) au fil de la **tuyauterie** (sockets TCP).

<small><small>Aussi "SPA" fait référence à l'eau minérale de Spa, une célèbre marque d'eau belge exploitée à Spa en province de Liège par la société Spa Monopole </small></small>

---

## 🛠️ Prérequis & Installation

### Ce qu'il faut installer

- **Java Runtime Environment (JRE)** ou **JDK 17+**
  - **Où l'installer ?** Sur la machine hébergeant le serveur ET sur chaque poste client.
  - **Lien de téléchargement :** [Adoptium / Eclipse Temurin](https://adoptium.net/)

### Lancement des applications

Les programmes ne nécessitent aucun argument en ligne de commande au démarrage. Les paramètres de connexion sont demandés de façon interactive via la console (`Scanner`).

- **Pour démarrer le serveur :**
  Exécuter `Start_Server.bat` ou lancer la commande suivante dans le dossier du serveur :

  ```bash
  java -jar SpaServer.jar
  ```

- **Pour démarrer un client :**
  Exécuter `Start_Client.bat` ou lancer la commande suivante :

  ```bash
  java -jar SpaClient.jar
  ```

---

## 📂 Structure du Projet

```text
C:.
│   Build.bat
│   SPA.jar
│   SpaClient.jar
│   SpaServer.jar
│
├───bin
│   └───spa
│       ├───client
│       │       ChatConsoleView.class
│       │       ControlerTchat.class
│       │       SpaClient.class
│       │       SpaLauncherTest.class
│       │       SpaMessageSender.class
│       │       SpaServerListener.class
│       │
│       ├───dao
│       │       DAOMessage.class
│       │       MessageFileDao.class
│       │
│       ├───model
│       │       ChatRoom.class
│       │       Message.class
│       │
│       ├───observer
│       │       ChatObserver.class
│       │
│       └───server
│               SpaClientHandler.class
│               SpaServerApp.class
│
└───src
    ├───spa
    │   ├───client
    │   │       ChatConsoleView.java
    │   │       ControlerTchat.java
    │   │       SpaClient.java
    │   │       SpaLauncherTest.java
    │   │       SpaMessageSender.java
    │   │       SpaServerListener.java
    │   │
    │   ├───dao
    │   │       DAOMessage.java
    │   │       MessageFileDao.java
    │   │
    │   ├───data
    │   │
    │   ├───model
    │   │       ChatRoom.java
    │   │       Message.java
    │   │
    │   ├───observer
    │   │       ChatObserver.java
    │   │
    │   └───server
    │           SpaClientHandler.java
    │           SpaServerApp.java
    │
    └───SPA_LAUNCH
        ├───CLIENT
        │       SpaClient.jar
        │       Start_Client.bat
        │
        └───SERVER
            │   SpaServer.jar
            │   Start_Server.bat
            │
            └───data
                (lieux où vos "logs" des serveurs seront mis, sous forme de fichier texte)
```

---

## 🌐 Déploiement & Méthodes de Communication

Pour échanger entre plusieurs machines distantes, deux configurations sont possibles :

### 1. Sur le même réseau local (LAN / Wi-Fi)

- Le client renseigne l'adresse IPv4 locale du serveur (ex : `192.168.x.x`) et le port défini.

### 2. À distance via Internet (Tunnel TCP)

Pour contourner la gestion de la box internet sans ouverture de ports (Port Forwarding), l'utilisation d'un tunnelier SSH TCP est recommandée :

- **Outil :** Pinggy (aucune installation nécessaire sur les postes clients).
- **Lancement du tunnel sur le serveur :**

  ```bash
  ssh -p 443 -R0:localhost:9287 TON_TOKEN+tcp@pro.pinggy.io
  ```

- Les clients saisissent ensuite l'URL hôte générée (ex : `votre-tunnel.run.pinggy.io`) et le port attribué.

---

## 🔬 Outils de Test & Inspection Réseau (Telnet / Netcat)

Le serveur communiquant en TCP brut, vous pouvez vous y connecter sans passer par l'exécutable `SpaClient.jar` via des outils de bas niveau :

```bash
# Via Telnet
telnet localhost 9287

# Via Netcat
nc localhost 9287
```

### 💡 Point éducatif : Parsing & Tolérance aux fautes de protocole

Le protocole du serveur attend un format strict pour l'injection des messages (ex : `expediteur;contenu`).

- **Que se passe-t-il avec `SpaClient.jar` ?** L'application cliente encapsule et formate correctement les données avant l'envoi.
- **Que se passe-t-il avec `telnet` ou `nc` ?** Si l'utilisateur saisit une chaîne ne respectant pas la syntaxe requise (`expediteur;contenu`), le gestionnaire de socket du serveur (`SpaClientHandler`) détecte la mauvaise donnée (une **fuite**). Pour des raisons de stabilité du thread, le serveur interrompt le flux d'écriture de cette socket.
- **Résultat :** L'utilisateur déconnecté du flux d'émission peut toujours **écouter** les messages entrants diffusés par la Citerne (socket en lecture seule), mais ne peut plus en **envoyer** sans réouvrir une nouvelle connexion.

---

## ⚠️ Limitations & Bugs Connus

- **Déconnexions intempestives :** En cas de forte charge ou d'inactivité prolongée sur la socket, le serveur peut parfois fermer la connexion abruptement.
- **Gestion du reconnect :** Le client ne gère pas la reconnection automatique en cas de rupture de socket ; il est nécessaire de relancer `SpaClient.jar`.

---

## 📄 Licence & Copyright

© 2026 Nathanaël Dos Santos (***supfoxlevrai**). Tous droits réservés.

Projet sous licence **MIT**.
