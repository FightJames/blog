package com.techapp.james.chatroomdemo.view.userSetting

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.techapp.james.chatroomdemo.R
import kotlinx.android.synthetic.main.activity_crop.*


class CropActivity : AppCompatActivity() {
    private var bmpUri: Uri? = null
    private var bitmap: Bitmap? = null
    private var clickFlag=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)
        bmpUri = intent.getParcelableExtra<Uri>(UserSettingConfigure.bitmapUri)
        Log.d("File absolut path", bmpUri.toString())
        cropImageView.setImageUriAsync(bmpUri)
        cropTextView.setOnClickListener {
            if(!clickFlag){
            bitmap = cropImageView.getCroppedImage()
            var test=AsyncImageUri(this,bitmap!!,object :AsyncImageUri.UriCallback{
                override fun callBack(uri: Uri) {
                    var i = Intent()
                    i.putExtra(UserSettingConfigure.bitmapUri, uri.toString())
                    setResult(Activity.RESULT_OK, i)
                    bitmap!!.recycle()
                    finish()
                }
            })
            if(bitmap!=null) {
                test.execute()
            }
        }
            clickFlag=true
        }
    }

//    fun bitmapToUri(): Uri? {
//        val file = File(this.filesDir.toString() + "/images/stickerImage.jpg")
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdir()
//        }
//        if (file != null) {
//            val fout: FileOutputStream
//            try {
//                fout = FileOutputStream(file)
//                bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fout)
//                fout.flush()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//            val uri = Uri.fromFile(file)
//            return uri
//        }
//        return null
//    }
}
