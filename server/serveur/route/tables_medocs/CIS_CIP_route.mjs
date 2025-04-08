import express from 'express'
import CIS_CIPController from '../../controller/CIS_CIPCONTROLLER.mjs'

const CIS_CIP_Router = express.Router()
const defaultRoute= "/CIS_CIP"

CIS_CIP_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_CIPController.populate())
        })
    
CIS_CIP_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CIS_CIPController.findAll(page, size)) 
    })

CIS_CIP_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CIS_CIPController.findByCodeCIS(code)) 
    })
    
export default CIS_CIP_Router