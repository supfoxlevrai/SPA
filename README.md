# 💧 SPA — Service Pour Annonce

<p align="center"> <img src="https://skillicons.dev/icons?i=java,windows,bash" alt="Java, Windows, Bash" /> </p> <p align="center"> <img src="https://img.shields.io/badge/Architecture-MVC%20%2B%20Observer-blue?style=plastic" alt="Architecture MVC + Observer" /> <img src="https://img.shields.io/badge/R%C3%A9seau-Sockets%20TCP-orange?style=plastic" alt="Réseau Sockets TCP" /> <img src="https://img.shields.io/badge/Licence-MIT-green?style=plastic" alt="Licence MIT" /> </p>

> **SPA** est un tchat réseau multi-clients basé sur une architecture client-serveur robuste en Java. Le projet a été conçu de manière incrémentale en appliquant les bonnes pratiques de conception (POO, MVC, Observer, DAO) jusqu'à la mise en réseau multithreadée.

> 💡 *Pourquoi **SPA** ?* Le nom fait référence à la thématique de l'hydratation et du flux de données : on y **pourre** (envoie) des messages dans une **citerne** (serveur) pour les **siroter** (recevoir) au fil de la **tuyauterie** (sockets TCP).
> ***Aussi "SPA" fait référence à l'eau minérale de Spa, une célèbre marque d'eau belge exploitée à Spa en province de Liège par la société Spa Monopole***

---

## 🛠️ Prérequis & Installation

### Ce qu'il faut installer

* **Java Runtime Environment (JRE)** ou **JDK 17+**
* **Où l'installer ?** Sur la machine hébergeant le serveur ET sur chaque poste client.
* **Lien de téléchargement :** [Adoptium / Eclipse Temurin](https://adoptium.net/)



### Lancement des applications

Les applications gèrent désormais la saisie **interactive** (via `Scanner` si aucun paramètre n'est transmis) **ET le passage d'arguments en ligne de commande** (directement via `java -jar` ou via les scripts `.bat` grâce à `%*`).

* **Pour démarrer le serveur :**
Par défaut (nom du serveur et port interactif) :
```bash
Start_Server.bat
# ou
java -jar SpaServer.jar

```
Avec un nom de serveur spécifique en argument :
```bash
Start_Server.bat MeilleurServeurDuMonde
# ou
java -jar SpaServer.jar MeilleurServeurDuMonde

```

Avec un port spécifique en argument :
```bash
Start_Server.bat 9287
# ou
java -jar SpaServer.jar 9287

```

Avec un nom de serveur et un port spécifique en argument :
```bash
Start_Server.bat MeilleurServeurDuMonde 9287
# ou
java -jar SpaServer.jar MeilleurServeurDuMonde 9287

```

* **Pour démarrer un client :**
Par défaut (invite interactive pour l'hôte, le port) :
```bash
Start_Client.bat
# ou
java -jar SpaClient.jar

```


Même logique avec le Client qu'avec le serveur :
```bash
Start_Client.bat 192.168.1.50 
# ou avec port
java -jar SpaClient.jar 192.168.1.50 9287

```
> la posibilité du client de mettre également son pseudo en arguments sera rajouté plus tard...


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
                (lieu où vos "logs" de serveur seront conservés sous forme de fichiers texte)

```

---

## 🌐 Déploiement & Méthodes de Communication

Pour échanger entre plusieurs machines distantes, deux configurations sont possibles :

### 1. Sur le même réseau local (LAN / Wi-Fi)

* Le client renseigne l'adresse IPv4 locale du serveur (ex : `192.168.x.x`) et le port défini.

### 2. À distance via Internet (Tunnel TCP)

Pour contourner la gestion de la box internet sans ouverture de ports (Port Forwarding), l'utilisation d'un tunnelier SSH TCP est recommandée :

* **Outil :** Pinggy (aucune installation nécessaire sur les postes clients).
* **Lancement du tunnel sur le serveur :**
```bash
ssh -p 443 -R0:localhost:9287 TON_TOKEN+tcp@pro.pinggy.io

```


* Les clients saisissent ensuite l'URL hôte générée (ex : `votre-tunnel.run.pinggy.io`) et le port attribué.

---

## 🔬 Outils de Test & Inspection Réseau (Telnet / Netcat)

Le serveur communiquant en TCP brut, vous pouvez vous y connecter sans passer par l'exécutable `SpaClient.jar` via des outils de bas niveau :

```bash
# Via Telnet
telnet localhost 9287

# Via Netcat
nc localhost 9287

```

### 💡 Point éducatif : Parsing & Robustesse du Serveur

Le protocole du serveur attend une syntaxe sous la forme `expediteur;contenu`.

* **Avec `SpaClient.jar` :** L'application formate automatiquement les paquets avant l'envoi.
* **Avec `telnet` ou `nc` :** Si un utilisateur envoyait une ligne sans séparateur `;` ou une chaîne vide, la découpe de tableau provoquait auparavant une `ArrayIndexOutOfBoundsException`. Cela interrompait brutalement le thread de lecture du serveur (`SpaClientHandler`), laissant le client capable de **recevoir** des messages mais bloqué en **émission** ("bloqué à la porte").
* **Correction apportée :** La vérification de la taille du tableau généré par le parsing garantit désormais qu'aucune exception n'est levée. Tout message mal formaté ou vide est neutralisé sans rompre la boucle de lecture du client.

---

## 🛠️ Améliorations Récentes & Corrections de Bugs

* **Gestion souple des arguments CLI (`%*`) :** Les fichiers `Start_Server.bat` et `Start_Client.bat` transmettent désormais la totalité de leurs arguments vers le binaire Java, permettant l'automatisation de la connexion sans passer par la saisie console.
* **Correction du crash de thread sur saisie invalide :** Sécurisation de l'accès à l'index du tableau de message au niveau du serveur pour maintenir le thread de traitement actif indépendamment du contenu envoyé.
* **Ergonomie du prompt console :*** Remplacement des libellés de saisie verbeux par un indicateur minimaliste ($>) synchronisé avec les réceptions du listener.

---

## ⚠️ Limitations & Bugs Connus

* **Déconnexions intempestives :** En cas de perte de paquet critique ou d'inactivité prolongée sur la socket, le serveur peut parfois fermer la connexion.
* **Gestion du reconnect :** Le client ne gère pas la reconnection automatique en cas de rupture de socket ; il est nécessaire de relancer `SpaClient.jar`.

---

## 📄 Licence & Copyright

© 2026 Nathanaël Dos Santos (***supfoxlevrai***). Tous droits réservés.

Projet sous licence **MIT**.
