"use strict"
import express from 'express'
import pharmacienControlleur from '../controller/pharmacienController.mjs';

const pharmaRouter = express.Router()
const defaultRoute='/pharmacien'

pharmaRouter
    .route(defaultRoute + '/')
        .get(async (req, res) => {
            let { page, size } = req.query
            page = parseInt(page, 10) || 1;
            size = parseInt(size, 10) || 1000;
            res.status(200).send(await pharmacienControlleur.findAll(page, size))
        })


pharmaRouter
    .route(defaultRoute + '/populate')
        .get(async (req, res) => {
            res.status(200).send(await pharmacienControlleur.populate())
        })        

// Génération des routes GET individuelles pour chaque champ
const fields = ['id','prenom', 'nom', 'codepostal', 'libellecommune'];
const handleGetPharmaciensByField = (field) => async (req, res) => {
    const paramValue = req.params[field];
    let { page, size } = req.query
    page = parseInt(page, 10) || 1;
    size = parseInt(size, 10) || 25;
    try {
        let queryMethod
        switch(field){
            case "id":
                queryMethod = pharmacienControlleur.findByID
                break;
            case "prenom":
                queryMethod = pharmacienControlleur.findByPrenom
                break;
            case "nom":
                queryMethod = pharmacienControlleur.findByNom
                break;
            case "codepostal":
                queryMethod = pharmacienControlleur.findByCodePostal
                break;
            case "libellecommune":
                queryMethod = pharmacienControlleur.findByLibelleCommune
                break;
            default:
                return res.status(400).send({ error: `Recherche par ${field} non prise en charge.` });
        }
        const pharmaciens = await queryMethod(paramValue, page, size);
        res.status(200).send(pharmaciens);
    } catch (err) {
        res.status(500).send({ error: `Erreur lors de la récupération des pharmaciens par ${field}.` + err });
    }
};

fields.forEach((field) => {
    pharmaRouter.route(`${defaultRoute}/${field}/:${field}`).get(handleGetPharmaciensByField(field));
});

export default pharmaRouter