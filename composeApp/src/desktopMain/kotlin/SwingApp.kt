import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun SwingApp() {
    SwingPanel(
        factory = {
            PaintPanel()
        },
        modifier = Modifier
            .fillMaxSize()
    )
}