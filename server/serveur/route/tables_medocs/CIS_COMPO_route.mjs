import express from 'express'
import CIS_COMPOController from '../../controller/CIS_COMPOCONTROLLER.mjs'

const CIS_COMPO_Router = express.Router()
const defaultRoute= "/CIS_COMPO"

CIS_COMPO_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_COMPOController.populate())
        })
    
CIS_COMPO_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CIS_COMPOController.findAll(page, size)) 
    })
        
CIS_COMPO_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CIS_COMPOController.findByCodeCIS(code)) 
    })
    
export default CIS_COMPO_Router