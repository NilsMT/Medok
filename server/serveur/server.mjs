'use strict'


//pour lire le .env
import dotenv from 'dotenv'
dotenv.config()
//import du client mongodb
import {mongoose} from 'mongoose';
//import du framework express
import express from 'express'


//port serveur http
const serverPort = process.env.PORT || 8080
//mongodb url
const mongoURL = process.env.MONGO_URL || 'mongodb://localhost:27017'
//mongodb db
const mongoDB = process.env.MONGO_DB || 'tpDB'

//environnement PROD ou DEV ou TEST
const env = (new URL(import.meta.url)).searchParams.get('ENV') ||process.env.ENV || 'TEST'
console.log(`env : ${env}`)

    //connexion à la BD
    if (env=='TEST') {
        //en test le travail est en mémoire
        const {MongoMemoryServer}  = await import('mongodb-memory-server')
        const mongoServer = await MongoMemoryServer.create();
        const uri = mongoServer.getUri();
        await mongoose.connect(uri)
        console.log("Mongo on memory "+uri)
    } else {
        await mongoose.connect(mongoURL + '/' + mongoDB)
        console.log("Mongo on "+ mongoURL + '/' + mongoDB)
    }
    //import de l'application

    const {default: app}  = await import ('./app.mjs')

    //lancement du serveur http
    const server = app.listen(serverPort, () =>
        console.log(`Example app listening on port ${serverPort}`)
    )


    //Pour les interrucptions utilisateur
    for (let signal of ["SIGTERM", "SIGINT"])
        process.on(signal,  () => {
            console.info(`${signal} signal received.`)
            console.log("Closing http server.");
            server.close(async (err) => {
                console.log("Http server closed.")
                await mongoose.connection.close()
                console.log("MongoDB connection  closed.")
                process.exit(err ? 1 : 0)
            });
        });

export default server

