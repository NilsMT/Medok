export default class Pharmacien {
    identifiant_PP
    code_civilite
    nom_exercice
    prenom_exercice
    code_postal_coord_structure
    libelle_commune_coord_structure
    telephone_coord_structure
    telephone_2_coord_structure
    adresse_email_coord_structure
    constructor(obj) {
        this.identifiant_PP = obj.identifiant_PP
        this.code_civilite = obj.code_civilite
        this.nom_exercice = obj.nom_exercice
        this.prenom_exercice = obj.prenom_exercice
        this.code_postal_coord_structure = obj.code_postal_coord_structure
        this.libelle_commune_coord_structure = obj.libelle_commune_coord_structure
        this.telephone_coord_structure = obj.telephone_coord_structure
        this.telephone_2_coord_structure = obj.telephone_2_coord_structure
        this.adresse_email_coord_structure = obj.adresse_email_coord_structure
    }
}