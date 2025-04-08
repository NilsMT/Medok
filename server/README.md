## Guide pour le démarrage du serveur:

Avec NodeJS -> npm préalablement installer:

- Avec un terminal, placez-vous dans le répertoire /serveur.

- Tapez "npm install" pour récupérer les packages utilisés par le projet.

- Tapez "npm run start" pour lancer le serveur.

- Sur un second terminal, retournez dans le même répertoire et tapez "npm run populate" pour remplir le serveur des données sources. (Ça peut prendre un moment, vous pouvez consulter le premier terminal pour observer la progression).

- Accédez aux routes du serveur avec localhost:8080/api/v0/${nom de la collection}

## A propos des dossiers comme .idea/ ou .gradle/ ,etc..

### Veuillez s'il vous plaît, lors d'un dev, ne pas faire de ```git add .``` mais de toujours utiliser ```git status``` avant pour voir vos modifications effectuées et de pouvoir controler ce que vous committez, ou pas.

### Cela ne devrait pas arriver grâce au *.gitignore* (que vous pouvez alimentez au besoin), mais si vous avez un nouveau fichier créé / un fichier modifié  alors que vous ne se savez pas du tout ce que c'est, **ne le commitez pas** !

#### *Pour la plupart, ce sont des fichiers qui sont créés automatiquement par votre instance d'android studio et spécialement créés pour **VOTRE MACHINE***
