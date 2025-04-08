import express from 'express'
import CIS_HAS_SMRController from '../../controller/CIS_HAS_SMRCONTROLLER.mjs'

const CIS_HAS_SMR_Router = express.Router()
const defaultRoute= "/CIS_HAS_SMR"

CIS_HAS_SMR_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_HAS_SMRController.populate())
        })
    
CIS_HAS_SMR_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CIS_HAS_SMRController.findAll(page, size)) 
    })
        
CIS_HAS_SMR_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CIS_HAS_SMRController.findByCodeCIS(code)) 
    })
    
export default CIS_HAS_SMR_Router