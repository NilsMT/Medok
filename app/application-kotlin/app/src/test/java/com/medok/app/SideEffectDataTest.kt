import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.medok.app.data.loadSideEffects
import com.medok.app.data.saveSideEffects
import com.medok.app.model.SideEffectModele
import org.junit.Test
import org.junit.Assert.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class SideEffectDataTest {
    val listMedicines = listOf("Doliprane", "Aspirine", "Ibuprofène")
    val listPathologies = listOf("Grippe", "Migraine", "Diabète")
    val listEffects = listOf("Nausée", "Vomissement", "Maux de tête")

    @Test
    fun testSaveSideEffects() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(context.getSharedPreferences("SideEffectsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        val sideEffects = listOf(
            SideEffectModele(1, "Test1", "01/01/2025", listMedicines.associateWith { false }.toMutableMap(), listPathologies.associateWith { false }.toMutableMap(), listEffects.associateWith { false }.toMutableMap()),
            SideEffectModele(2, "Test2", "02/02/2025", listMedicines.associateWith { false }.toMutableMap(), listPathologies.associateWith { false }.toMutableMap(), listEffects.associateWith { false }.toMutableMap())
        )
        saveSideEffects(context, sideEffects)
        verify(editor).putString(eq("sideEffects"), anyString())
        verify(editor).apply()
    }

    @Test
    fun testLoadSideEffects() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val sideEffects = listOf(SideEffectModele(1, "Test1", "01/01/2025", listMedicines.associateWith { false }.toMutableMap(), listPathologies.associateWith { false }.toMutableMap(), listEffects.associateWith { false }.toMutableMap()),
            SideEffectModele(2, "Test2", "02/02/2025", listMedicines.associateWith { false }.toMutableMap(), listPathologies.associateWith { false }.toMutableMap(), listEffects.associateWith { false }.toMutableMap())
        )
        val gson = Gson()
        val json = gson.toJson(sideEffects)
        `when`(context.getSharedPreferences("SideEffectsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString("sideEffects", "[]")).thenReturn(json)
        val result = loadSideEffects(context)
        assertNotNull(result)
        assertEquals(sideEffects.size, result.size)
        assertEquals(sideEffects[0].name, result[0].name)
        assertEquals(sideEffects[1].name, result[1].name)
    }

    @Test
    fun testSaveSideEffectsWithCorruptedData() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(context.getSharedPreferences("SideEffectsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        val corruptedSideEffects = listOf(
            SideEffectModele(1, "", "01/01/2025", listMedicines.associateWith { false }.toMutableMap(), listPathologies.associateWith { false }.toMutableMap(), listEffects.associateWith { false }.toMutableMap())
        )
        assertThrows(Exception::class.java) {
            saveSideEffects(context, corruptedSideEffects)
        }
    }


    @Test
    fun testLoadSideEffectsWithCorruptedData() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val corruptedJson = "{ 'invalidJson' : 'this is wrong' }"
        `when`(context.getSharedPreferences("SideEffectsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString("sideEffects", "[]")).thenReturn(corruptedJson)
        assertThrows(Exception::class.java) {
            loadSideEffects(context)
        }
    }

}
