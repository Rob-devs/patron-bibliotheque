# Projet Bibliothèque

## Description
Le projet Bibliothèque est une application de gestion de bibliothèque développée en Java avec l'utilisation du Framework Maven, de la base de données SQLite et de la librairie JavaFX pour l'interface utilisateur.

## Prérequis
- [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (version 17)
- [Maven](https://maven.apache.org/download.cgi) (version 3.10.1)
- [SQLite](https://www.sqlite.org/download.html) (version 3.43.2.0)

## Installation
*Les sources du projet sont récupérables sur le dépôt GitHub associé.*
1. Clonez le dépôt du projet si nécessaire :
   ```bash
   git clone https://github.com/Rob-devs/patron-bibliotheque.git
   ```

2. Accédez au répertoire du projet :
   ```bash
   cd bibliotheque
   ```

3. Compilez le projet avec Maven :
   ```bash
   mvn clean install
   ```

4. Exécutez l'application en lançant Launcher.java.

## Utilisation
1. Lancez l'application en suivant les étapes d'installation.
2. Utilisez l'interface pour effectuer des actions telles que la réservation, l'emprunt, et le rendu d'exemplaires.

## Fonctionnalités
- Gestion des usagers
- Gestion des oeuvres
- Gestion des exemplaires
- Traitement des états des exemplaires disponibles
- Prises de réservations pour une oeuvre
- Annulations de réservations
- Création d'emprunts par des usagers
- Rendu d'exemplaires empruntés avec gestion automatique des réservations

## Auteurs
- BONARO Kilian
- BRUMBTER Robin

---
**Note :** Ce projet a été réalisé dans un cadre académique.
