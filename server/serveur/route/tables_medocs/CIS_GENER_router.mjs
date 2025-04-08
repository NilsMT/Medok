import express from 'express'
import CIS_GENERController from '../../controller/CIS_GENERCONTROLLER.mjs'

const CIS_GENER_Router = express.Router()
const defaultRoute= "/CIS_GENER"

CIS_GENER_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_GENERController.populate())
        })
    
CIS_GENER_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CIS_GENERController.findAll(page, size)) 
    })

CIS_GENER_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CIS_GENERController.findByCodeCIS(code)) 
    })
        
export default CIS_GENER_Router