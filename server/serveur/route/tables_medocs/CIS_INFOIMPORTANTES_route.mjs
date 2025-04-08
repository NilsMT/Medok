import express from 'express'
import CIS_INFOIMPORTANTESController from '../../controller/CIS_INFOIMPORTANTESCONTROLLER.mjs'

const CIS_INFOIMPORTANTES_Router = express.Router()
const defaultRoute= "/CIS_INFOIMPORTANTES"

CIS_INFOIMPORTANTES_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_INFOIMPORTANTESController.populate())
        })
    
CIS_INFOIMPORTANTES_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        res.status(200).send(await CIS_INFOIMPORTANTESController.findAll()) 
    })
        
export default CIS_INFOIMPORTANTES_Router