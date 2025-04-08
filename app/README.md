## A propos des Merge requests

Ne créez pas vos merges request via une Issue car cela créent des branches inutiles et vides, créez votre merge request vous-même dans la section "Merge requests".

Et point important : NE COCHEZ PAS la case "Squash commits" pour voir l'ajout de TOUT vos commits dans l'historique de `main` (le squash est mieux mais on a besoin de voir les commits pour savoir si la personne a travaillé )

## A propos des Commits

Ne faites pas `git add .`, faite un `git status` avant pour voir les modifications effectuées et pouvoir controller ce que vous envoyez dans votre commit.

Un `.gitignore` est aussi mis à disposition pour ignorer les dossiers comme `.idea/` ou `.gradle/`, n'hésitez pas à l'alimenter.

ℹ️ Info
> *Pour la plupart, ce sont des fichiers qui sont créés automatiquement par votre instance d'android studio et spécialement créés pour **VOTRE MACHINE***

:warning: Attention
> *N'ajoutez pas un fichier **INCONNU** à votre **commit** même s'il vient d'être créé*

## À propos des conventions à respecter au sein du projet

### Convention de nommage

Voici les règles concernant le nom des variables et des fichiers :

- les noms de fonctions (= aussi les noms de composants) et de classes doivent être écrites comme ceci: `MaFonctionSuperbe`

- les noms de variables doivent être écris comme ceci : `maVariable`

- les variables et fonctions doivent être écrites en anglais (donc `RedVif` devient `RedBright`)
  - un pop-up = `Dialog`
  - une vue / une page = `View`
  - une input = `Field`<br>
*☝️🤓 certaines exceptions peuvent avoir lieu (comme prenom et nom)*

- les mots doivent être bien choisis et doivent illustrer la nature de la fonction / de la variable (donc `POP` devient `Dialog` car c'est plus clair)

- la fonction d'un composant doit être préciser à la fin, donc `POPForgotPassword` devient `ForgotPasswordDialog` (et on rend la fonction plus clair par la même occasion)

:warning: Attention
> *Les fichiers doivent être identiques aux noms des composants / fonctions*

### Convention hiérarchique 

```
- app/src/main
    - controller 
      ℹ️ les controlleurs
    - data 
      ℹ️ tout ce qui touche à la BD
    - model 
      ℹ️ le modèle de l'appli
    - ui
        - theme 
          ℹ️ le thème, la palette de couleur
        - components
          ℹ️ les composants, peuvent être ranger dans un sous-package
    - view 
      ℹ️ les vues, peuvent être ranger dans un sous-package

- app/src/test
  ℹ️ les tests qui ne necessitent pas de l'interface android (ç.a.d test du model/BD)
 
- app/src/androidTest
  ℹ️ les tests qui necessitent l'interface android (ç.a.d test qu'au click d'un bouton ça fasse un truc)
```

# Convention sur la documentation des composants

Documentez vos fonctions, avec à minima son intérêt, de cette manière :
```kotlin
/**
 * Fenêtre de dialog (POP-UP) permettant de consulter, cocher et modifier des éléments d'une liste
 * @param selectionState liste des éléments associés à des booléens
 * @param isMutable booléen indiquant si la liste est modifiable (suppression & ajout)
 * @param onOk action à effectuer avec la liste retournée à la fin du dialog (quand l'utilisateur appuis sur Ok)
 * @param onDismiss action à faire si le dialog est annulé (quand l'utilisateur appuis sur Annuler / sur les bords)
 */
fun ListDialog(
    selectionState: MutableMap<String, Boolean>,
    isMutable: Boolean,
    onOk: (MutableMap<String, Boolean>) -> Unit,
    onDismiss: () -> Unit
) {
...
}
```
ℹ️ Info
> Il y a aussi la description des paramètres, ces descriptions ne commencent **__JAMAIS__** par un determinant, **__TOUJOURS__** par un nom