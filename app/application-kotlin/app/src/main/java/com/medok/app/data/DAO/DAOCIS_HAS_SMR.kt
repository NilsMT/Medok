package com.medok.app.data.objectbox.DAO

import android.content.res.AssetManager
import com.medok.app.data.DAO.RequestManager
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.Entity.CIS_HAS_ASMR
import com.medok.app.data.objectbox.Entity.CIS_HAS_SMR

class DAOCIS_HAS_SMR:DAOInterface<CIS_HAS_SMR> {
    val manager = RequestManager

    override fun findAll(page : Int, size : Int): List<CIS_HAS_SMR> {
        val result : Response<CIS_HAS_SMR>
            = manager.getRequestResult("/CIS_HAS_SMR?page=$page&size=$size")
        return result.data
    }

    fun findByID(code : Long): CIS_HAS_SMR? {
        val result : Response<CIS_HAS_SMR>
                = manager.getRequestResult("/CIS_HAS_SMR/cis/$code")
        return result.data.firstOrNull()
    }
}