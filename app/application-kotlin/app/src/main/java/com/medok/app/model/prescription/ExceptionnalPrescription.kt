package com.medok.app.model.prescription

import com.medok.app.model.Doctor
import com.medok.app.model.Medicine
import com.medok.app.model.Organization
import kotlinx.datetime.LocalDate

data class ExceptionnalPrescription(
    val medicine: Medicine,
    val doctor: Doctor, //take care of doctor infos
    val organization: Organization, //take care of organization infos
    val redactionDate: LocalDate
)