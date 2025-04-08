import express from 'express'
import CIS_HAS_ASMRController from '../../controller/CIS_HAS_ASMRCONTROLLER.mjs'

const CIS_HAS_ASMR_Router = express.Router()
const defaultRoute= "/CIS_HAS_ASMR"

CIS_HAS_ASMR_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_HAS_ASMRController.populate())
        })
    
CIS_HAS_ASMR_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CIS_HAS_ASMRController.findAll(page, size)) 
    })

CIS_HAS_ASMR_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CIS_HAS_ASMRController.findByCodeCIS(code)) 
    })
        
export default CIS_HAS_ASMR_Router