package com.medok.app.model

import android.telephony.PhoneNumberUtils

data class Doctor(
    val nom : String,
    val prenom : String,
    val phoneNumber: PhoneNumber? = null,
    val email: EmailAddress? = null,
    val organization: Organization,
    val rpps : Int
) {
    override fun toString(): String {
        return "$prenom $nom"
    }
}