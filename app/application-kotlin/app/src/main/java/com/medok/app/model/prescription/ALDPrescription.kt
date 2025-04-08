package com.medok.app.model.prescription

import com.medok.app.model.Doctor
import com.medok.app.model.Organization
import kotlinx.datetime.LocalDate

data class ALDPrescription(
    val aldRelatedMedicines: List<PrescribedMedicine>,
    val aldUnrelatedMedicines: List<PrescribedMedicine>,
    val doctor: Doctor, //take care of doctor infos
    val organization: Organization, //take care of organization infos
    val redactionDate: LocalDate
)