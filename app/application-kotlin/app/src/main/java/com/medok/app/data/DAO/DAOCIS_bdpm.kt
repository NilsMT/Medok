package com.medok.app.data.objectbox.DAO

import com.medok.app.data.DAO.RequestManager
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.Entity.CIS_bdpm

class DAOCIS_bdpm:DAOInterface<CIS_bdpm> {
    val manager = RequestManager

    override fun findAll(page : Int, size : Int): List<CIS_bdpm> {
        val result : Response<CIS_bdpm>
            = manager.getRequestResult("/CIS_BDPM?page=$page&size=$size")
        return result.data
    }

    fun findByID(code : Long): CIS_bdpm? {
        val result : Response<CIS_bdpm>
                = manager.getRequestResult("/CIS_BDPM/cis/$code")
        return result.data.firstOrNull()
    }
}