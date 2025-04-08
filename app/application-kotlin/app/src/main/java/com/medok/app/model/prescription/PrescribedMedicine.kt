package com.medok.app.model.prescription

import com.medok.app.model.Medicine
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

/*data class PrescribedMedicine (
    val medicine: Medicine,
    val posology: String,
    val mention: PrescriptionMention? = null,
)*/

//Simplified for the prototype
@Entity
data class PrescribedMedicine (
    @Id var id : Long = 0,
    val posology: String = ""
){
    lateinit var medicine: ToOne<Medicine>

    override fun toString(): String {
        return "id: $id - posology: $posology  - medicine: ${medicine.target.name}"
    }
}