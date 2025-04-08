package com.medok.app.model

data class Organization(
    val name: String,
    val address: String,
    val siret: String? = null,
    val finess_ico: Int? = null,
    val finess_am: Int? = null
) {
    fun hasValidSiret(siret: String): Boolean {
        return siret.all { it.isDigit() }
    }

    init {
        if (siret != null) {
            hasValidSiret(siret)
        }
    }

    override fun toString(): String {
        return name
    }
}