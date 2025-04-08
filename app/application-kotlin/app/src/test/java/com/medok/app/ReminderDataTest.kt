import android.content.Context
import android.content.SharedPreferences
import com.example.applicationsae.model.MedicalReminderModele
import com.google.gson.Gson
import com.medok.app.data.loadReminders
import com.medok.app.data.saveReminders
import org.junit.Test
import org.junit.Assert.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class ReminderDataTest {
    @Test
    fun testSaveReminders() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(context.getSharedPreferences("RappelsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        val reminders = listOf(
            MedicalReminderModele(
                id = 1,
                name = "Reminder 1",
                hour = "08:00",
                date = "06-02-2025",
                medicines = mutableMapOf("Med1" to true),
                pathologies = mutableMapOf("Disease1" to true),
                activated = true,
                recurrenceDateType = "Days",
                everyRecurrence = "2",
                radioSelectedOption = "Option1",
                endDate = "06-06-2025",
                endTimes = "10:00",
                recurrenceActive = true
            )
        )
        saveReminders(context, reminders)
        verify(editor).putString(eq("rappels"), anyString())
        verify(editor).apply()
    }

    @Test
    fun testLoadReminders() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val reminders = listOf(
            MedicalReminderModele(
                id = 1,
                name = "Reminder 1",
                hour = "08:00",
                date = "06-02-2025",
                medicines = mutableMapOf("Med1" to true),
                pathologies = mutableMapOf("Disease1" to true),
                activated = true,
                recurrenceDateType = "Days",
                everyRecurrence = "2",
                radioSelectedOption = "Option1",
                endDate = "06-06-2025",
                endTimes = "10:00",
                recurrenceActive = true
            )
        )
        val gson = Gson()
        val json = gson.toJson(reminders)
        `when`(context.getSharedPreferences("RappelsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString("rappels", "[]")).thenReturn(json)
        val result = loadReminders(context)
        assertNotNull(result)
        assertEquals(reminders.size, result.size)
        val reminder = result[0]
        assertEquals(reminders[0].id, reminder.id)
        assertEquals(reminders[0].name, reminder.name)
        assertEquals(reminders[0].hour, reminder.hour)
        assertEquals(reminders[0].date, reminder.date)
        assertEquals(reminders[0].medicines, reminder.medicines)
        assertEquals(reminders[0].pathologies, reminder.pathologies)
        assertEquals(reminders[0].activated, reminder.activated)
        assertEquals(reminders[0].recurrenceDateType, reminder.recurrenceDateType)
        assertEquals(reminders[0].everyRecurrence, reminder.everyRecurrence)
        assertEquals(reminders[0].radioSelectedOption, reminder.radioSelectedOption)
        assertEquals(reminders[0].endDate, reminder.endDate)
        assertEquals(reminders[0].endTimes, reminder.endTimes)
        assertEquals(reminders[0].recurrenceActive, reminder.recurrenceActive)
    }

    @Test
    fun testSaveRemindersWithCorruptedData() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(context.getSharedPreferences("RappelsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        val corruptedReminders = listOf(
            MedicalReminderModele(
                id = 1,
                name = "Reminder 1",
                hour = "invalid hour",
                date = "invalid date",
                medicines = mutableMapOf("Med1" to true),
                pathologies = mutableMapOf("Disease1" to true),
                activated = true,
                recurrenceDateType = "Days",
                everyRecurrence = "2",
                radioSelectedOption = "Option1",
                endDate = "invalid endDate",
                endTimes = "invalid time",
                recurrenceActive = true
            )
        )
        assertThrows(Exception::class.java) {
            saveReminders(context, corruptedReminders)
        }
        verify(sharedPreferences, never()).edit()
    }

    @Test
    fun testLoadRemindersWithCorruptedData() {
        val context = mock(Context::class.java)
        val sharedPreferences = mock(SharedPreferences::class.java)
        val corruptedJson = "{ 'invalidJson' : 'this is wrong' }"
        `when`(context.getSharedPreferences("RappelsPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString("rappels", "[]")).thenReturn(corruptedJson)
        assertThrows(Exception::class.java) {
            loadReminders(context)
        }
    }
}
