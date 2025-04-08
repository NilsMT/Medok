import {mongoose} from 'mongoose';
import CIS_BDPM from "../model/CIS_BDPMModel.mjs";
import DAO_ABSTRACT from './DAO_ABSTRACT.mjs';
import path from 'node:path';

const schema = new mongoose.Schema({
    codeCIS: {type: Number, required: true},
    denomMedic: {type: String, required: true},
    formePharma: {type: String, required: true},
    voieAdmin: {type: [String], required: true},
    statusAdminAMM: {type: String, required: true},
    typeProcAMM: {type: String, required: true},
    etatCommerce: {type: String, required: true},
    dateAMM: {type: Date, required: true},
    statusBdm: {type: String, required: false},
    numEU: {type:String,required:false},
    titulaires: {type:[String],required:true},
    surveillanceReforce: {type:Boolean,required:true}
})

const MongoCIS_BDPM = new mongoose.model('CIS_BDPMCollection',schema)
const CIS_BDPMDAO = {

    // Remplissage de la base de données avec la data provenant du fichier source
    populate : async()=> {
        await MongoCIS_BDPM.deleteMany({})
     
        try{
         
             //obligatoire, pour gérer les environnemments linux et Windows.
            const filePath = path.join('data', 'tables-utf8', 'CIS_bdpm.txt');
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
                const elementClear= new CIS_BDPM({
                    codeCIS: DATA[i][0],
                    denomMedic: DATA[i][1],
                    formePharma: DATA[i][2],
                    voieAdmin: DAO_ABSTRACT.semicolontreatement(DATA[i][3]),
                    statusAdminAMM: DATA[i][4],
                    typeProcAMM: DATA[i][5],
                    etatCommerce: DATA[i][6],
                    dateAMM: DAO_ABSTRACT.dayMonthYearStringToDate(DATA[i][7]),
                    statusBdm: DATA[i][8],
                    numEU: DATA[i][9],
                    titulaires: DAO_ABSTRACT.semicolontreatement(DATA[i][10]),
                    surveillanceReforce: DAO_ABSTRACT.frenchBooleanTreatement(DATA[i][11])
                })
                
                await MongoCIS_BDPM.create(elementClear)
            };
            process.stdout.write('\r'+100+'%')
            console.log('\nDonnées insérées avec succès.') 

        }catch(e){
            throw new Error("Erreur:" + e.message);
        }
    },

    // Retourne une liste comprenant l'ensemble des pharmaciens
    findAll :async (page, size)=> {
        const result = await MongoCIS_BDPM.aggregate([
            DAO_ABSTRACT.createPagination(page, size)
        ]);
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];

        return DAO_ABSTRACT.jsonToReturn(page, size, metadata, data, CIS_BDPM)
    },

    findByCodeCIS : async(codecis_API) => {
        const pipeline = [{ $match: {
                codeCIS: Number(codecis_API),
                },
            },
            DAO_ABSTRACT.createPagination(1, 1)
        ]

        const result = await MongoCIS_BDPM.aggregate(pipeline)
        const metadata = result[0]?.metadata?.[0] || { totalCount: 0 };
        const data = result[0]?.data || [];
        return DAO_ABSTRACT.jsonToReturn(1, 1, metadata, data, CIS_BDPM)
    }
}


export {CIS_BDPMDAO, MongoCIS_BDPM}