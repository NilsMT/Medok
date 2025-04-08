package com.medok.app.data.objectbox.DAO

import com.medok.app.data.DAO.RequestManager
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.Entity.CIS_HAS_ASMR

class DAOCIS_HAS_ASMR:DAOInterface<CIS_HAS_ASMR> {
    val manager = RequestManager

    override fun findAll(page : Int, size : Int): List<CIS_HAS_ASMR> {
        val result : Response<CIS_HAS_ASMR>
            = manager.getRequestResult("/CIS_HAS_ASMR?page=$page&size=$size")
        return result.data
    }

    fun findByID(code : Long): CIS_HAS_ASMR? {
        val result : Response<CIS_HAS_ASMR>
            = manager.getRequestResult("/CIS_HAS_ASMR/cis/$code")
        return result.data.firstOrNull()
    }
}