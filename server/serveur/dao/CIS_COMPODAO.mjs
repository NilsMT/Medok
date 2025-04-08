import {mongoose} from 'mongoose';
import CIS_COMPOModel from '../model/CIS_COMPOModel.mjs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';

const schema = new mongoose.Schema({
    codeCIS: {type: Number, required: true},
    designPharma: {type: String, required: true},
    codeSubstance: {type: Number, required: true},
    dosageSubstance: {type: String, required: false},
    denomSubstance: {type:String,required:false},
    referenceDosage: {type: String, required: false},
    natureComposant: {type: String, required: true},
    numeroLiaisonSAFT: {type: Number, required: true},
})

const MongoCIS_COMPO = new mongoose.model('CIS_COMPOCollection',schema)
const CIS_COMPODAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoCIS_COMPO.deleteMany({})
     
        try{
         
             //obligatoire, pour gérer les environnemments linux et Windows.
            const filePath = path.join('data', 'tables-utf8', 'CIS_COMPO_bdpm.txt');
            const DATA=await DAO_ABSTRACT.readTxtFile(filePath)
           
            //pour afficher la progression d'insertion des données.
            let percent=0

            //boucle d'insertion de données.
            console.log("Insertion des données en cours...")
            
            for (let i=0;i<DATA.length;i++){
                //pour éviter de surcharger la console d'input (on affiche que les entiers, et seulement quand on en a besoin.)
                let newpercent=Math.round(((i/DATA.length)*100))
                if (newpercent>percent){
                    percent=newpercent
                    process.stdout.write('\r'+percent+'%')
                }
                
                //on passe certaines fonctions afin de rendre l'element plus propre et lisible.
                const elementClear= new CIS_COMPOModel({
                    codeCIS: DATA[i][0],
                    designPharma: DATA[i][1],
                    codeSubstance: DATA[i][2],
                    dosageSubstance: DATA[i][3],
                    denomSubstance: DATA[i][4],
                    referenceDosage: DATA[i][5],
                    natureComposant: DATA[i][6],
                    numeroLiaisonSAFT: DATA[i][7],
                })
                
                await MongoCIS_COMPO.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.') 

        }catch(e){
            throw new Error("Erreur:" + e.message);
        }
    },

    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async (page,size)=> {
        const result = await MongoCIS_COMPO.aggregate([
            DAO_ABSTRACT.createPagination(page, size)
        ]);
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];

        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, CIS_COMPOModel)
    },

    findByCodeCIS : async(codecis_API) => {
        const pipeline = [{ $match: {
                codeCIS: Number(codecis_API),
                },
         },
            DAO_ABSTRACT.createPagination(1, 1)
        ]

        const result = await MongoCIS_COMPO.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(1, 1, metadata, data, CIS_COMPOModel)
    }
}

export {CIS_COMPODAO, MongoCIS_COMPO}