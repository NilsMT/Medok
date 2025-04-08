import {mongoose} from 'mongoose';
import CIS_GENERModel from '../model/CIS_GENERModel.mjs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';

const schema = new mongoose.Schema({
    idGrpGen: {type:Number,required:true},
    libelleGrpGen: {type:String,required:true},
    codeCIS: {type: Number, required: true},
    typeGen: {type:Number,required:true},
    numTri: {type:Number,required:true}
})

const MongoCIS_GENER = new mongoose.model('CIS_GENERCollection',schema)
const CIS_GENERDAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoCIS_GENER.deleteMany({})
     
        try{
         
             //obligatoire, pour gérer les environnemments linux et Windows.
            const filePath = path.join('data', 'tables-utf8', 'CIS_GENER_bdpm.txt');
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
                const elementClear= new CIS_GENERModel({
                    idGrpGen: DATA[i][0],
                    libelleGrpGen: DATA[i][1],
                    codeCIS: DATA[i][2],
                    typeGen: DATA[i][3],
                    numTri: DATA[i][4]

                })
                
                await MongoCIS_GENER.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.') 

        }catch(e){
            throw new Error("Erreur:" + e.message);   
        }
    },

    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async (page, size)=> {
        const result = await MongoCIS_GENER.aggregate([
            DAO_ABSTRACT.createPagination(page, size)
        ]);

        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, CIS_GENERModel)
    },

    findByCodeCIS : async(codecis_API) => {
        const pipeline = [{ $match: { codeCIS: Number(codecis_API)},},
            DAO_ABSTRACT.createPagination(1, 1)
        ]

        const result = await MongoCIS_GENER.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(1, 1, metadata, data, CIS_GENERModel)
    }
}

export {CIS_GENERDAO, MongoCIS_GENER}