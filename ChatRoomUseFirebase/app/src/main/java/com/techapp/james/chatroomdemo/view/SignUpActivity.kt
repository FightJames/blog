package com.techapp.james.chatroomdemo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.techapp.james.chatroomdemo.R
import com.techapp.james.chatroomdemo.view.chatRoomView.ChatRoomActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    val TAG = "SignUpActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        createBtn.setOnClickListener {
            var email = accountEditText.text.toString()
            var password = passwordEditText.text.toString()
            if (!(email.equals("") || password.equals(""))) {
                mAuth!!.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
// Sign in success, update UI with the signed-in dbUser's information
                                Log.d(TAG, "createUserWithEmail:success")
                                var user = mAuth!!.getCurrentUser()
                                updateUI(user)
                            } else {
// If sign in fails, display a message to the dbUser.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(this@EmailPasswordActivity, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show()
                                updateUI(null)
                            }
                        }
            }
        }
        cencelBtn.setOnClickListener {
            this.finish()
        }
    }

    fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            val toast = Toast.makeText(this,
                    "Sign Fail", Toast.LENGTH_LONG)
            toast.show()
            return
        }
        var i = Intent(this, ChatRoomActivity::class.java)
        startActivity(i)
    }
}
/**
mAuth!!.createUserWithEmailAndPassword(accountEditText.text.toString(), passwordEditText.text.toString())
.addOnCompleteListener(this) { task ->
if (task.isSuccessful) {
// Sign in success, update UI with the signed-in dbUser's information
Log.d(FragmentActivity.TAG, "createUserWithEmail:success")
val dbUser = mAuth.getCurrentUser()
updateUI(dbUser)
} else {
// If sign in fails, display a message to the dbUser.
Log.w(FragmentActivity.TAG, "createUserWithEmail:failure", task.exception)
Toast.makeText(this@EmailPasswordActivity, "Authentication failed.",
Toast.LENGTH_SHORT).show()
updateUI(null)
}

// ...
}
 * **/
//sign out
//FirebaseAuth.getInstance().signOut();