export default class CIS_CIP{
    codeCIS
    codeCIP7
    libellePres
    statutAdminPres
    etatCommerce
    dateDeclaCommerce
    codeCIP13
    agrementCollect
    tauxRembourse
    prixMedic
    prixMedicAndOuvreDroit
    indicOuvreDroitRembourse
  
    constructor(obj){
        this.codeCIS=obj.codeCIS
        this.codeCIP7=obj.codeCIP7
        this.libellePres=obj.libellePres
        this.statutAdminPres=obj.statutAdminPres
        this.etatCommerce=obj.etatCommerce
        this.dateDeclaCommerce=obj.dateDeclaCommerce
        this.codeCIP13=obj.codeCIP13
        this.agrementCollect=obj.agrementCollect
        this.tauxRembourse=obj.tauxRembourse
        this.prixMedic=obj.prixMedic
        this.prixMedicAndOuvreDroit=obj.prixMedicAndOuvreDroit
        this.indicOuvreDroitRembourse=obj.indicOuvreDroitRembourse
    }
}