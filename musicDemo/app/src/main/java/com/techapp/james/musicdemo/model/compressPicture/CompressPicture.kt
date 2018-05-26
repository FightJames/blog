package com.techapp.james.musicdemo.model.compressPicture

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by James on 2018/3/24.
 */
class CompressPicture {
    fun compressBySize(resource: Resources, resourceId: Int, requestWidth: Int, requestHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resource, resourceId, options)
        options.inSampleSize = calculateFitSize(requestWidth, requestHeight, options)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resource, resourceId, options)
    }

    private fun calculateFitSize(reqWidth: Int, reqHeight: Int, options: BitmapFactory.Options): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}