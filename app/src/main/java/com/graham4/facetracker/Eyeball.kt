package com.graham4.facetracker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.google.firebase.ml.vision.common.FirebaseVisionPoint


class Eyeball(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var faceX: Float = 0F
    var faceY: Float = 0F

    fun upDateEyes(imagePoint: FirebaseVisionPoint, imageWidth: Int, imageHeight: Int) {
        Log.d("Eyeball", "width: ${this.width}, imageWidth: ${imageWidth}, height: ${this.height}, imageHeight: $imageHeight")
        Log.d("Eyeball" , "scale x: " + (this.width.toFloat() / imageWidth.toFloat()) + ", scale y: " + (this.height.toFloat() / imageHeight.toFloat()))
        val scaleFactorX = this.width.toFloat() / imageWidth.toFloat()
        val scaleFactorY = this.height.toFloat() / imageHeight.toFloat()

        faceX = imagePoint.x * scaleFactorX
        faceY = imagePoint.y * scaleFactorY
        Log.d("Eyeball", "facex: ${faceX}, facey: ${faceY}")

        // Clip the x / y value so the inner circle doesn't go outside the oval.
        if (faceX < 100) {
            faceX = 100F
        }
        if (faceX > (this.width - 100)) {
            faceX = (this.width - 100).toFloat()
        }
        if (faceY < 100) {
            faceY = 100F
        }
        if (faceY > (this.height - 100)) {
            faceY = (this.height - 100).toFloat()
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val width = this.width
        val height = this.height

        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 8F

        canvas?.drawColor(Color.WHITE)
        canvas?.drawOval(16F, 16F, (width-16).toFloat(), (height-16).toFloat(), paint)

        paint.style = Paint.Style.FILL

        // invert x value so eyeballs will track face on front camera.
        faceX = width - faceX
        canvas?.drawCircle(faceX, faceY, 100F, paint)
    }
}
