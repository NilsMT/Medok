import {mongoose} from 'mongoose';
import Pharmacien from "../model/pharmacienModel.mjs";
import csv from 'csv-parser';
import fs from 'node:fs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';

const schema = new mongoose.Schema({
    identifiant_PP: {type: Number, required: true},
    code_civilite: {type: String, required: true},
    nom_exercice: {type: String, required: true},
    prenom_exercice: {type: String, required: true},
    code_postal_coord_structure: {type: Number, required: false},
    libelle_commune_coord_structure: {type: String, required: false},
    telephone_coord_structure: {type: Number, required: false},
    telephone_2_coord_structure: {type: Number, required: false},
    adresse_email_coord_structure: {type: String, required: false}
})

const MongoPharmacien = new mongoose.model('pharmacienCollection',schema)
const pharmacienDAO = {
    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoPharmacien.deleteMany({})

        const promises = [];
        return new Promise((resolve, reject) => {
        fs.createReadStream('./data/Infos_pro_sante.csv')
        .pipe(csv({ separator: '|' }))
        .on('data', async data => {
            const pharmacien = new Pharmacien({
                                identifiant_PP : data["Identifiant_PP"],
                                code_civilite : data["Code_civilité"],
                                nom_exercice : data["Nom_dexercice"],
                                prenom_exercice : data["Prénom_dexercice"],
                                code_postal_coord_structure : data["Code_postal_coord_structure"],
                                libelle_commune_coord_structure : data["Libellé_commune_coord_structure"],
                                telephone_coord_structure : DAO_ABSTRACT.keepOnlyNumbers(data["Téléphone_coord_structure"]),
                                telephone_2_coord_structure : DAO_ABSTRACT.keepOnlyNumbers(data["Téléphone_2_coord_structure"]),
                                adresse_email_coord_structure : data["Adresse_e-mail_coord_structure"]});

            promises.push(MongoPharmacien.create(pharmacien));
        }).on('end', async () => {
                // Attends que toutes les promesses de création soient résolues
                await Promise.all(promises);
                console.log("done"); // Affiche que la population est terminée
                resolve(); // Résout la promesse pour indiquer que l'opération est terminée
            });
    });
    },
    
    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async (page, size)=> {
        const result = await MongoPharmacien.aggregate([
            DAO_ABSTRACT.createPagination(page, size)
        ]);

        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, Pharmacien)
    },
    
    // Retourne une liste de pharmaciens selon l'id fourni en paramètre,
    // la recherche ne prend PAS en compte la casse.
    findByID: async (id_API)=> {
        const pipeline = [{ $match: {
                identifiant_PP: Number(id_API),
                },
            },
            DAO_ABSTRACT.createPagination(1, 1)
        ]
        const result = await MongoPharmacien.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(1, 1, metadata, data, Pharmacien)
    },

    // Retourne une liste de pharmaciens selon le prénom fourni en paramètre,
    // la recherche ne prend PAS en compte la casse.
    findByPrenom: async (prenom_API, page, size)=> {
        const pipeline = [{ $match: {
                prenom_exercice: { $regex: `^${prenom_API}$`, $options: "i" },
                },
            }, 
            DAO_ABSTRACT.createPagination(page, size)
        ]
        const result = await MongoPharmacien.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, Pharmacien)
    },

    // Retourne une liste de pharmaciens selon le nom fourni en paramètre,
    // la recherche ne prend PAS en compte la casse.
    findByNom: async (nom_API, page, size)=> {
        const pipeline = [{ $match: {
                nom_exercice: { $regex: `^${nom_API}$`, $options: "i" },
                },
            },
            DAO_ABSTRACT.createPagination(page,size)
        ]

        const result = await MongoPharmacien.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, Pharmacien)
    },

    // Retourne une liste de pharmaciens selon le code postal fourni en paramètre
    findByCodePostal: async (codePostal_API, page, size)=> {
        const pipeline = [{ $match: { 
                code_postal_coord_structure: Number(codePostal_API)
                }
            },
            DAO_ABSTRACT.createPagination(page, size)
        ]

        const result = await MongoPharmacien.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, Pharmacien)
    },

    // Retourne une liste de pharmaciens selon le libellé commune fourni en paramètre,
    // la recherche ne prend PAS en compte la casse.
    findByLibelleCommune: async (commune_API, page, size)=> {
        const pipeline = [{ $match: { libelle_commune_coord_structure: { $regex: `^${commune_API}$`, $options: "i" }},},
            DAO_ABSTRACT.createPagination(page, size)
        ]

        const result = await MongoPharmacien.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, Pharmacien)
    }
}


export {pharmacienDAO, MongoPharmacien}