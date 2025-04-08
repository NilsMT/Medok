export default class CIS_COMPOModel{
    codeCIS
    designPharma
    codeSubstance
    denomSubstance
    dosageSubstance
    referenceDosage
    natureComposant
    numeroLiaisonSAFT
  
    constructor(obj){
        this.codeCIS=obj.codeCIS
        this.designPharma=obj.designPharma
        this.codeSubstance=obj.codeSubstance
        this.dosageSubstance=obj.dosageSubstance
        this.denomSubstance=obj.denomSubstance
        this.referenceDosage=obj.referenceDosage
        this.natureComposant=obj.natureComposant
        this.numeroLiaisonSAFT=obj.numeroLiaisonSAFT
    }
}