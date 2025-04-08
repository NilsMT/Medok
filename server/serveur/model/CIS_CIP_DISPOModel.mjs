export default class CIS_CIP_DISPOModel{
    codeCIS
    codeCIP13
    codeStatut
    libelleStatut
    dateDebut
    dateMAJ
    dateRemiseDispo
    lienANSM
  
    constructor(obj){
        this.codeCIS=obj.codeCIS
        this.codeCIP13=obj.codeCIP13
        this.codeStatut=obj.codeStatut
        this.libelleStatut=obj.libelleStatut
        this.dateDebut=obj.dateDebut
        this.dateMAJ=obj.dateMAJ
        this.dateRemiseDispo=obj.dateRemiseDispo
        this.lienANSM=obj.lienANSM
    }
}