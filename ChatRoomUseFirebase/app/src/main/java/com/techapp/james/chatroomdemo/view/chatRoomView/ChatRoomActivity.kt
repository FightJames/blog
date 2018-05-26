package com.techapp.james.chatroomdemo.view.chatRoomView

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.github.bassaer.chatmessageview.model.Message
import com.techapp.james.chatroomdemo.R
import com.techapp.james.chatroomdemo.model.convertDBmessage.ConvertMessage
import com.techapp.james.chatroomdemo.model.convertDBmessage.ConvertMsgCallBack
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.chatroomdemo.model.userModel.CurrentUser
import com.techapp.james.chatroomdemo.view.userSetting.UserSettingActivity
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.DBCallBack
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseFacade
import kotlinx.android.synthetic.main.activity_chat_room.*


class ChatRoomActivity : AppCompatActivity() {
    var convertMsg = ConvertMessage()
    var isLast = "isLast"
    var judgeChangeMsg: MessageData = MessageData()
    var lastMsg: MessageData = MessageData()
    var getMessageCallback = object : ConvertMsgCallBack {
        override fun convertCallBack(message: Message) {
            if (message.text!!.toString().equals(isLast)) {
                chat_view.setRefreshing(false)
            } else {
                if (!judgeChangeMsg.time.equals(convertMsg.convertUIMsgToDBMsg(message).time)) {
                    if (message.user.getId().equals(CurrentUser.dbUser!!.myId)) {
                        message.isRight = true
                        chat_view.send(message)
                    } else {
                        message.isRight = false
                        chat_view.receive(message)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        chat_view.setAutoScroll(true)
        init()
        settingTextView.setOnClickListener {
            var i = Intent(this, UserSettingActivity::class.java)
            this.startActivity(i)
        }
//        var msg = Message.Builder()
//                .setUser() // Sender
//                .setType(Message.Type.PICTURE)
//                .setPicture()
//                .build()
        chat_view.getInputBox().setHintTextColor(Color.parseColor("#000000"))
        chat_view.inputTextColor = Color.parseColor("#000000")
        chat_view.getMessageViewRecycler().addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (chat_view.getMessageViewRecycler().isCurrentLast()) {
                    chat_view.getMessageViewRecycler().removePast = true
                    var lastIndex = chat_view.getMessageViewRecycler().messageList.size - 1
                    var msgDB = convertMsg.convertUIMsgToDBMsg(chat_view.getMessageViewRecycler().messageList[lastIndex])
                    if (!lastMsg.id.equals(msgDB.id)) {
                        FirebaseFacade.getLastMsg(msgDB, object : DBCallBack {
                            override fun dbCallBack(any: Any) {
                                var msgDB = any as MessageData
                                convertMsg.convertSingleDBMsgToUIMsg(msgDB, getMessageCallback)
                            }
                        })
                        lastMsg = msgDB
                    }
                }
            }
        })
        // chat_view.setSendIcon(R.drawable.ic_chevron_left_white_24dp)
        //chat_view.setBackgroundResource(R.color.primary_material_dark)
//    chat_view.getMessageView().setBackgroundResource(R.color.primary_material_dark)
//    chat_view.inputTextColor=R.color.primary_material_dark
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

    }

    fun init() {
        chat_view.getMessageViewRecycler().maxMessage = 20
        chat_view.setOnClickSendButtonListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                chat_view.setAutoScroll(true)
                if (!chat_view.inputText.equals("")) {
                    var msg = Message.Builder()
                            .setUser(CurrentUser.dbUser!!) // Sender
                            .setRight(true) // This message Will be shown right side.
                            .setText(chat_view.inputText) //Message contents
                            .build()
                    chat_view.send(msg)
                    chat_view.inputText = ""
                    judgeChangeMsg = convertMsg.convertUIMsgToDBMsg(msg)
                    FirebaseFacade.saveMsg(judgeChangeMsg)
//                    var targetTopMsg=chat_view.getMessageView().messageList[0]
//               Log.d("Top Msg ",targetTopMsg.text)
                }
            }
        })
        FirebaseFacade.getMsg(object : DBCallBack {
            override fun dbCallBack(any: Any) {
                var msgDB = any as MessageData
                //    Log.d("onChange", "pass")
                convertMsg.convertSingleDBMsgToUIMsg(msgDB, getMessageCallback)
            }
        })
        chat_view.setEnableSwipeRefresh(true)
        chat_view.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                chat_view.getMessageViewRecycler().removePast = false
                chat_view.setAutoScroll(false)
                var msgDB = convertMsg.convertUIMsgToDBMsg(chat_view.getMessageViewRecycler().messageList[0])
                FirebaseFacade.getPastMsg(msgDB, object : DBCallBack {
                    override fun dbCallBack(any: Any) {
                        var msgDB = any as MessageData
                        if (msgDB.isLast) {
                            msgDB.content = isLast
                            msgDB.time = "1000"
                            convertMsg.convertSingleDBMsgToUIMsg(msgDB, getMessageCallback)
                            //chat_view.setRefreshing(false)
                        } else {
                            convertMsg.convertSingleDBMsgToUIMsg(msgDB, getMessageCallback)
                        }
                    }
                })
            }
        })
    }
}