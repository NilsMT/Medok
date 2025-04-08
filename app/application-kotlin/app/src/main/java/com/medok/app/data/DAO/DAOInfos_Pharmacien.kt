package com.medok.app.data.DAO

import com.medok.app.data.Entity.Infos_Pharmacien
import com.medok.app.data.Entity.Response
import com.medok.app.data.objectbox.DAO.DAOInterface


class DAOInfos_Pharmacien: DAOInterface<Infos_Pharmacien> {
    val manager = RequestManager

    override fun findAll(page : Int, size : Int): List<Infos_Pharmacien> {
        /* ATTENTION: La requête ci-dessous n'est pas suppose fonctionner avec du http
        ça ne fonctionne actuellement SEULEMENT car le fichier network_security_config a été
        ajouté dans /res/xml et que la ligne android:networkSecurityConfig="@xml/network_security_config"
        a été ajoutée au manifest. Ce n'est QUE du debug.*/
        verifyPagination(page, size)
        val result : Response<Infos_Pharmacien>
            = manager.getRequestResult("/pharmacien?page=$page&size=$size")
        return result.data
    }

    fun findByID(id : Long): Infos_Pharmacien? {
        val result : Response<Infos_Pharmacien>
            = manager.getRequestResult("/pharmacien/id/$id")
        return result.data.firstOrNull()
    }

    fun findByPrenom(prenom : String, page: Int=1, size: Int=25): List<Infos_Pharmacien> {
        verifyPagination(page, size)
        if (prenom.isEmpty()) throw IllegalArgumentException("Le prénom n'est pas renseigné")
        val result : Response<Infos_Pharmacien>
            = manager.getRequestResult<Infos_Pharmacien>("/pharmacien/prenom/$prenom?page=$page&size=$size")
        return result.data
    }

    fun findByNom(nom : String, page: Int=1, size: Int=25): List<Infos_Pharmacien> {
        verifyPagination(page, size)
        if (nom.isEmpty()) throw IllegalArgumentException("Le nom n'est pas renseigné")
        val result : Response<Infos_Pharmacien>
            = manager.getRequestResult("/pharmacien/nom/$nom?page=$page&size=$size")
        return result.data
    }

    fun findByCodepostal(code : Int, page: Int=1, size: Int=25): List<Infos_Pharmacien> {
        verifyPagination(page, size)
        val result : Response<Infos_Pharmacien>
            = manager.getRequestResult("/pharmacien/codepostal/$code?page=$page&size=$size")
        return result.data
    }

    fun findByLibelleCommune(commune : String, page: Int=1, size: Int=25): List<Infos_Pharmacien> {
        verifyPagination(page, size)
        if (commune.isEmpty()) throw IllegalArgumentException("Le libellé commune n'est pas renseigné")
        val result : Response<Infos_Pharmacien>
            = manager.getRequestResult("/pharmacien/libellecommune/$commune?page=$page&size=$size")
        return result.data
    }



    private fun verifyPagination(page : Int, size : Int){
        if (page < 1 || size < 1) throw IllegalArgumentException("La pagination est invalide")
    }
}