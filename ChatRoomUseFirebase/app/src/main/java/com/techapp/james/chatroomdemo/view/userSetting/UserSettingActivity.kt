package com.techapp.james.chatroomdemo.view.userSetting

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.techapp.james.chatroomdemo.R
import com.techapp.james.chatroomdemo.model.compressImage.CompressImage
import com.techapp.james.chatroomdemo.model.thread.ImageLoad
import com.techapp.james.chatroomdemo.model.userModel.CurrentUser
import kotlinx.android.synthetic.main.activity_user_setting.*
import java.io.File
import java.util.regex.Pattern


class UserSettingActivity : AppCompatActivity() {
    private val CAMERA_REQUEST = 0
    private val GALLERY_REQUEST = 1
    private val CAMERA_RETURN = 2
    private val GALLERY_RETURN = 3
    private val CROP_RETURN = 4
    var userIconBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
        CurrentUser.dbUser!!.myName
        CurrentUser.dbUser!!.email
        backImageView.setOnClickListener {
            this.onBackPressed()
        }
        stickerImageView.setImageBitmap(CurrentUser.dbUser!!.getIcon())
        nameEditText.setText(CurrentUser.dbUser!!.myName)
        emailEditText.setText(CurrentUser.dbUser!!.email)
        stickerImageView.setOnClickListener {
            createDialog()
        }
        changeBtn.setOnClickListener {
            val check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
            val regex = Pattern.compile(check)
            val matcher = regex.matcher(emailEditText.text.toString())
            val isMatched = matcher.matches()
            if (isMatched && !(emailEditText.text.toString().equals(CurrentUser.dbUser!!.email))) {
                CurrentUser.fireBaseUser!!.updateEmail(emailEditText.text.toString())
                CurrentUser.dbUser!!.email = emailEditText.text.toString()
            }
            if (!(nameEditText.text.toString().equals("") && nameEditText.text.toString().equals(CurrentUser.dbUser!!.myName))) {
                CurrentUser.dbUser!!.myName = nameEditText.text.toString()
            }
            if ((!passwordEditText.text.toString().equals("")) && (passwordEditText.text.toString().length >= 6)) {
                CurrentUser.fireBaseUser!!.updatePassword(passwordEditText.text.toString())
            }
            if (userIconBitmap != null) {
                CurrentUser.dbUser!!.setIcon(userIconBitmap!!)
            }

            //firebaseFacade.saveUser(CurrentUser.dbUser!!)
            AsyncSave().execute()
            this.finish()
        }
    }


    var fileUri: Uri? = null
    fun createDialog() {
        var file: File? = null
        val filePath = this.filesDir.toString() + "/images/stickerImage.jpg"
        file = File(filePath)
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir()
        }

        //share file to camera
        fileUri = FileProvider.getUriForFile(this, "chatRoom.fileProvider", file!!)
        val builder = AlertDialog.Builder(this)
        //.setTitle(R.string.pick_color)
        builder.setItems(R.array.chooseImageResource, DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                CAMERA_REQUEST -> {
                    Log.d("File path ", Uri.fromFile(file).toString())
                    var i = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    startActivityForResult(i, CAMERA_RETURN)
                }
                GALLERY_REQUEST -> {
                    val photoPickerIntent = Intent(Intent.ACTION_PICK)
                    photoPickerIntent.type = "image/*"
                    startActivityForResult(photoPickerIntent, GALLERY_RETURN)
                }
            }
        })
        var dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_RETURN -> {

                    var i = Intent(this, CropActivity::class.java)
                    i.putExtra(UserSettingConfigure.bitmapUri, fileUri)

                    startActivityForResult(i, CROP_RETURN)
                }
                GALLERY_RETURN -> {
                    var imageUri = data!!.getData();
                    val imageStream = getContentResolver().openInputStream(imageUri);
//                    var selectedImage = BitmapFactory.decodeStream(imageStream);
//                    stickerImageView.setImageBitmap(selectedImage);
                    var i = Intent(this, CropActivity::class.java)
                    i.putExtra(UserSettingConfigure.bitmapUri, imageUri)

                    startActivityForResult(i, CROP_RETURN)
                }
                CROP_RETURN -> {
                    Log.d("Crop", "Back pass")
                    var bmpUri = Uri.parse(data!!.extras.getString(UserSettingConfigure.bitmapUri))
                    //Picasso.get().load(bmpUri).into(stickerImageView);
                    //decode to bitmap  ->OOM

                    var imageLoad = ImageLoad(this, bmpUri, object : ImageLoad.CallBack {
                        override fun callBack(bitmap: Bitmap) {
                            userIconBitmap = bitmap
                            stickerImageView.setImageBitmap(userIconBitmap)
                            stickerImageView.visibility= View.VISIBLE
                            loadTextView.visibility=View.INVISIBLE
                        }
                    })
                    Thread(imageLoad).start()
                    stickerImageView.visibility= View.INVISIBLE
                    loadTextView.visibility=View.VISIBLE
                    //var tar = Picasso.get().load(bmpUri).get()

//                    userIconBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, bmpUri)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
