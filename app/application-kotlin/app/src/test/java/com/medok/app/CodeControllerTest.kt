package com.medok.app

import com.medok.app.controller.CodeController
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CodeControllerTest {

    private lateinit var codeController: CodeController

    @Before
    fun setUp() {
        codeController = CodeController()
    }

    @Test
    fun testGenerateRandomCode() {
        val code = codeController.generateRandomCode()
        assertEquals(6, code.length)
        assertTrue(code.all { it.isDigit() })
    }
}
