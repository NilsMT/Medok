package com.medok.app.data.objectbox.Entity

import com.example.applicationsae.data.objectbox.enumClass.smrValue
import kotlinx.datetime.LocalDateTime
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class CIS_HAS_SMR(
    val codeCIS:Long,
    val codeDossierHAS:String,
    val motifEval:String,
    //attention au format de la date qui est au format AAAAMMJJ (sans tiret) dans le txt.
    val dateAvisCommisTranspa: LocalDateTime,
    val smrValue:smrValue,
    val smrLibelle:String

)
