package com.medok.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.medok.app.data.Entity.Infos_Pharmacien
import com.medok.app.data.controller.Pharmacien_Controller
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class TestControllerInfosPharmacien {
    private val controller = Pharmacien_Controller()
    private val examplePharmacien = Infos_Pharmacien(10000059153,"MME",
        "SILVIE","MURIEL",64701,
        "Hendaye",559480800,null,
        "")

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
        val result = controller.getAll(1,25)
        assertEquals(25, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetAll_PageChangesData() = runBlocking {
        val result = controller.getAll(2,25)
        assertNotEquals(examplePharmacien, result.first())
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByID_ReturnsCorrectPharmacist() = runBlocking {
        val result = controller.getByID(10000059153)
        assertEquals("SILVIE", result?.nom_exercice)
    }

    @Test
    fun Test_GetByID_ReturnsNull() = runBlocking {
        val result = controller.getByID(-1)

        assertEquals(null, result)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByPrenom_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByPrenom("Muriel",0) }
    }

    @Test
    fun Test_GetByPrenom_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByPrenom("Muriel",1, 0) }
    }

    @Test
    fun Test_GetByPrenom_EmptyString(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByPrenom("") }
    }

    @Test
    fun Test_GetByPrenom_ReturnsCorrectPharmacist() = runBlocking {
        val result = controller.getByPrenom("Muriel", 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByPrenom_ReturnsSamePharmacist_DespiteLowercase() = runBlocking {
        val result = controller.getByPrenom("muriel", 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByPrenom_ReturnsMultiplePharmacists() = runBlocking {
        val result = controller.getByPrenom("Muriel", 1, 10)
        assertTrue(result.size in 2..10)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByPrenom_PageChangesData() = runBlocking {
        val result = controller.getByPrenom("Muriel", 2, 1)
        assertEquals(1, result.size)
        assertNotEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByPrenom_ReturnsEmptyList() = runBlocking {
        val result = controller.getByPrenom("abcdefghijklmnopqrstuvwxyz")
        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByNom_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByNom("Silvie",0) }
    }

    @Test
    fun Test_GetByNom_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByNom("Silvie",1, 0) }
    }

    @Test
    fun Test_GetByNom_EmptyString(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByNom("") }
    }

    @Test
    fun Test_GetByNom_ReturnsCorrectPharmacist() = runBlocking {
        val result = controller.getByNom("Silvie", 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByNom_ReturnsSamePharmacist_DespiteLowercase() = runBlocking {
        val result = controller.getByNom("silvie", 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByNom_ReturnsMultiplePharmacists() = runBlocking {
        val result = controller.getByNom("Silvie", 1, 10)
        assertTrue(result.size in 2..10)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByNom_PageChangesData() = runBlocking {
        val result = controller.getByNom("Silvie", 2, 1)
        assertEquals(1, result.size)
        assertNotEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByNom_ReturnsEmptyList() = runBlocking {
        val result = controller.getByNom("abcdefghijklmnopqrstuvwxyz")
        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByCodePostal_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByCodePostal(64701,0) }
    }

    @Test
    fun Test_GetByCodePostal_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByCodePostal(64701,1, 0) }
    }

    @Test
    fun Test_GetByCodePostal_ReturnsCorrectPharmacist() = runBlocking {
        val result = controller.getByCodePostal(64701, 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCodePostal_ReturnsMultiplePharmacists() = runBlocking {
        val result = controller.getByCodePostal(64701, 1, 10)
        assertTrue(result.size in 2..10)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCodePostal_PageChangesData() = runBlocking {
        val result = controller.getByCodePostal(64701, 2, 1)
        assertEquals(1, result.size)
        assertNotEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCodePostal_ReturnsEmptyList() = runBlocking {
        val result = controller.getByCodePostal(0)
        assertEquals(0, result.size)
    }

    /*-------------------------------------------------------------------------------------------*/

    @Test
    fun Test_GetByCommune_InvalidPage(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByLibelleCommune("Hendaye",0) }
    }

    @Test
    fun Test_GetByCommune_InvalidSize(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByLibelleCommune("Hendaye",1, 0) }
    }

    @Test
    fun Test_GetByCommune_EmptyString(){
        assertThrows(IllegalArgumentException::class.java) { controller.getByLibelleCommune("") }
    }

    @Test
    fun Test_GetByCommune_ReturnsCorrectPharmacist() = runBlocking {
        val result = controller.getByLibelleCommune("Hendaye", 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCommune_ReturnsSamePharmacist_DespiteLowercase() = runBlocking {
        val result = controller.getByLibelleCommune("hendaye", 1, 1)
        assertEquals(1, result.size)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCommune_ReturnsMultiplePharmacists() = runBlocking {
        val result = controller.getByLibelleCommune("Hendaye", 1, 10)
        assertTrue(result.size in 2..10)
        assertEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCommune_PageChangesData() = runBlocking {
        val result = controller.getByLibelleCommune("Hendaye", 2, 1)
        assertEquals(1, result.size)
        assertNotEquals(examplePharmacien, result.first())
    }

    @Test
    fun Test_GetByCommune_ReturnsEmptyList() = runBlocking {
        val result = controller.getByLibelleCommune("abcdefghijklmnopqrstuvwxyz")
        assertEquals(0, result.size)
    }
}