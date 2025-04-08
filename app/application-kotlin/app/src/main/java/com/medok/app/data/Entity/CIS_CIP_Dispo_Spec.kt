package com.medok.app.data.objectbox.Entity

import kotlinx.datetime.LocalDateTime
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class CIS_CIP_Dispo_Spec(
    val codeCIS:Long,
    val codeCIP13:Long,
    val codeStatut:Int,
    val statut:String,
    val dateDebut:LocalDateTime,
    val dateMiseAJour:LocalDateTime,
    val dateRemiseDispo:LocalDateTime,
    val linkANSM:String

)
