package com.medok.app.data.objectbox.Entity

import kotlinx.datetime.LocalDateTime
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class CIS_InfoImportantes_bdpm(

    val codeCIS:Long,
    val dateDebutInfoSecu: LocalDateTime,
    val dateFinInfoSecu:LocalDateTime,
    val textInfoSecu:String

    )