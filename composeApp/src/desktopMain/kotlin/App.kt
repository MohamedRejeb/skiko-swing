import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val path = remember { Path() }

    var update by remember { mutableIntStateOf(0) }

    var lastEventTime = remember { 0L }

    var useWithFrameMillis by remember { mutableStateOf(false) }

    LaunchedEffect(useWithFrameMillis) {
        if (useWithFrameMillis) {
            while (true) {
                withFrameMillis {  }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            path.moveTo(it.x, it.y)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            path.lineTo(change.position.x, change.position.y)

                            val diff = change.uptimeMillis - lastEventTime

                            if (diff > 20) {
                                update++
                                lastEventTime = change.uptimeMillis
                            }
                        },
                        onDragEnd = {
                            path.reset()
                            update++
                        },
                        onDragCancel = {
                            path.reset()
                            update++
                        }
                    )
                }
        ) {
            update

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(
                    width = 5f,
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = useWithFrameMillis,
                onCheckedChange = { useWithFrameMillis = it },
            )

            Text(
                text = "useWithFrameMillis: $useWithFrameMillis"
            )
        }
    }
}