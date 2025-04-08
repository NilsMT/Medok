package com.medok.app.data.objectbox.Entity;


import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CIS_bdpm (
    val codeCIS:Long,
    val denomMedic:String,
    val formePharma:String,
    val voie:List<String>,
    val statutAdministratifAMM:String,
    val typeProcedureAMM:String,
    val etatCommerce:String,
    val dataAMM:LocalDate,
    val statutBDM:String,
    val numAutorisationEU:String,
    val titulaires:List<String>,
    val surveillanceRenforcee:Boolean
)
