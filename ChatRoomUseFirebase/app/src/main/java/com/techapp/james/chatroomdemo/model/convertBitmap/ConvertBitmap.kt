package com.techapp.james.chatroomdemo.model.convertBitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Created by James on 2018/4/17.
 */
object ConvertBitmap {
    fun BitMapToString(bitmap: Bitmap): String {
        val ByteStream = ByteArrayOutputStream()
        //quality : 0-100。 0 mean compressQuality 100%，100 mean not to compressQuality
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteStream)
        val b = ByteStream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun StringToBitMap(encodedString: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            var bitmapArray: ByteArray
            bitmapArray = Base64.decode(encodedString, Base64.DEFAULT)
            //opts is mean to compressQuality bitmap
            val opts = BitmapFactory.Options()
            opts.inSampleSize = 1
            //f set to a value > 1, requests the decoder to subsample the original image, returning a smaller image to save memory.
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.size, opts)
            return bitmap
        } catch (e: Exception) {
            return null
        }

    }
}