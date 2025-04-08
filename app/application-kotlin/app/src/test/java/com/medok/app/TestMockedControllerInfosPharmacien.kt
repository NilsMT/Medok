package com.medok.app

import com.medok.app.data.DAO.RequestManager
import com.medok.app.data.Entity.Infos_Pharmacien
import com.medok.app.data.Entity.Response
import com.medok.app.data.controller.Pharmacien_Controller
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before


class TestMockedControllerInfosPharmacien {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var controller: Pharmacien_Controller
    val mockData = listOf(
        Infos_Pharmacien(2, "M.",
        "Doe", "John", 44200,
        "Nantes", 123456789,
        null, "test.email@gmail.com")
    )


    @Before
    fun Setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        RequestManager.serverAdress = mockWebServer.url("/api/v0").toString().removeSuffix("/")

        controller = Pharmacien_Controller()
    }

    @After
    fun Teardown() {
        mockWebServer.shutdown()
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetAll_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getAll(0) }
    }

    @Test
    fun Test_GetAll_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getAll(1, 0) }
    }

    @Test
    fun Test_GetAll_ExpectsData() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(1, 1, 1, mockData))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getAll()

        assertEquals(1, result.size)
        assertEquals("Doe", result[0].nom_exercice)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByID_ReturnsCorrectPharmacist() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(1, 1, 1, mockData))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByID(2)
        assertEquals("Doe", result?.nom_exercice)
    }

    @Test
    fun Test_GetByID_ReturnsNull() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(0, 1, 1, listOf<Infos_Pharmacien>()))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByID(999)

        assertEquals(null, result)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByPrenom_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByPrenom("John",0) }
    }

    @Test
    fun Test_GetByPrenom_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByPrenom("John",1, 0) }
    }

    @Test
    fun Test_GetByPrenom_EmptyString(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByPrenom("") }
    }

    @Test
    fun Test_GetByPrenom_ReturnsCorrectPharmacist() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(1, 1, 1, mockData))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByPrenom("John")

        assertEquals(1, result.size)
        assertEquals("Doe", result[0].nom_exercice)
    }

    @Test
    fun Test_GetByPrenom_ReturnsEmptyList() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(0, 1, 1, listOf<Infos_Pharmacien>()))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByPrenom("Bob")

        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByNom_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByNom("Doe",0) }
    }

    @Test
    fun Test_GetByNom_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByNom("Doe",1, 0) }
    }

    @Test
    fun Test_GetByNom_EmptyString(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByNom("") }
    }

    @Test
    fun Test_GetByNom_ReturnsCorrectPharmacist() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(1, 1, 1, mockData))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByNom("Doe")

        assertEquals(1, result.size)
        assertEquals("John", result[0].prenom_exercice)
    }

    @Test
    fun Test_GetByNom_ReturnsEmptyList() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(0, 1, 1, listOf<Infos_Pharmacien>()))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByNom("Layton")

        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByCodePostal_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByCodePostal(44200,0) }
    }

    @Test
    fun Test_GetByCodePostal_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByCodePostal(44200,1, 0) }
    }

    @Test
    fun Test_GetByCodePostal_ReturnsCorrectPharmacist() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(1, 1, 1, mockData))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByCodePostal(44200)

        assertEquals(1, result.size)
        assertEquals("Doe", result[0].nom_exercice)
    }

    @Test
    fun Test_GetByCodePostal_ReturnsEmptyList() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(0, 1, 1, listOf<Infos_Pharmacien>()))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByCodePostal(44200)

        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByCommune_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByLibelleCommune("Nantes",0) }
    }

    @Test
    fun Test_GetByCommune_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByLibelleCommune("Nantes",1, 0) }
    }

    @Test
    fun Test_GetByCommune_EmptyString(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByLibelleCommune("") }
    }

    @Test
    fun Test_GetByCommune_ReturnsCorrectPharmacist() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(1, 1, 1, mockData))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByLibelleCommune("Nantes")

        assertEquals(1, result.size)
        assertEquals("John", result[0].prenom_exercice)
    }

    @Test
    fun Test_GetByCommune_ReturnsEmptyList() = runBlocking {
        val jsonResponse = Json.encodeToString(Response(0, 1, 1, listOf<Infos_Pharmacien>()))
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val result = controller.getByLibelleCommune("Nantes")

        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_Request_TimesOut(){
        mockWebServer.enqueue(
            MockResponse()
                .setSocketPolicy(SocketPolicy.NO_RESPONSE) // Timeout de 15s défini dans request manager
        )

        assertThrows(RuntimeException::class.java) { runBlocking { controller.getAll() } }
    }
}