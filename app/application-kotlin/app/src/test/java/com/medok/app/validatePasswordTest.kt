import com.medok.app.view.dialogView.validatePassword
import org.junit.Assert.assertEquals
import org.junit.Test

class validatePasswordTest {

    @Test
    fun testPasswordTooShort() {
        val result = validatePassword("Short1")
        assertEquals("Le mot de passe doit comporter au moins 8 caractères.", result)
    }

    @Test
    fun testPasswordNoSpecialCharacter() {
        val result = validatePassword("Password123")
        assertEquals("Le mot de passe doit comporter au moins un caractère spécial.", result)
    }

    @Test
    fun testPasswordValid() {
        val result = validatePassword("Password!123")
        assertEquals("", result)
    }

    @Test
    fun testPasswordEmpty() {
        val result = validatePassword("")
        assertEquals("Le mot de passe doit comporter au moins 8 caractères.", result)
    }
}
