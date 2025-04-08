package com.medok.app.model

data class SideEffectModele (
    val id: Int,
    var name: String,
    var date: String,
    var medicines: MutableMap<String, Boolean>,
    var pathologies: MutableMap<String, Boolean>,
    var effects: MutableMap<String, Boolean>
)