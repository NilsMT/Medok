package com.medok.app.model

//La classe pour les éléments d'une liste (de ListDialog)
open class ListItem(open val name: String) {
    override fun toString(): String {
        return name
    }
}