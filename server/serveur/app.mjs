"use strict"
import express from "express";
import swaggerUi from 'swagger-ui-express'
import swaggerJson from './swagger.json' assert {type: 'json'};
import pharmaRouter from "./route/pharmaciens_route.mjs";
import CIS_BDPM_Router from "./route/tables_medocs/CIS_BDPM_route.mjs";
import CIS_HAS_ASMR_Router from "./route/tables_medocs/CIS_HAS_ASMR_route.mjs";
import CIS_HAS_SMR_Router from "./route/tables_medocs/CIS_HAS_SMR_route.mjs";
import CIS_COMPO_Router from "./route/tables_medocs/CIS_COMPO_route.mjs";
import CIS_CIP_Router from "./route/tables_medocs/CIS_CIP_route.mjs";
import HAS_LIENSPAGECT_Router from "./route/tables_medocs/HAS_LIENSPAGECT_route.mjs";
import CIS_CPD_Router from "./route/tables_medocs/CIS_CPD_route.mjs";
import CIS_GENER_Router from "./route/tables_medocs/CIS_GENER_router.mjs";
import CIS_INFOIMPORTANTES_Router from "./route/tables_medocs/CIS_INFOIMPORTANTES_route.mjs";
import CIS_CIP_Dispo_Router from "./route/tables_medocs/CIS_CIP_Dispo.mjs";
//pour lire le .env
import dotenv from 'dotenv'


dotenv.config()
//api path
const APIPATH = process.env.API_PATH || '/api/v0'
const app = express()

//chargement des middleware
//Pour le CORS
app.use((req,res,next)=>{
    res.setHeader("Access-Control-Allow-Origin",'*');
    res.setHeader("Access-Control-Allow-Methods",'OPTIONS, GET, POST, PUT, PATCH, DELETE');
    res.setHeader("Access-Control-Allow-Headers",'Content-Type,Authorization');
    next();
})

//pour traiter les body en json
app.use(express.json())

//route pour swagger
app.use('/doc', swaggerUi.serve, swaggerUi.setup(swaggerJson))

//chargement des routes

app.use(APIPATH+'/',pharmaRouter)
app.use(APIPATH+'/',CIS_BDPM_Router)
app.use(APIPATH+'/',CIS_HAS_ASMR_Router)
app.use(APIPATH+'/',CIS_HAS_SMR_Router)
app.use(APIPATH+'/',CIS_COMPO_Router)
app.use(APIPATH+'/',CIS_CIP_Router)
app.use(APIPATH+'/',HAS_LIENSPAGECT_Router)
app.use(APIPATH+'/',CIS_CPD_Router)
app.use(APIPATH+'/',CIS_GENER_Router)
app.use(APIPATH+'/',CIS_INFOIMPORTANTES_Router)
app.use(APIPATH+'/',CIS_CIP_Dispo_Router)

//message par defaut
app.use((error,req,res,next)=>{
    console.log(error)
    const status = error.statusCode || 500
    const message = error.message
    res.status(status).json({message:message})
})

export default app;
