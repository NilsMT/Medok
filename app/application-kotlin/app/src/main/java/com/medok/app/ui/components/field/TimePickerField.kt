import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.theme.BlueLight
import java.util.Calendar


@Composable
fun TimePickerField(hour: String = "Sélectionnez l'heure", onHourSelected: (String) -> Unit) {
    val time = remember { mutableStateOf(hour) }
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TimePickerDialog(
            LocalContext.current,
            { _, hour, minute ->
                time.value = String.format("%02d:%02d", hour, minute)
                onHourSelected(time.value)
                showDialog = false
            },
            currentHour, currentMinute, true
        ).apply {
            setOnCancelListener {
                showDialog = false
            }
        }.show()
    }
    CustomButton(
        "Heure du rappel : " + time.value,
        { showDialog = true },
        buttonColor = BlueLight
    )
}
