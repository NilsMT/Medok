package com.medok.app.data.Entity

import kotlinx.serialization.Serializable

@Serializable
data class Infos_Pharmacien (
    val identifiant_PP: Long,
    val code_civilite: String,
    val nom_exercice: String,
    val prenom_exercice: String,
    val code_postal_coord_structure: Int?,
    val libelle_commune_coord_structure: String?,
    val telephone_coord_structure: Int?,
    val telephone_2_coord_structure: Int?,
    val adresse_email_coord_structure: String?
){
    override fun equals(other: Any?): Boolean {
        if (other !is Infos_Pharmacien) return false
        if (this.identifiant_PP != other.identifiant_PP) return false
        return true
    }
}