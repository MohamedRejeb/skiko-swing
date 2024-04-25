import androidx.compose.ui.geometry.Offset
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun simplifyPoints(
    points: List<Offset>,
    epsilon: Float,
): List<Offset> {
    if (points.size < 3) return points

    // Find the point with the maximum distance
    var dmax = 0f
    var index = 0

    for (i in 1 until points.lastIndex) {
        val d = perpendicularDistance(points[i], points[0], points[points.lastIndex])
        if (d > dmax) {
            index = i
            dmax = d
        }
    }
    // If max distance is greater than epsilon, recursively simplify
    return if (dmax > epsilon) {
        // Recursive call
        val recResults1 = simplifyPoints(points.subList(0, index + 1), epsilon)
        val recResults2 = simplifyPoints(points.subList(index, points.size), epsilon)

        // Build the result list
        listOf(recResults1.subList(0, recResults1.lastIndex), recResults2).flatMap { it.toList() }
    } else {
        listOf(points[0], points[points.lastIndex])
    }
}

private fun perpendicularDistance(pt: Offset, lineFrom: Offset, lineTo: Offset): Float =
    abs((lineTo.x - lineFrom.x) * (lineFrom.y - pt.y) - (lineFrom.x - pt.x) * (lineTo.y - lineFrom.y)) /
            sqrt((lineTo.x - lineFrom.x).pow(2) + (lineTo.y - lineFrom.y).pow(2))
