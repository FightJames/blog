package com.github.bassaer.example

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.bassaer.chatmessageview.model.Message
import kotlinx.android.synthetic.main.activity_massage_recycler.*
import java.util.*

class MassageRecyclerActivity : AppCompatActivity() {
    private var mUsers = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massage_recycler)
        initUsers()
      //  chat_view.setEnableSwipeRefresh(true)
        button.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = 1000L
            val message = Message.Builder()
                    .setUser(mUsers[0])
                    .setRight(false)
                    .setSendTime(calendar)
                    .setText("new")
                    .hideIcon(false)
                    .build()

            //Set to chat view
            chat_view.send(message)
        }
        chat_view.setAutoScroll(false)
        chat_view.setOnClickSendButtonListener(View.OnClickListener {
            //new message
            val message = Message.Builder()
                    .setUser(mUsers.get(0))
                    .setRight(true)
                    .setText(chat_view.inputText)
                    .hideIcon(true)
                    .build()

            //Set to chat view
            chat_view.send(message)
            //Reset edit text
            chat_view.inputText = ""
            chat_view.getMessageViewRecycler().scrollToEnd()
            //    receiveMessage(message.getText());
        })

    }

    private fun initUsers() {
        mUsers = ArrayList<User>()
        //User id
        val myId = 0
        //User icon
        val myIcon = BitmapFactory.decodeResource(resources, R.drawable.face_2)
        //User name
        val myName = "Michael"

        val yourId = 1
        val yourIcon = BitmapFactory.decodeResource(resources, R.drawable.face_1)
        val yourName = "Emily"

        val me = User(myId, myName, myIcon)
        val you = User(yourId, yourName, yourIcon)

        mUsers.add(me)
        mUsers.add(you)
    }
}
