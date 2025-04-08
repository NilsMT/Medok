package com.medok.app.data.objectbox.Entity

import kotlinx.serialization.Serializable

@Serializable
data class CIS_GENER_bdpm(

    val idGrpGen:Int,
    val idLibelleGrpGen:String,
    val codeCIS:Long,
    val typeGen:Int,
    val numTri:Int

)
