package com.medok.app.model

data class EmailAddress(
    val email: String
) {
    init {
        require(isValidEmail(email)) { "Invalid email address" }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return emailRegex.matches(email)
    }

    override fun toString(): String {
        return email.trim()
    }
}