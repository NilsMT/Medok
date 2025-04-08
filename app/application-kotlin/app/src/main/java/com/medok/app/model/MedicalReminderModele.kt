package com.example.applicationsae.model

data class MedicalReminderModele(
    val id: Int,
    var name: String,
    var hour: String,
    var date: String,
    var medicines: MutableMap<String, Boolean>,
    var pathologies: MutableMap<String, Boolean>,
    var activated: Boolean,
    var recurrenceDateType: String,
    var everyRecurrence: String,
    var radioSelectedOption: String,
    var endDate: String,
    var endTimes: String,
    var recurrenceActive: Boolean
)