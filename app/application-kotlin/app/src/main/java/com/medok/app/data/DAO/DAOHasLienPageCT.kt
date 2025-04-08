package com.medok.app.data.objectbox.DAO

import android.content.res.AssetManager
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.Entity.HasLienPageCT_bdpm
import io.objectbox.Box
import io.objectbox.BoxStore

class DAOHasLienPageCT : DAOInterface<HasLienPageCT_bdpm>{

    // Retourne une liste des liens stocké dans la BD
    override fun findAll(page : Int, size : Int): List<HasLienPageCT_bdpm> {
        TODO()
    }

    // Retourne une liste des liens dont leurs codes correspond au code fourni en paramètre
    fun findByCodeDossierHas(code: String): Response<HasLienPageCT_bdpm> {
        TODO()
    }
}