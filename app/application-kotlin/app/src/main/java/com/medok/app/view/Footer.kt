import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController


/**
 * Le Footer de l'application
 */
@Composable
fun Footer(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterItem(icon = Icons.Filled.DateRange, label = "Rappels", action = {navController.navigate("rappels") })
            FooterItem(icon = Icons.Filled.Info, label = "Ordonnances", action = { navController.navigate("ordonnances") })
            FooterItem(icon = Icons.Filled.Favorite, label = "Effets", action = { navController.navigate("effets") })
            FooterItem(icon = Icons.Filled.Person, label = "Profils", action = { navController.navigate("profils") })
            FooterItem(icon = Icons.Filled.Email, label = "Contacts", action = { navController.navigate("contacts") })
        }
    }
}

@Composable
fun FooterItem(icon: ImageVector, label: String, action: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = action) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Black
            )
        }
        Text(label, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 11.sp)
    }
}

