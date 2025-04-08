import {mongoose} from 'mongoose';
import CIS_CIP_DISPOModel from '../model/CIS_CIP_DISPOModel.mjs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';

const schema = new mongoose.Schema({
    codeCIS: {type:Number,required:true},
    codeCIP13: {type:Number,required:false},
    codeStatut: {type:Number,required:true},
    libelleStatut: {type:String,required:true},
    dateDebut: {type:Date,required:true},
    dateMAJ : {type:Date,required:true},
    dateRemiseDispo : {type:Date,required:false},
    lienANSM: {type:String,required:true}
})

const MongoCIS_CIP_Dispo = new mongoose.model('CIS_CIP_DispoCollection',schema)
const CIS_CIP_DispoDAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoCIS_CIP_Dispo.deleteMany({})
     
        try{
         
             //obligatoire, pour gérer les environnemments linux et Windows.
            const filePath = path.join('data', 'tables-utf8', 'CIS_CIP_Dispo_Spec.txt');
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
                const elementClear= new CIS_CIP_DISPOModel({
                    codeCIS: DATA[i][0],
                    codeCIP13: DATA[i][1],
                    codeStatut:DATA[i][2],
                    libelleStatut: DATA[i][3],
                    dateDebut: DAO_ABSTRACT.dayMonthYearStringToDate(DATA[i][4]),
                    dateMAJ: DAO_ABSTRACT.dayMonthYearStringToDate(DATA[i][5]),
                    dateRemiseDispo: DAO_ABSTRACT.dayMonthYearStringToDate(DATA[i][6]),
                    lienANSM: DATA[i][7]
                })
                await MongoCIS_CIP_Dispo.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.') 

        }catch(e){
            throw new Error("Erreur:" + e.message);
            
        }
        
    },

    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async ()=> {
        const data = await MongoCIS_CIP_Dispo.find({})
        return data.map((element)=>new CIS_CIP_DISPOModel(element))
    },

    findByCodeCIS : async(codecis) => {
        const line = await MongoCIS_CIP.find({codeCIS: codecis})
    }

}

export {CIS_CIP_DispoDAO, MongoCIS_CIP_Dispo}