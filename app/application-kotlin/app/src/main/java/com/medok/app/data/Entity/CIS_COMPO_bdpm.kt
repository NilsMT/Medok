package com.medok.app.data.objectbox.Entity

import com.example.applicationsae.data.objectbox.enumClass.NatureComposant
import kotlinx.serialization.Serializable

@Serializable
data class CIS_COMPO_bdpm (
    val codeCIS:Long,
    val designationSubstance:String,
    val codeSubstance:Long,
    val denomSubstance:String,
    val dosageSubstance:String,
    val refDosage:NatureComposant,
    val numLiaison:Int

)