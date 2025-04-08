package com.medok.app.model.prescription

enum class PrescriptionMention(val description: String) {
    NR("Non remboursable"),
    AR("À renouveler"),
    QSP("Quantité suffisante pour")
}