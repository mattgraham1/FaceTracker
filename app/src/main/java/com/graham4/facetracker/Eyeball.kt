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
        Log.d("Eyeball", "percent x: ${(imagePoint.x/imageWidth)}, y: ${(imagePoint.y/imageHeight)}")
        Log.d("Eyeball", "width: ${this.width}, imageWidth: ${imageWidth}, height: ${this.height}, imageHeight: $imageHeight")
        Log.d("Eyeball" , "scale x: " + (this.width.toFloat() / imageWidth.toFloat()) + ", scale y: " + (this.height.toFloat() / imageHeight.toFloat()))
        val scaleFactorX = this.width.toFloat() / imageWidth.toFloat()
        val scaleFactorY = this.height.toFloat() / imageHeight.toFloat()

        faceX = imagePoint.x * scaleFactorX
        faceY = imagePoint.y * scaleFactorY
        Log.d("Eyeball", "facex: ${faceX}, facey: ${faceY}")

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

//        canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), 100F, paint)
//        Log.d("Eyeball", "width/2: " + (width/2) + ", height/2: " + (height/2))

        // invert x value so eyeballs will track face on front camera.
        faceX = width - faceX
        canvas?.drawCircle(faceX, faceY, 100F, paint)
    }
}
