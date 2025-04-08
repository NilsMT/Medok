package com.medok.app.model.prescription

/*
data class SimplePrescription(
    val medicines: List<PrescribedMedicine>,
    val doctor: Doctor, //take care of doctor infos
    val organization: Organization, //take care of address
    val redactionDate: LocalDate
)
*/

//Simplified for the prototype
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import kotlin.random.Random

@Entity
data class SimplePrescription(
    @Id var id: Long = 0,
    var doctorName: String = "",
    var doctorPhoneNumber: String ="",
    var address: String = "",
    var redactionDate: String = ""
) {
    lateinit var medicines : ToMany<PrescribedMedicine>

    override fun toString(): String {
        val meds = medicines.joinToString("\n\n") {
            "Medicine: ${it.medicine.target.name}\nPosology: ${it.posology}"
        }

        return "$id\n$doctorName $doctorPhoneNumber\n$address\nle $redactionDate\n$meds"
    }
}