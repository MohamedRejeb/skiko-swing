import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font


class Shape {
    private var x1: Int
    private var x2: Int
    private var y1: Int
    private var y2: Int

    var color: Color
        private set
    private var fillColor: Color? = null
    var stroke: BasicStroke
        private set
    var message: String? = null
        private set

    var transparency: Boolean = false

    var shape: Int
        private set
    var font: Font? = null
        private set

    var group: Int = 0


    constructor(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        color: Color,
        stroke: BasicStroke,
        shape: Int,
        fill: Color?,
        transparent: Boolean
    ) {
        this.x1 = x1
        this.x2 = x2
        this.y1 = y1
        this.y2 = y2
        this.color = color
        this.stroke = stroke
        this.shape = shape
        this.group = 0
        this.fillColor = fill
        this.transparency = transparent
    }

    constructor(
        x1: Int,
        y1: Int,
        fontSize: Int,
        font: Font?,
        color: Color,
        stroke: BasicStroke,
        shape: Int,
        message: String?
    ) {
        this.x1 = x1
        this.y1 = y1
        this.y2 = 0
        this.font = font
        this.x2 = fontSize
        this.color = color
        this.stroke = stroke
        this.shape = shape
        this.group = 0
        this.message = message
    }

    constructor(x1: Int, y1: Int, x2: Int, y2: Int, color: Color, stroke: BasicStroke, shape: Int, group: Int) {
        this.x1 = x1
        this.x2 = x2
        this.y1 = y1
        this.y2 = y2
        this.color = color
        this.stroke = stroke
        this.shape = shape
        this.group = group
    }

    fun getx1(): Int {
        return this.x1
    }

    fun getx2(): Int {
        return this.x2
    }

    fun gety1(): Int {
        return this.y1
    }

    fun gety2(): Int {
        return this.y2
    }

    fun getfillColor(): Color? {
        return this.fillColor
    }
}
