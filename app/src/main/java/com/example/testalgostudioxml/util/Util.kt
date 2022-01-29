package com.example.testalgostudioxml.util

import android.content.Context
import android.graphics.*
import androidx.core.content.res.ResourcesCompat
import com.example.testalgostudioxml.R
import kotlin.math.roundToInt
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.lang.Exception


class Util {

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
        bm.recycle()
        return resizedBitmap
    }

     fun drawTextToBitmap(
        context: Context,
        databitmap: Bitmap,
        textSize: Int = 16,
        text1: String
    ): Bitmap {

        val resources = context.resources
        val scale = resources.displayMetrics.density
        var bitmap = databitmap

        var bitmapConfig = bitmap.config;
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true)

        val canvas = Canvas(bitmap)
        // new antialised Paint
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.rgb(93, 101, 67)
        // text size in pixels
        paint.textSize = (textSize * scale).roundToInt().toFloat()
        //custom fonts
        val fontFace = ResourcesCompat.getFont(context, R.font.poppins_semibold)
        paint.typeface = Typeface.create(fontFace, Typeface.NORMAL)
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE)

        // draw text to the Canvas center
        val bounds = Rect()
        //draw the first text
        paint.getTextBounds(text1, 0, text1.length, bounds)

         val x = 1

         val _x = 1f/2*bitmap.width-10
         val _y = (1f/3*bitmap.height) * 1/2
        canvas.drawText(text1, _x, _y, paint)

        return bitmap
    }
    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(
                encodeByte, 0,
                encodeByte.size
            )
        } catch (e: Exception) {
            e.message
            null
        }
    }
}