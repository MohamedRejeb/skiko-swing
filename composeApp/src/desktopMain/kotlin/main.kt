import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
//    System.setProperty("skiko.vsync.enabled", "false")

    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposePlayground"
    ) {
        var isSwing by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .weight(1f)
            ) {
                if (isSwing)
                    SwingApp()
                else
                    App()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isSwing,
                    onCheckedChange = { isSwing = it },
                )

                Text(
                    text = "Current: "
                )

                Text(
                    text = if (isSwing) "Swing" else "Skiko",
                )
            }
        }
    }
}