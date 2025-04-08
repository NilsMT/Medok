import {mongoose} from 'mongoose';
import HAS_LIENSPAGECTModel from '../model/HAS_LIENSPAGECTModel.mjs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';

const schema = new mongoose.Schema({
    codeDossierHAS: {type:String,required:true},
    lienPageAvisCT: {type:String,required:true}
})

const MongoHAS_LIENSPAGECT = new mongoose.model('HAS_LIENSPAGECTCollection',schema)
const HAS_LIENSPAGECTDAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoHAS_LIENSPAGECT.deleteMany({})
     
        try{
         
             //obligatoire, pour gérer les environnemments linux et Windows.
            const filePath = path.join('data', 'tables-utf8', 'HAS_LiensPageCT_bdpm.txt');
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
                const elementClear= new HAS_LIENSPAGECTModel({
                    codeDossierHAS: DATA[i][0],
                    lienPageAvisCT: DATA[i][1]
                })
                await MongoHAS_LIENSPAGECT.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.') 

        }catch(e){
            throw new Error("Erreur:" + e.message);
        }
    },

    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async (page, size)=> {
        const result = await MongoHAS_LIENSPAGECT.aggregate([
            DAO_ABSTRACT.createPagination(page, size)
        ]);

        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, HAS_LIENSPAGECTModel)
    },
}

export {HAS_LIENSPAGECTDAO, MongoHAS_LIENSPAGECT}