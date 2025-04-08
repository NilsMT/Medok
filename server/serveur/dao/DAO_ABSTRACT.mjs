import fs from 'node:fs';
import readline from 'node:readline'

const  DAO_ABSTRACT = {
   
    //Permet de lire un fichier TXT
    readTxtFile: async(filePath)=> {
      
        const stream = fs.createReadStream(filePath, { encoding: 'utf8' }); 
        const lines=[]

        const rl = readline.createInterface({
            input: stream,
            output: process.stdout,
            terminal: false
        });

        for await(const line of rl){
            const columns=line.split('\t')
            lines.push(columns)  
        }

        console.log("Lecture du fichier terminée, en attente de traitement.")
        
        return lines

    },

    // Renvoi le string fourni en paramètre ne gardant que ses chiffres
    keepOnlyNumbers(input) {
        return input ? input.replace(/\D+/g, '') : '';
    },

    // Retourne le morceau d'agrégation permettant la pagination sur les requêtes
    createPagination(page, size){
        return {
            $facet: {
                metadata: [{ $count: "totalCount" }],
                data: [
                    { $skip: (page - 1) * size },
                    { $limit: size },
                ],
            },
        }
    },

    // Retourne le json contenant les données à renvoyer dans le bon format
    jsonToReturn(page, size, metadata, data, Constructor){
        return {
            totalCount: metadata.totalCount,
            totalPages: Math.ceil(metadata.totalCount / size),
            currentPage: page,
            data: data.map((item) => new Constructor(item))
        };
    },


    //Certaines colonnes contiennent plusieurs valeurs séparés par des ; (voir doc du gouvernement)
    //on préfère mettre un tableau
    semicolontreatement(string){
        if (string !=""){
       
            return string.split(';');
        }else{
            return ""
        }
    
    },


    //remplace les strings "oui" et "non" de la table en vrais booleans.
    frenchBooleanTreatement(string){
        const stringtreated=string.toLowerCase()
        if (stringtreated=="oui"){

            return true
        }else if (stringtreated=="non"){
            return false
        }
        return false
    },



    //transforme une string JJ/MM/AAAA en date
    dayMonthYearStringToDate(string){
        if (string!=""){
            const regex = /^(\d{2})\/(\d{2})\/(\d{4})$/;
            const match = string.match(regex);
        if (match) {
            
            const day = match[1];
            const month = match[2] - 1;  //parce que janvier == 0 en Date js 
            const year = match[3];
    
            // Créer un objet Date avec ces valeurs
            return new Date(year, month, day);
        } else {
            throw new Error("Format de date invalide. Utilisez JJ/MM/AAAA.");
        }
        }else{
        return ""
        }
   
    },

    //transforme une string AAAAMMJJ ou AAAA/MM/JJ en date
    yearMonthDayStringToDate(string,haveSeparator){

        if (string.length === 8 && !isNaN(string)) {  
            
            if (haveSeparator){
                const regex = /^(\d{4})\-(\d{2})\-(\d{2})$/;
                const match = string.match(regex);
                const day = match[3];
                const month = match[2] - 1;  //parce que janvier == 0 en Date js 
                const year = match[1];
        
            
                return new Date(year, month, day);

            }else{

                const annee = string.substring(0, 4);
                const mois = string.substring(4, 6);
                const jour = string.substring(6, 8);
                return new Date(`${annee}-${mois}-${jour}`)
            }
        }else{
        console.error(string +" Est invalide. Veuillez entrer une date sous le format AAAAMMJJ ou AAAA/MM/JJ.")
        }

    },

    //converti un nombre a virgule string en nombre flottant
    //s'il n'est pas renseigné, on retourne -1
    floatTreatement(string){
        if (string!=""){
            //pour parse en float, pas le choix de remplacer la virgule en point
            string=string.replace(',','.')
            return parseFloat(string)
        }else{
            return -1
        }
    },
    //Traite un libellé sale (problème d'encodage extétieur, balise html...) pour qu'il soit lisible 
    libelleTreatement(string){
        //on remplace les br par un retour chariot (à interpréter)
        let stringCleaned1=string.replace(/<br>/g,"\n")

        //remplacement de tous les ? et les ? espagnols.
        let stringCleaned2=stringCleaned1.replace(/¿/g,"")
        let stringCleaned3=stringCleaned2.replace(/\?/g,"")
        //certains libellé sont comme "cités" avec des "", on veut l'enlever pour avoir un affichage plus propre.
        let stringCleaned4=stringCleaned3.replace(/"\s*/g,"")

        return stringCleaned4
    }

}
export default DAO_ABSTRACT