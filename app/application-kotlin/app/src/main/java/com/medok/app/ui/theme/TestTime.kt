import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun TestTimePicker() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    OutlinedTextField(
        value = "Cliquez pour sélectionner une heure",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        println("Heure sélectionnée : $hour:$minute")
                    },
                    currentHour,
                    currentMinute,
                    true
                ).show()
            },
        label = { Text("Test TimePicker") },
        readOnly = true
    )
}
