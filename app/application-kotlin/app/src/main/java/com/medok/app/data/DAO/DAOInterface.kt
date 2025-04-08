package com.medok.app.data.objectbox.DAO

import com.medok.app.data.Entity.Response


interface DAOInterface<T> {
    //Renvoies toutes les données de la table de la BD
    fun findAll(page : Int = 1, size : Int = 25): List<T>
}