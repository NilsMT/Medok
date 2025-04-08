package com.medok.app.data.objectbox.Entity

import kotlinx.serialization.Serializable

@Serializable
data class CIS_CPD_bdpm(
    val codeCIS:Long,
    val conditionPrescriDeliv:String
)
