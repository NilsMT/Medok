package com.medok.app.data.objectbox.Entity

import kotlinx.datetime.LocalDateTime
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class CIS_CIP_bdpm (
    val codeCIS:Long,
    val codeCIP7:Long,
    val libellePres:String,
    val statutAdminPres:String,
    val etatCommercialisation:String,
    val dateDeclaCommercilisation: LocalDateTime,
    val codeCIP13:Long,
    val agrementCollectivite:Boolean,
    val tauxRembourse:String,
    val prixMedic:Double,
    val prixAndHono:Double,
    val honoraireDispens:Double

    )