package com.app.cameraxwork.mvccamera.util

import android.graphics.*
import android.text.TextUtils
import java.io.File
import java.io.FileOutputStream
import java.util.*

class Helper {
    /*fun canvasTextBitmap(
        originalBitmap: Bitmap,
        pathImage: String?,
        record: com.vigilant.mcsidekicksdk.data.ScanMultiInfo?
    ) {
        val file = File(pathImage)
        try {
            val out = FileOutputStream(file)
            val canvas = Canvas(originalBitmap)
            val paint = Paint()
            paint.color = -1
            paint.textSize = 20.0f
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
            var strTime: String
            var boundsTime: Rect
            if (record != null && !TextUtils.isEmpty(record.Latitude) && !TextUtils.isEmpty(record.Longitude)) {
                strTime = record.Latitude + ", " + record.Longitude
                boundsTime = Rect()
                paint.getTextBounds(strTime, 0, strTime.length, boundsTime)
                canvas.drawText(strTime, 10.0f, (boundsTime.height() + 10).toFloat(), paint)
            }
            strTime = com.vigilant.mcsidekicksdk.utilities.DfcGlobalFunctions.getLocalTime(
                Date().time,
                "yyyy-MM-dd HH:mm a"
            )
            boundsTime = Rect()
            paint.getTextBounds(strTime, 0, strTime.length, boundsTime)
            canvas.drawText(
                strTime,
                (originalBitmap.width - boundsTime.width() - 10).toFloat(),
                (boundsTime.height() + 10).toFloat(), paint
            )
            canvas.drawBitmap(originalBitmap, 0.0f, 0.0f, paint)
            originalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
        } catch (var10: Exception) {
            var10.printStackTrace()
        }
    }*/
}