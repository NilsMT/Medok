package com.medok.app.data.controller

import com.medok.app.data.DAO.DAOInfos_Pharmacien

class Pharmacien_Controller {
    val dao = DAOInfos_Pharmacien()

    /**
     * Récupérer tous les pharmaciens selon une pagination spécifique
     * @param page entier supérieur à 0 de la page souhaitée
     * @param size entier supérieur à 0 de la taille de chaque page
     * @return La liste des pharmaciens à l'intérieur de la page actuelle
     */
    fun getAll(page : Int = 1, size : Int = 25) = dao.findAll(page, size)

    /**
     * Récupérer le pharmacien correspondant à un id
     * @param id long supérieur à 0
     * @return Le pharmacien correpsondant ou null
     */
    fun getByID(id : Long) = dao.findByID(id)

    /**
     * Récupérer tous les pharmaciens selon une pagination spécifique et son prénom
     * @param prenom string non vide du prénom du pharmacien
     * @param page entier supérieur à 0 de la page souhaitée
     * @param size entier supérieur à 0 de la taille de chaque page
     * @return La liste des pharmaciens correspondant au prénom et à l'intérieur de la page actuelle
     */
    fun getByPrenom(prenom : String, page : Int = 1, size : Int = 25) = dao.findByPrenom(prenom, page, size)

    /**
     * Récupérer tous les pharmaciens selon une pagination spécifique et son nom
     * @param nom string non vide du nom du pharmacien
     * @param page entier supérieur à 0 de la page souhaitée
     * @param size entier supérieur à 0 de la taille de chaque page
     * @return La liste des pharmaciens correspondant au nom et à l'intérieur de la page actuelle
     */
    fun getByNom(nom : String, page : Int = 1, size : Int = 25) = dao.findByNom(nom, page, size)

    /**
     * Récupérer tous les pharmaciens selon une pagination spécifique et son nom
     * @param codepostal entier correspondant au codepostal de la pharmacie
     * @param page entier supérieur à 0 de la page souhaitée
     * @param size entier supérieur à 0 de la taille de chaque page
     * @return La liste des pharmaciens correspondant au code postal et à l'intérieur de la page actuelle
     */
    fun getByCodePostal(codepostal : Int, page : Int = 1, size : Int = 25) = dao.findByCodepostal(codepostal, page, size)

    /**
     * Récupérer tous les pharmaciens selon une pagination spécifique et son nom
     * @param libelle string non vide correspondant à la ville de la pharmacie
     * @param page entier supérieur à 0 de la page souhaitée
     * @param size entier supérieur à 0 de la taille de chaque page
     * @return La liste des pharmaciens correspondant au libelle commune et à l'intérieur de la page actuelle
     */
    fun getByLibelleCommune(libelle : String, page : Int = 1, size : Int = 25) = dao.findByLibelleCommune(libelle, page, size)
}