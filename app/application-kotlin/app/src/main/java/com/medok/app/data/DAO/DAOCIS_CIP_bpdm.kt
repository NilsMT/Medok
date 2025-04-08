package com.medok.app.data.objectbox.DAO

import com.medok.app.data.DAO.RequestManager
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.Entity.CIS_CIP_bdpm

class DAOCIS_CIP_bpdm:DAOInterface<CIS_CIP_bdpm>{
    val manager = RequestManager

    override fun findAll(page : Int, size : Int): List<CIS_CIP_bdpm> {
        val result : Response<CIS_CIP_bdpm>
            = manager.getRequestResult("/CIS_CIP?page=$page&size=$size")
        return result.data
    }

    fun findByID(code : Long): CIS_CIP_bdpm? {
        val result : Response<CIS_CIP_bdpm>
                = manager.getRequestResult("/CIS_CIP/cis/$code")
        return result.data.firstOrNull()
    }
}