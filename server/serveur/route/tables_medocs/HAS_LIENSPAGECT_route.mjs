import express from 'express'
import HAS_LIENPAGESCTController from '../../controller/HAS_LIENSPAGECTCONTROLLER.mjs'

const HAS_LIENSPAGECT_Router = express.Router()
const defaultRoute= "/HAS_LIENSPAGECT"

HAS_LIENSPAGECT_Router 
    .route(defaultRoute + '/populate')
    .get(async (req, res) => {
            res.status(200).send(await HAS_LIENPAGESCTController.populate())
        })
    
HAS_LIENSPAGECT_Router 
    .route(defaultRoute + '/')
    .get(async (req,res)=> {
        let { page, size } = req.query
        page = parseInt(page, 10) || 1;
        size = parseInt(size, 10) || 25;
        res.status(200).send(await HAS_LIENPAGESCTController.findAll(page, size)) 
    })

export default HAS_LIENSPAGECT_Router