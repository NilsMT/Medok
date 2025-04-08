package com.medok.app.model

data class PhoneNumber(
    val countryCode: Int,
    val number: String
) {
    init {
        require(isValidCountryCode(countryCode)) { "Invalid country code" }
        require(isValidNumber(number)) { "Invalid phone number" }
    }

    private fun isValidCountryCode(countryCode: Int): Boolean {
        return countryCode.toString().matches(Regex("^\\d{1,3}$"))
    }

    private fun isValidNumber(number: String): Boolean {
        return number.matches(Regex("^\\d{9,10}$"))
    }

    override fun toString(): String {
        val spacedNumber = number.chunked(2).joinToString(" ").trim()
        return "+$countryCode $spacedNumber"
    }
}
