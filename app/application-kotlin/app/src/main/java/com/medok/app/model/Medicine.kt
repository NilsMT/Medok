package com.medok.app.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

//La classe pour les médicaments
@Entity
data class Medicine(
    @Id var id : Long = 0,
    override val name: String = ""
) : ListItem(name) {
    override fun toString(): String {
        return super.toString()
    }
}