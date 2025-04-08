package com.medok.app.model

//La classe pour les pathologies
data class Pathology(override val name: String) : ListItem(name) {
    override fun toString(): String {
        return super.toString()
    }
}