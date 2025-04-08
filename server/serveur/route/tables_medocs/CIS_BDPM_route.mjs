import express from 'express'
import CISBDPMController from '../../controller/CIS_BDPMCONTROLLER.mjs'

const CIS_BDPM_Router = express.Router()
const defaultRoute= "/CIS_BDPM"

CIS_BDPM_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CISBDPMController.populate())
        })
    
CIS_BDPM_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CISBDPMController.findAll(page, size)) 
    })

CIS_BDPM_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CISBDPMController.findByCodeCIS(code)) 
    })
        
export default CIS_BDPM_Router