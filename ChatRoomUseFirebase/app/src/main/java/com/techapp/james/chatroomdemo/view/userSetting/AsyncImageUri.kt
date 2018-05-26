package com.techapp.james.chatroomdemo.view.userSetting

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

/**
 * Created by James on 2018/4/23.
 */
class AsyncImageUri : AsyncTask<Unit, Unit, Unit> {
    var context: Context
    var bitmap: Bitmap
    var progressBar: ProgressDialog? = null
var callback:UriCallback
    constructor(context: Context, bitmap: Bitmap,callback:UriCallback) {
        this.context = context
        this.bitmap = bitmap
        this.callback=callback
    }

    override fun onPreExecute() {
        super.onPreExecute()
        progressBar = ProgressDialog(context);
        progressBar!!.setMessage("Loading...")
        progressBar!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar!!.setCancelable(false);
        progressBar!!.show();
        //初始化進度條並設定樣式及顯示的資訊。
    }

    override fun doInBackground(vararg params: Unit?) {
        //progressBar!!.show();
        var uri = getImageUri(context, bitmap)
        callback.callBack(uri)
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        progressBar!!.dismiss()
        progressBar=null

    }

    override fun onProgressUpdate(vararg values: Unit?) {
        super.onProgressUpdate(*values)
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "stickerImageAfterCrop", null)
        //Insert an image and create a thumbnail for it.
        //thumbnail:a small copy of a larger picture on a computer, shown in this way to allow more to be seen on the screen
        // Log.d("File path", Uri.parse(path).toString())
        return Uri.parse(path)
    }

    interface UriCallback {
        fun callBack(uri: Uri)
    }
}