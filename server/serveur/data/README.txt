Les tables version utf8 sont disponible dans les assets du projet 


Pour vérifier un encodage du fichier:
file -i chemin/du/fichier

POUR LES UNKNOWN-8bit faire sur les fichiers :

iconv -f WINDOWS-1252 -t UTF-8 nomfichier -o newfichier.txt 

Pour harmoniser les autres fichiers:

iconv -f ISO-8859-1 UTF-8 nomfichier -o newfichier.txt 

pour le us-ASCII:
iconv -f US-ASCII UTF-8 nomfichier -o newfichier.txt 




CIS_bdpm.txt -> iso-8859-1	Caractère d'échappement tabulation
RAS (à revoir pour être sur)



CIS_CIP_bdpm.txt -> unknown-8bit Caractère d'échappement tabulation
Colonnes en + après le taux de prise en charge par l'assurance maladie: prix du médicament (normal que se soit mis),prix du medicament + honoraire de dispensiation, honoraire de dispensiation. 




CIS_CIP_Dispo_Spec.txt -> unknown-8bit Caractère d'échappement tabulation
RAS



CIS_COMPO_bdpm.txt -> iso-8859-1 Caractère d'échappement tabulation
RAS (certains médicaments sans dosage ??) à revoir


CIS_CPD_BDPM.txt -> iso-8859-1   Caractère d'échappement tabulation
RAS (que deux colonnes, le bonheur)

CIS_GENER_bdpm.txt -> unknown-8bit Caractère d'échappement tabulation
RAS



CIS_HAS_ASMR_bdpm.txt -> unknown-8bit Caractère d'échappement tabulation
colonne D, date sans / en AA MM JJ


CIS_HAS_SMR_bdpm.txt -> unknown-8bit  Caractère d'échappement tabulation
RAS

CIS_InfoImportantes_20240927104844_bdpm.txt -> iso-8859-1 Caractère d'échappement tabulation
RAS

HAS_LiensPageCT_bdpm.txt -> us-ascii Caractère d'échappement tabulation
RAS




