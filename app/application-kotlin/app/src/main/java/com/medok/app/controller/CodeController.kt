package com.medok.app.controller

import com.medok.app.model.CodeSauvegarde

class CodeController {
    private val codeSauvegarde = CodeSauvegarde()

    fun generateRandomCode(): String {
        val allowedChars = "0123456789"
        val code = (1..6)
            .map { allowedChars.random() }
            .joinToString("")
        codeSauvegarde.setCode(code)
        return code
    }

    fun getCode(): String {
        return codeSauvegarde.getCode()
    }

}
