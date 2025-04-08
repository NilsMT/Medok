package com.medok.app.data.objectbox.DAO

import com.medok.app.data.DAO.RequestManager
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.Entity.CIS_COMPO_bdpm

class DAOCIS_COMPO_bdpm:DAOInterface<CIS_COMPO_bdpm> {
    val manager = RequestManager

    override fun findAll(page : Int, size : Int): List<CIS_COMPO_bdpm> {
        val result : Response<CIS_COMPO_bdpm>
            = manager.getRequestResult("/CIS_COMPO?page=$page&size=$size")
        return result.data
    }

    fun findByID(code : Long): CIS_COMPO_bdpm? {
        val result : Response<CIS_COMPO_bdpm>
            = manager.getRequestResult("/CIS_COMPO/cis/$code")
        return result.data.firstOrNull()
    }
}