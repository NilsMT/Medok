import express from 'express'
import CIS_CPDController from '../../controller/CIS_CPDCONTROLLER.mjs'

const CIS_CPD_Router = express.Router()
const defaultRoute= "/CIS_CPD"

CIS_CPD_Router
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await CIS_CPDController.populate())
        })
    
CIS_CPD_Router
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await CIS_CPDController.findAll(page, size)) 
    })

CIS_CPD_Router
    .route(defaultRoute + '/cis/:cis')
    .get(async (req,res)=> {
        const code = req.params["cis"];
        res.status(200).send(await CIS_CPDController.findByCodeCIS(code)) 
    })
        
export default CIS_CPD_Router