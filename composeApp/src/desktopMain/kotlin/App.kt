import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
@Preview
fun App() {
    val currentDrawing = remember { mutableStateListOf<Offset>() }

    val path = remember { Path() }

    var update by remember { mutableIntStateOf(0) }

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
                            currentDrawing.clear()
                            currentDrawing.add(it)
                            path.moveTo(it.x, it.y)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentDrawing.add(change.position)
                            path.lineTo(change.position.x, change.position.y)
                            update++
                        },
                        onDragEnd = {
                            currentDrawing.clear()
                            path.reset()
                            update++
                        },
                        onDragCancel = {
                            currentDrawing.clear()
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
    }
}