import java.awt.BasicStroke
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JPanel
import javax.swing.SwingUtilities

class PaintPanel(
) : JPanel(), MouseListener, MouseMotionListener {

    private var stroke = BasicStroke(2f)
    var canvas: BufferedImage? = null
    var graphics2D: Graphics2D? = null

    private var shapes: Stack<Shape>? = null
    private var removed: Stack<Shape>? = null
    private var preview: Stack<Shape>? = null

    private var grouped = 0

    var x1: Int = 0
    var y1: Int = 0
    var x2: Int = 0
    var y2: Int = 0

    private var dragged = false
    private var currentColor: Color? = null
    private var fillColor: Color? = null
    private var transparent = false

    private var inkPanelWidth = 0
    private var inkPanelHeight = 0

    init {
        //this.setPreferredSize(new Dimension(300,300));
        val dim = Toolkit.getDefaultToolkit().screenSize
        inkPanelWidth = dim.width - 150
        inkPanelHeight = dim.height - 160
        this.setSize(inkPanelWidth - 3, inkPanelHeight - 3) //the 3 accounts for the sp scroller
        this.preferredSize = Dimension(inkPanelWidth - 3, inkPanelHeight - 3)
        this.layout = null
        isDoubleBuffered = true
        setLocation(10, 10)
        background = Color.WHITE
        currentColor = Color.BLACK
        this.fillColor = Color.white
        isFocusable = true
        requestFocus()
        this.addMouseListener(this)
        this.addMouseMotionListener(this)
        this.shapes = Stack()
        this.removed = Stack()
        this.grouped = 1
        this.preview = Stack()
        this.transparent = true

        //if the mouse is pressed it sets the oldX & oldY
        //coordinates as the mouses x & y coordinates

        //while the mouse is dragged it sets currentX & currentY as the mouses x and y
        //then it draws a line at the coordinates
        //it repaints it and sets oldX and oldY as currentX and currentY
    }

    public override fun paintComponent(g: Graphics) {
        if (canvas == null) {
            canvas = BufferedImage(inkPanelWidth, inkPanelHeight, BufferedImage.TYPE_INT_ARGB)
            graphics2D = canvas!!.createGraphics()
            graphics2D!!.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            clear()
        }
        g.drawImage(canvas, 0, 0, null)
        val g2 = g as Graphics2D

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        for (s in shapes!!) {
            g2.color = s.color
            g2.stroke = s.stroke
            g2.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2())
        }
        if (preview!!.size > 0) {
            val s = preview!!.pop()
            g2.color = s.color
            g2.stroke = s.stroke
            g2.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2())
        }
    }

    fun clear() {
        graphics2D!!.paint = Color.white
        graphics2D!!.fillRect(0, 0, size.width, size.height)
        shapes!!.removeAllElements()
        removed!!.removeAllElements()
        repaint()
        graphics2D!!.color = currentColor
    }

    override fun mouseDragged(e: MouseEvent) {
        var primary = currentColor
        var secondary = fillColor
        if (SwingUtilities.isRightMouseButton(e)) {
            primary = secondary
            secondary = currentColor
        }
        x2 = e.x
        y2 = e.y
        dragged = true

        shapes!!.push(Shape(x1, y1, x2, y2, primary!!, stroke, 1, grouped))
        repaint()
        x1 = x2
        y1 = y2
    }

    override fun mouseMoved(e: MouseEvent) {}

    override fun mouseClicked(e: MouseEvent) {}

    override fun mouseEntered(e: MouseEvent) {}

    override fun mouseExited(e: MouseEvent) {}

    override fun mousePressed(e: MouseEvent) {
        x1 = e.x
        y1 = e.y
    }

    override fun mouseReleased(e: MouseEvent) {
        grouped++

        dragged = false
        removed!!.removeAllElements()
        repaint()
    }

    companion object {
        private const val serialVersionUID = 2032329974746950013L
    }
}