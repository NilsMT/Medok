import express from 'express'
import CIS_CIP_DISPOController from '../../controller/CIS_CIP_DispoCONTROLLER.mjs'

const CIS_CIP_Dispo_Router = express.Router()
const defaultRoute= "/CIS_CIP_Dispo"

CIS_CIP_Dispo_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_CIP_DISPOController.populate())
        })
    
CIS_CIP_Dispo_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        res.status(200).send(await CIS_CIP_DISPOController.findAll()) 
    })
        
export default CIS_CIP_Dispo_Router