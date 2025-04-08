package com.medok.app.data.objectbox

import io.objectbox.Box
import io.objectbox.BoxStore
import android.content.Context
import com.medok.app.model.Medicine
import com.medok.app.model.Medicine_
import com.medok.app.model.MyObjectBox
import com.medok.app.model.prescription.PrescribedMedicine
import com.medok.app.model.prescription.PrescribedMedicine_
import com.medok.app.model.prescription.SimplePrescription
import io.objectbox.kotlin.boxFor


object LocalDatabaseManager {
    private lateinit var boxStore: BoxStore

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context).build()
    }

    private fun getBoxStore(): BoxStore = boxStore

    fun getMedicineBox(): Box<Medicine> = boxStore.boxFor()
    fun getPrescribedMedicineBox(): Box<PrescribedMedicine> = boxStore.boxFor()
    fun getPrescriptionBox(): Box<SimplePrescription> = boxStore.boxFor()

    fun addMedicine(medicine : Medicine){
        val medicineBox = getMedicineBox()
        val data = medicineBox.query(Medicine_.name.equal(medicine.name)).build()
        if (data == null){
            medicineBox.put(medicine)
        }
    }

    fun addPrescribedMedicine(prescribedMed : PrescribedMedicine){
        val prescribedMedBox = getPrescribedMedicineBox()
        prescribedMedBox.put(prescribedMed)
    }

    fun addPrescription(prescription: SimplePrescription){
        val prescriptionBox = getPrescriptionBox()
        prescriptionBox.put(prescription)
    }

    fun addPrescribedMedToPrescription(id: Long, prescribedMed: PrescribedMedicine){
        val prescriptionBox = getPrescriptionBox()
        val prescribedMedicineBox = getPrescribedMedicineBox()
        val medicineBox = getMedicineBox()
        val prescription = prescriptionBox.get(id) ?: return


        println(prescribedMed.medicine.target.id)
        println(prescribedMed.medicine.target.name)
        println("${prescribedMed.id}")

        medicineBox.put(prescribedMed.medicine.target)
        prescribedMedicineBox.put(prescribedMed) // Enregistrer s'il est nouveau

        prescription.medicines.add(prescribedMed)
        prescriptionBox.put(prescription)
        println(prescriptionBox.all)

        val updatedPrescription = getPrescriptionBox().get(prescription.id)
        println(updatedPrescription?.medicines?.forEach { it.medicine.target.name })
    }

    fun deletePrescription(id : Long){
        val prescriptionBox = getPrescriptionBox()
        val prescribedMedBox = getPrescribedMedicineBox()

        val prescription = prescriptionBox.get(id)
        if (prescription != null) {
            // Supprimer tous les PrescribedMedicine associés
            prescribedMedBox.remove(prescription.medicines)

            // Supprimer la prescription elle-même
            prescriptionBox.remove(prescription)

            println("Prescription with ID $id deleted successfully!")
        } else {
            println("Prescription $id not found!")
        }
    }

    //Fonction de debug
    fun clearAllData(context: Context) {
        getMedicineBox().removeAll()
        getPrescribedMedicineBox().removeAll()
        getPrescriptionBox().removeAll()
        boxStore.close()
        init(context)
    }
}