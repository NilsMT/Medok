package com.medok.app.data.DAO
import android.util.Log
import com.medok.app.data.Entity.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

//TODO Bouger vers controller?
object RequestManager {
    var serverAdress = "http://10.0.2.2:8080/api/v0"

    val kTorClient = HttpClient(OkHttp) {
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
        install(Logging) { /* debug mode */
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }
        install(ResponseObserver) { /* debug mode */
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }
    }

    /**
     * Exécute une requète vers la BD côté serveur
     * @param route string de la route suivant /api/v0
     * @return Une Reponse contenant des données correspondant au type du DAO utilisant cette fonction
     * @throws HttpRequestTimeoutException si le serveur ne répond pas
     */
     inline fun <reified T : Any> getRequestResult(route: String) : Response<T> {
        val url = "$serverAdress$route"
        return runBlocking(Dispatchers.IO) {
                val response = kTorClient.get(url).body<String>()
                Json{ignoreUnknownKeys = true}.decodeFromString(response)
            }
    }
}