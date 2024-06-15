# À propos de Confera

Confera est une application complète pour mobile, [web](https://github.com/ahmeddouss/conferaWeb) et desktop, conçue pour gérer les conférences et répondre aux besoins spécifiques des entreprises. Elle offre une multitude de fonctionnalités permettant de créer et de gérer des conférences ainsi que leurs sessions de manière efficace et organisée.

# Pré-requis

## Jupyter

Jupyter est utilisé pour le [code de machine learning](machine_learning-stat.ipynb) afin de traiter nos statistiques de présence et d'évaluer plus efficacement nos sessions.

![chart (8)](https://github.com/ahmeddouss/conferaWeb/assets/118319834/340b7d82-ae16-4e05-9391-03047e4bf1c3)


## Arduino IDE

Arduino IDE est utilisé pour le [code ESP32](esp32) afin d'obtenir l'ID utilisateur lorsqu'il entre dans une session.

## Java FX

Pour exécuter la partie desktop de Confera, [intaller javafx-sdk](https://www.oracle.com/java/technologies/install-javafx-sdk.html) puis specifier son path dans le compilateur :

```sh
--module-path "C:\Users\YOURPATH\openjfx-21.0.2_windows-x64_bin-sdk\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml```

```

## Mercure

Mercure est le serveur auquel tous les utilisateurs se connectent pour recevoir des notifications en temps réel chaque fois que les données sont mises à jour.

![mercure](https://github.com/ahmeddouss/conferaWeb/assets/118319834/32ea5b68-0406-4714-a397-0deb74559bc2)

Remarque: Lorsque les deux application web et desktop marche simultaniment un code a été ajouter pour donnée la priorité de l'enregistrement dans le Web pour évité la duplication des données.

Utilisez cette commande dans PowerShell pour exécuter Mercure :

```powershell
$env:MERCURE_PUBLISHER_JWT_KEY='!ChangeThisMercureHubJWTSecretKey!'; $env:MERCURE_SUBSCRIBER_JWT_KEY='!ChangeThisMercureHubJWTSecretKey!'; .\mercure.exe run --config Caddyfile.dev
```


---
# Interfaces de Confera

![ezgif-2-4806444cf3](https://github.com/LukaTN/PIDEV3A29/assets/118319834/9dfb119e-d520-4a42-8f42-ee6a92074f8d)

### Liste des utilisateurs
- Ajouter des invitation aux utilisateurs qui n'ont pas une carte en leurs envoyant un email contenant un Qr d'invitation.
- Suprrimer une Carte ou un Qr.

### Live mode
- Suivre les entrers et les sorties des utilisateurs en temps réel.

### Chart de présence
- le temps de participation totale dans chaque session
- Si en rouge : moyenne non considéreble
- Si charte en bleu : moyenne considérable

---

# Équipe de gestion

- **Gestion des utilisateurs :** [Hamza Zayeti](https://github.com/zayatihamza)
- **Gestion des sessions et de la présence :** [Ahmed Douss](https://github.com/ahmeddouss)
- **Gestion des conférences :** [Melek Massouadi](https://github.com/LukaTN) 
- **Gestion des sponsor :** [Ali Triki](https://github.com/Alilovez)
- **Gestion des Budgets :** [Amen allah Mrabet](https://github.com/AmenAllahMrabet)
- **Gestion des evaluations :** [Jean Perial](https://github.com/AmenAllahMrabet)
