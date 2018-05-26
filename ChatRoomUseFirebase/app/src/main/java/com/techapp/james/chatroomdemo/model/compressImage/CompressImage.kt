package com.techapp.james.chatroomdemo.model.compressImage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.annotation.DrawableRes
import com.techapp.james.chatroomdemo.R
import java.io.ByteArrayOutputStream

/**
 * Created by James on 2018/4/24.
 */
object CompressImage {
    fun compressQuality(uri: Uri, context: Context): Bitmap {
        var imageStream = context.contentResolver.openInputStream(uri)
        var bmp = BitmapFactory.decodeStream(imageStream)

        var baos: ByteArrayOutputStream? = ByteArrayOutputStream()
//compress Quality 100->50
        bmp.compress(Bitmap.CompressFormat.PNG, 50, baos)
        var byteArray = baos!!.toByteArray()
        baos!!.close()
        baos = null
        //size compress length and width become (origin length and width) /8
        var opts = BitmapFactory.Options()
        opts.inSampleSize = 8
        //offset mean byteArray index, Start with offset to decode until size byteArray.size
        var bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.size, opts)
        return bitmap
    }

    fun compressSize(context: Context, @DrawableRes image: Int): BitmapDrawable {
        var opts = BitmapFactory.Options()
        opts.inSampleSize = 8
        var bitmap = BitmapFactory.decodeResource(context.resources, image, opts)
        var ansImage = BitmapDrawable(bitmap)
        return ansImage
    }
}