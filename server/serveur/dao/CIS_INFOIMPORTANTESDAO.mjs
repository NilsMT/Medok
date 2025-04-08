import {mongoose} from 'mongoose';
import CIS_INFOIMPORTANTESModel from '../model/CIS_INFOIMPORTANTESModel.mjs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';
import fs from 'node:fs';

const schema = new mongoose.Schema({
    codeCIS: {type: Number, required: true},
    dateDebutInfoSecu : {type:Date,required:true},
    dateFinInfoSecu : {type:Date,required:true},
    textInfoSecu : {type:String,required:true},
    lienInfoSecu: {type:String,required:true}
})

const MongoCIS_INFOIMPORTANTES = new mongoose.model('CIS_INFOIMPORTANTESCollection',schema)
const CIS_INFOIMPORTANTESDAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoCIS_INFOIMPORTANTES.deleteMany({})
     
        try{
         
            //obligatoire, pour gérer les environnemments linux et Windows.
            const directory= path.join('data','tables-utf8');
            //pour pouvoir lire le fichier de façon dynamique
            const files= await fs.promises.readdir(directory);
            const filePath=files.filter(file => file.match(/CIS_InfoImportantes_[0-9]{14}_bdpm\.txt/))
            
                
            const DATA=await DAO_ABSTRACT.readTxtFile(directory + "/" + filePath[0])
         
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
                
                //exctraction des texts et des liens.
                let textAndLink=separateTextAndLink(DATA[i][3])
                //on passe certaines fonctions afin de rendre l'element plus propre et lisible.
                const elementClear= new CIS_INFOIMPORTANTESModel({
                    codeCIS: DATA[i][0],
                    dateDebutInfoSecu: yearMonthDayStringToDate(DATA[i][1],true),
                    dateFinInfoSecu: yearMonthDayStringToDate(DATA[i][2],true),
                    textInfoSecu: textAndLink[0],
                    lienInfoSecu: textAndLink[1]
                })
                
                await MongoCIS_INFOIMPORTANTES.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.')    

        }catch(e){
            throw new Error("Erreur:" + e.message);
        }
       
    },

    
    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async ()=> {
        const data = await MongoCIS_INFOIMPORTANTES.find({})
        return data.map((element)=>new CIS_INFOIMPORTANTESModel(element))
    },

    findByCodeCIS : async(codecis) => {
        const line = await MongoCIS_INFOIMPORTANTES.find({codeCIS: codecis})
    }

}

//Sépare (et extrait uniquement) le lien et le texte à afficher du champ DATA[i][3] pour qu'ils soient exploitable faclilement
function separateTextAndLink(string){
    const myLink=string.match(/href='([^']*)'/)
    const myText=string.match(/>([^<]*)<\/a>/)
    
    return [myText[1],myLink[1]]
}



export {CIS_INFOIMPORTANTESDAO, MongoCIS_INFOIMPORTANTES}