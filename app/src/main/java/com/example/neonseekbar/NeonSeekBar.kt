package com.example.neonseekbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

@SuppressLint("CustomViewStyleable", "AppCompatCustomView")
class NeonSeekBar(context: Context, attrs: AttributeSet) : SeekBar(context, attrs) {

    private var anchorText: String = "0"  // Default text
    private var thumbX: Float = 0f  // Track thumb position
    private var showShadows: Boolean = false  // Default is true (show shadows)
    private var activeTrackColor: Int =
        Color.parseColor("#FFFFFF")  // Default active track color (white)
    private var inactiveTrackColor: Int =
        Color.parseColor("#2e2e2e")  // Default inactive track color (gray)
    private var thumbColor: Int = Color.parseColor("#FFFFFF")
    private var labelBackgroundColor: Int = Color.parseColor("#2e2e2e")
    private var anchorTextColor: Int = Color.parseColor("#FFFFFF")
    private var arrowColor: Int = Color.parseColor("#2e2e2e")
    private var shadowColor: Int = Color.parseColor("#FFFFFF")


    var valueFrom: Float = 0f
        set(value) {
            field = value
            updateProgress()
        }

    var valueTo: Float = 100f
        set(value) {
            field = value
            updateProgress()
        }

    var stepSize: Float = 1f
        set(value) {
            field = value
            invalidate()
        }

    var value: Int = 0
        set(value) {
            field = value.coerceIn(valueFrom.toInt(), valueTo.toInt())
            progress = ((field - valueFrom) / (valueTo - valueFrom) * max).toInt()
            invalidate()
        }


    private fun updateProgress() {
        max = ((valueTo - valueFrom) / stepSize).toInt()
        progress = ((value - valueFrom) / (valueTo - valueFrom) * max).toInt()
    }


    // Paint for inactive track
    private val inactiveTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = inactiveTrackColor
        style = Paint.Style.FILL
    }

    // Paint for active track with shadow (Top shadow)
    private val activeTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = activeTrackColor
        style = Paint.Style.FILL
    }


    private val activeTrackTopShadow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = shadowColor
        style = Paint.Style.FILL
        setShadowLayer(
            15f,
            -6f,
            6f,
            shadowColor

        )
    }

    // Paint for active track with shadow (Bottom shadow)
    private val activeTrackBottomShadow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = shadowColor
        style = Paint.Style.FILL
        setShadowLayer(
            15f,
            -6f,
            -6f,
            shadowColor
        )
    }

    // Paint for thumb with shadow
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    private val thumbPaintShadow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = shadowColor
        style = Paint.Style.FILL
        setShadowLayer(20f, -4f, 10f, shadowColor)
    }

    // Paint for thumb with front shadow
    private val frontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = shadowColor
        style = Paint.Style.FILL
        setShadowLayer(20f, 5f, 0f, shadowColor)
    }

    // Paint for the background of the anchor text (white, rounded rectangle)
    private val labelBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = labelBackgroundColor
        style = Paint.Style.FILL
        //  setShadowLayer(10f, 0f, 5f, labelBackgroundColor)
    }

    // Paint for the anchor text (green color)
    private val anchorTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 35f
        textAlign = Paint.Align.CENTER
    }

    private val activeTrackRect = RectF()

    // Paint for the arrow (pointing upwards)
    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = arrowColor
        style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = 120 + dpToPx(40f, context).toInt()
        val heightSpec = MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    fun setAnchorText(text: String) {
        this.anchorText = text
        invalidate()
    }

    fun setShowShadows(show: Boolean) {
        this.showShadows = show
        invalidate()  // Trigger a redraw when the shadow visibility changes
    }

    fun setShadowColor(color: Int) {
        shadowColor = color
        activeTrackTopShadow.color = color
        activeTrackBottomShadow.color = color
        thumbPaintShadow.color = color
        frontPaint.color = color
        //labelBackgroundPaint.color = color
        invalidate()
    }

    fun setInactiveTrackColor(color: Int) {
        inactiveTrackColor = color
        inactiveTrackPaint.color = color
        invalidate()
    }

    fun setActiveTrackColor(color: Int) {
        activeTrackColor = color
        activeTrackPaint.color = color  // Update the actual paint object
        invalidate()
    }

    fun setThumbColor(color: Int) {
        thumbColor = color
        thumbPaint.color = color
        invalidate()
    }

    fun setLabelBackgroundColor(color: Int) {
        labelBackgroundColor = color
        labelBackgroundPaint.color = color
        invalidate()
    }


    fun setArrowColor(color: Int) {
        arrowColor = color
        arrowPaint.color = color
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        val trackHeight = 10
        val padding = 40

        // Calculate progress ratio and thumb position
        val progressRatio = progress.toFloat() / max
        val activeTrackWidth = progressRatio * (width - 2 * padding)
        val thumbX = padding + activeTrackWidth

        // Draw inactive track
        canvas.drawRect(
            padding.toFloat(),
            (height / 2 - trackHeight / 2).toFloat(),
            (width - padding).toFloat(),
            (height / 2 + trackHeight / 2).toFloat(),
            inactiveTrackPaint
        )

        // Draw active track
        activeTrackRect.set(
            padding.toFloat(),
            (height / 2 - trackHeight / 2).toFloat(),
            (activeTrackWidth + padding),
            (height / 2 + trackHeight / 2).toFloat()
        )
        canvas.drawRoundRect(
            activeTrackRect,
            (trackHeight / 2).toFloat(),
            (trackHeight / 2).toFloat(),
            activeTrackPaint
        )


        // Draw custom thumb
        val thumbRadius = 20f
        canvas.drawCircle(thumbX, height / 2f, thumbRadius, thumbPaint)


        //------------anchor text with pointer and background

        // Position for the anchor text and background
        val marginDp = 15f
        val marginPx = dpToPx(marginDp, context)
        val textWidth = anchorTextPaint.measureText(anchorText)
        var textX = thumbX - (textWidth / 2)
        val textY = height / 2f - 20f - 30f - marginPx

        // Ensure the anchor text doesn't go out of bounds
        if (textX < padding) {
            textX = padding.toFloat()  // Keep the text within bounds on the left
        }
        if (textX + textWidth > width - padding) {
            textX = (width - padding - textWidth) // Keep the text within bounds on the right
        }

        // Draw background for anchor text (rounded rectangle)
        val textBackgroundTop = textY - 10f
        canvas.drawRoundRect(
            textX - 20f,
            textY - 20f,
            textX + textWidth + 20f,
            textY + 45f,
            10f,
            10f,
            labelBackgroundPaint
        )

        // Draw anchor text
        canvas.drawText(anchorText, textX + textWidth / 2, textY + 25f, anchorTextPaint)

        // Define the margin for the arrow from the thumb
        val arrowMarginDp = -24f
        val arrowMarginPx = dpToPx(arrowMarginDp, context)

        // Position the arrow right below the anchor text background with margin from the thumb
        val arrowY = textBackgroundTop - arrowMarginPx

        // Draw reversed pointing arrow (upwards)
        val arrowPath = Path().apply {
            moveTo(thumbX, arrowY)
            lineTo(thumbX - 21f, arrowY - 25f)
            lineTo(thumbX + 21f, arrowY - 25f)
            close()
        }

        // Draw the reversed arrow on the canvas
        canvas.drawPath(arrowPath, arrowPaint)

        //-------------Draw Shadow effect here------------

        if (showShadows) {
            // Draw active track
            activeTrackRect.set(
                padding.toFloat(),
                (height / 2 - trackHeight / 2).toFloat(),
                (activeTrackWidth + padding),
                (height / 2 + trackHeight / 2).toFloat()
            )
            canvas.drawRoundRect(
                activeTrackRect,
                (trackHeight / 2).toFloat(),
                (trackHeight / 2).toFloat(),
                activeTrackTopShadow
            )

            // Draw active track
            activeTrackRect.set(
                padding.toFloat(),
                (height / 2 - trackHeight / 2).toFloat(),
                (activeTrackWidth + padding),
                (height / 2 + trackHeight / 2).toFloat()
            )
            canvas.drawRoundRect(
                activeTrackRect,
                (trackHeight / 2).toFloat(),
                (trackHeight / 2).toFloat(),
                activeTrackBottomShadow
            )

            canvas.drawCircle(thumbX, height / 2f, thumbRadius, thumbPaintShadow)
            canvas.drawCircle(thumbX, height / 2f, thumbRadius, activeTrackBottomShadow)
            canvas.drawCircle(thumbX, height / 2f, thumbRadius, frontPaint)
        }


    }


    init {
        this.thumb = null
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar)

        anchorText = typedArray.getString(R.styleable.CustomSeekBar_anchorText) ?: anchorText
        valueFrom = typedArray.getFloat(R.styleable.CustomSeekBar_valueFrom, 0f)
        valueTo = typedArray.getFloat(R.styleable.CustomSeekBar_valueTo, 100f)
        value = typedArray.getFloat(R.styleable.CustomSeekBar_value, 0F).toInt()
        stepSize = typedArray.getFloat(R.styleable.CustomSeekBar_stepSize, 1f)
        activeTrackColor =
            typedArray.getColor(R.styleable.CustomSeekBar_activeTrackColor, Color.WHITE)
        inactiveTrackColor = typedArray.getColor(
            R.styleable.CustomSeekBar_inactiveTrackColor,
            ContextCompat.getColor(context, R.color.gray_shade_2e2e2e)
        )
        labelBackgroundColor = typedArray.getColor(
            R.styleable.CustomSeekBar_labelBackgroundColor,
            ContextCompat.getColor(context, R.color.gray_shade_2e2e2e)
        )
        anchorTextColor = typedArray.getColor(
            R.styleable.CustomSeekBar_anchorTextColor,
            ContextCompat.getColor(context, R.color.white)
        )
        arrowColor = typedArray.getColor(
            R.styleable.CustomSeekBar_arrowColor,
            ContextCompat.getColor(context, R.color.gray_shade_2e2e2e)
        )
        thumbColor = typedArray.getColor(
            R.styleable.CustomSeekBar_thumbColor,
            ContextCompat.getColor(context, R.color.white)
        )
        shadowColor = typedArray.getColor(
            R.styleable.CustomSeekBar_shadowColor,
            ContextCompat.getColor(context, R.color.white)
        )
        typedArray.recycle()  // Always recycle the typedArray to avoid memory leaks
        updateProgress()

    }

    // Override the setOnSeekBarChangeListener method
    override fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener?) {
        // Custom logic before calling the super method
        super.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                // Calculate the value using the progress ratio
                val progressRatio = progress.toFloat() / max.toFloat()
                val calculatedValue =
                    progressRatio.coerceIn(0f, 1f) * (valueTo - valueFrom) + valueFrom
                if (!calculatedValue.isNaN()) {
                    value = ((calculatedValue / stepSize).roundToInt() * stepSize).toInt()

                }
                thumbX = paddingStart + progressRatio * (width - paddingStart - paddingEnd)

                // Invalidate to trigger a redraw
                invalidate()
                l?.onProgressChanged(seekBar, progress, fromUser)  // Call the original listener
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                l?.onStartTrackingTouch(seekBar)  // Call the original listener
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                l?.onStopTrackingTouch(seekBar)  // Call the original listener
            }
        })
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        )
    }

}

