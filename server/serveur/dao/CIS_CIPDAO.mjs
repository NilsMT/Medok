import {mongoose} from 'mongoose';
import CIS_CIP from '../model/CIS_CIPModel.mjs';
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';


const schema = new mongoose.Schema({
    codeCIS: {type: Number, required: true},
    codeCIP7 : {type:Number,required:true},
    libellePres : {type:String,required:true},
    statutAdminPres : {type:String,required:true},
    etatCommerce : {type:String,required:true},
    dateDeclaCommerce : {type:Date,required:true},
    codeCIP13 : {type:Number,required:true},
    agrementCollect : {type:Boolean,required:false},
    tauxRembourse :  {type:String,required:false},
    prixMedic: {type:Number,required:false},
    prixMedicAndOuvreDroit: {type:Number,required:false},
    indicOuvreDroitRembourse: {type:Number,required:false}

})

const MongoCIS_CIP = new mongoose.model('CIS_CIPCollection',schema)
const CIS_CIPDAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoCIS_CIP.deleteMany({})
     
        try{
         
            //obligatoire, pour gérer les environnemments linux et Windows.
            const filePath = path.join('data', 'tables-utf8', 'CIS_CIP_bdpm.txt');
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
                const elementClear= new CIS_CIP({
                    codeCIS: DATA[i][0],
                    codeCIP7: DATA[i][1],
                    libellePres: DATA[i][2],
                    statutAdminPres: DATA[i][3],
                    etatCommerce: DATA[i][4],
                    dateDeclaCommerce: DAO_ABSTRACT.dayMonthYearStringToDate(DATA[i][5]),
                    codeCIP13: DATA[i][6],
                    agrementCollect: DAO_ABSTRACT.frenchBooleanTreatement(DATA[i][7]),
                    tauxRembourse: DATA[i][8],
                    prixMedic: DAO_ABSTRACT.floatTreatement(DATA[i][9]),
                    prixMedicAndOuvreDroit: DAO_ABSTRACT.floatTreatement(DATA[i][10]),
                    indicOuvreDroitRembourse: DAO_ABSTRACT.floatTreatement(DATA[i][11])
                })
                
                await MongoCIS_CIP.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.')  

        }catch(e){
            throw new Error("Erreur:" + e.message);
            
        }
        
       
    },

    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async (page,size)=> {
        const result = await MongoCIS_CIP.aggregate([
            DAO_ABSTRACT.createPagination(page, size)
        ]);
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];

        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, CIS_CIP)
    },

    findByCodeCIS : async(codecis_API) => {
        const pipeline = [{ $match: {
                codeCIS: Number(codecis_API),
                },
            },
            DAO_ABSTRACT.createPagination(1, 99)
        ]

        const result = await MongoCIS_CIP.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(1, 99, metadata, data, CIS_CIP)
    }
}

export {CIS_CIPDAO, MongoCIS_CIP}