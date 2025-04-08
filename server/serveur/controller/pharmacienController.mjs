import { pharmacienDAO } from '../dao/pharmacienDAO.mjs'

const pharmacienControlleur = {
    populate : async () => await pharmacienDAO.populate(),
    findAll : async (page, size) => await pharmacienDAO.findAll(page, size),
    findByID : async (id) => await pharmacienDAO.findByID(id),
    findByPrenom : async (prenom, page, size) => await pharmacienDAO.findByPrenom(prenom, page, size),
    findByNom : async (nom, page, size) => await pharmacienDAO.findByNom(nom, page, size),
    findByCodePostal : async (code, page, size) => await pharmacienDAO.findByCodePostal(code, page, size),
    findByLibelleCommune : async (commune, page, size) => await pharmacienDAO.findByLibelleCommune(commune, page, size)
}

export default pharmacienControlleur