package com.techapp.james.chatroomdemo.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.techapp.james.chatroomdemo.R
import com.techapp.james.chatroomdemo.model.compressImage.CompressImage
import com.techapp.james.chatroomdemo.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    val TAG = "MainActivity"
    var mainPresenter: MainPresenter? = null
    private val allPermission = ArrayList<Int>()
    private val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)  //權限項目在此新增
    private val REQUEST_CODE_ASK_PERMISSIONS = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var background = CompressImage.compressSize(this.baseContext, R.drawable.login_background)
        root.background=background
       // root.setBackgroundResource(R.drawable.login_background)
        applyRight()
        mainPresenter = MainPresenter(this)
        mAuth = FirebaseAuth.getInstance()
        loginBtn.setOnClickListener {
            val TIME = (2 * 1000).toLong()
            loginBtn.setEnabled(false)
            Handler().postDelayed(Runnable { loginBtn.setEnabled(true) }, TIME)
            var email = accountEditText.text.toString()
            var password = passwordEditText.text.toString()
            if (email.equals("") || password.equals("")) {
                email = "-1"
                password = "-1"
            }
            mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in dbUser's information
                            Log.d(TAG, "signInWithEmail:success")
                            var user = mAuth!!.getCurrentUser()
                            Log.d(TAG, user.toString())
                            Log.d(TAG, user!!.email)
                            Log.d(TAG, user.uid)// use uid in database to record message
                            mainPresenter!!.updateUI(user)
                        } else {
                            // If sign in fails, display a message to the dbUser.
                            Log.d(TAG, "signInWithEmail:failure", task.exception)
                            mainPresenter!!.updateUI(null)
                        }
                    }
        }
        signUpBtn.setOnClickListener {
            mainPresenter!!.intentToSignUp()
        }
        testBtn.setOnClickListener {
            accountEditText.setText("test@gmail.com")
            passwordEditText.setText("123456")
        }
    }

    fun applyRight(): Boolean {
        for (i in permission.indices) {
            val eachPermission = this.checkSelfPermission(permission[i]) //命名權限
            allPermission.add(eachPermission)
        }
        for (i in allPermission.indices) {
            if (allPermission[i] != PackageManager.PERMISSION_GRANTED) {
                // System.out.println(permission[serviceIntent]);
                this.requestPermissions(permission,
                        REQUEST_CODE_ASK_PERMISSIONS)
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }
    /**
    send message to firebase
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("chatData")
    var person = User()
    person.name = "James"
    var msg = MessageData()
    msg.name = "Windy"
    msg.data = "I'm James."
    msg.date = "20180415"
    myRef.child(person.name).child("Group").child(msg.date).setValue(msg)
     **/
    /**
     * get data use add single listener ,please.
     **/
    //firebase create dbUser
}
