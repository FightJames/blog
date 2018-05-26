package com.github.bassaer.chatmessageview.view

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import com.github.bassaer.chatmessageview.model.Message
import com.github.bassaer.chatmessageview.models.Attribute
import com.github.bassaer.chatmessageview.util.MessageDateComparator
import com.github.bassaer.chatmessageview.util.TimeUtils
import java.util.*
import android.view.ViewTreeObserver
import kotlin.collections.ArrayList


class MessageViewRecycler : RecyclerView, View.OnFocusChangeListener {
    /**
     * All contents such as right message, left message, date label
     */
    private var chatList: MutableList<Any> = ArrayList()
    public var maxMessage = 0
    public var removePast = false
    /**
     * Only messages
     */
    val messageList = ArrayList<Message>()

    private lateinit var messageAdapter: MessageRecyclerAdapterRE

    private lateinit var keyboardAppearListener: OnKeyboardAppearListener

    /**
     * MessageView is refreshed at this time
     */
    private var refreshInterval: Long = 60000

    private var attribute: Attribute
    private var lastCompletelyVisibleItemPosition: Int = -1

    interface OnKeyboardAppearListener {
        fun onKeyboardAppeared(hasChanged: Boolean)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        attribute = Attribute(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attribute = Attribute(context, attrs)
        init()
    }


    fun init(list: List<Message>) {
        chatList = ArrayList()
        for (i in list.indices) {
            addMessage(list[i])
        }
        refresh()
        init()
    }

    fun init(attribute: Attribute) {
        this.attribute = attribute
        init()
    }

    /**
     * Initialize list
     */
    fun init() {

        messageAdapter = MessageRecyclerAdapterRE(context, 0, chatList, attribute)
        layoutManager = LinearLayoutManager(context)
        adapter = messageAdapter
        val handler = Handler()
        val refreshTimer = Timer(true)
        refreshTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post { messageAdapter.notifyDataSetChanged() }
            }
        }, 1000, refreshInterval)
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                lastCompletelyVisibleItemPosition = (recyclerView?.getLayoutManager() as LinearLayoutManager).findLastVisibleItemPosition()
                Log.d("VisibleItemposition", lastCompletelyVisibleItemPosition.toString())
            }
        })
    }

    /**
     * Set new message and refresh
     * @param message new message
     */
    fun setMessage(message: Message) {
        addMessage(message)
        refresh()
        messageAdapter.notifyDataSetChanged()
        messageAdapter.notifyItemRangeChanged(0, (chatList.size - 1))
    }

    /**
     * Dynamically update message status and refresh, updating the status icon
     * @param message message to update
     * @param status new status to be applied
     */
    fun updateMessageStatus(message: Message, status: Int) {
        val indexOfMessage = messageList.indexOf(message)
        val messageToUpdate = messageList[indexOfMessage]
        messageToUpdate.status = status
        messageAdapter.notifyDataSetChanged()
    }

    /**
     * Add message to chat list and message list.
     * Set date text before set message if sent at the different day.
     * @param message new message
     */
    private fun addMessage(message: Message) {
        messageList.add(message)
        if (messageList.size == 1) {
            chatList.add(message.dateSeparateText)
            chatList.add(message)
            return
        }
        val prevMessage = messageList[messageList.size - 2]
        if (!TimeUtils.isSameDay(prevMessage.sendTime, message.sendTime)) {
            chatList.add(message.dateSeparateText)
        }
        chatList.add(message)
    }

    private fun messageLimit() {
        if (removePast) {
            if (maxMessage != 0) {
                while (messageList.size > maxMessage) {
                    messageList.removeAt(0)
                }
            }
            messageList[0].iconVisibility = true
            messageList[0].usernameVisibility = true
        } else {
            if (maxMessage != 0) {
                while (messageList.size > maxMessage) {
                    messageList.removeAt(messageList.size - 1)
                }
            }
        }
    }

    public fun isCurrentLast(): Boolean {
        Log.d("VisibleItemposition", "ChatList " + (chatList.size - 1).toString())
        return (chatList.size - 1) == lastCompletelyVisibleItemPosition!!
    }

    private fun refresh() {
//        for (i in messageList.indices) {
//            Log.d("content", "Before " + messageList[i].text.toString())
//        }
        sortMessages(messageList)
        messageLimit()
        chatList.clear()
        //chatList=ArrayList()
        chatList.addAll(insertDateSeparator(messageList))
//        for (i in chatList.indices) {
//            if (chatList[i] is String) {
//                Log.d("content", "After " + chatList[i])
//            } else {
//                Log.d("content", "After " + (chatList[i] as Message).text.toString())
//            }
//        }
//        this.adapter = null
//        this.adapter = messageAdapter
//        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//            override fun onChanged() {
//                super.onChanged()
//                decoration.invalidateHeaders()
//            }
//        })
        //  messageAdapter.notifyItemInserted(0)
    }

    fun remove(message: Message) {
        messageList.remove(message)
        refresh()
    }

    fun removeAll() {
        messageList.clear()
        refresh()
    }

    private fun insertDateSeparator(list: List<Message>): List<Any> {
        val result = ArrayList<Any>()
        if (list.isEmpty()) {
            return result
        }
        result.add(list[0].dateSeparateText)
        result.add(list[0])
        if (list.size < 2) {
            return result
        }
        for (i in 1 until list.size) {
            val prevMessage = list[i - 1]
            val currMessage = list[i]
            if (!TimeUtils.isSameDay(prevMessage.sendTime, currMessage.sendTime)) {
                result.add(currMessage.dateSeparateText)
            }
            result.add(currMessage)
        }
        return result
    }

    /**
     * Sort messages
     */
    private fun sortMessages(list: List<Message>?) {
        val dateComparator = MessageDateComparator()
        if (list != null) {
            Collections.sort(list, dateComparator)
        }
    }

    fun setOnKeyboardAppearListener(listener: OnKeyboardAppearListener) {
        keyboardAppearListener = listener
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        // if ListView became smaller
        if (::keyboardAppearListener.isInitialized && height < oldHeight) {
            keyboardAppearListener.onKeyboardAppeared(true)
        }
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            //Scroll to end
            scrollToEnd()
        }
    }

    fun scrollToEnd() {
        if (chatList.size != 0) {
            //layoutManager.scrollToPosition((chatList.size - 1))
            //smoothScrollToPosition((chatList.size - 1))
            scrollToPosition((chatList.size - 1))
        }
    }

    fun setLeftBubbleColor(color: Int) {
        messageAdapter.setLeftBubbleColor(color)
    }

    fun setRightBubbleColor(color: Int) {
        messageAdapter.setRightBubbleColor(color)
    }

    fun setUsernameTextColor(color: Int) {
        messageAdapter.setUsernameTextColor(color)
    }

    fun setSendTimeTextColor(color: Int) {
        messageAdapter.setSendTimeTextColor(color)
    }

    fun setMessageStatusColor(color: Int) {
        messageAdapter.setStatusColor(color)
    }

    fun setDateSeparatorTextColor(color: Int) {
        messageAdapter.setDateSeparatorColor(color)
    }

    fun setRightMessageTextColor(color: Int) {
        messageAdapter.setRightMessageTextColor(color)
    }

    fun setLeftMessageTextColor(color: Int) {
        messageAdapter.setLeftMessageTextColor(color)
    }

    fun setOnBubbleClickListener(listener: Message.OnBubbleClickListener) {
        messageAdapter.setOnBubbleClickListener(listener)
    }

    fun setOnBubbleLongClickListener(listener: Message.OnBubbleLongClickListener) {
        messageAdapter.setOnBubbleLongClickListener(listener)
    }

    fun setOnIconClickListener(listener: Message.OnIconClickListener) {
        messageAdapter.setOnIconClickListener(listener)
    }

    fun setOnIconLongClickListener(listener: Message.OnIconLongClickListener) {
        messageAdapter.setOnIconLongClickListener(listener)
    }

    fun setMessageMarginTop(px: Int) {
        messageAdapter.setMessageTopMargin(px)
    }

    fun setMessageMarginBottom(px: Int) {
        messageAdapter.setMessageBottomMargin(px)
    }

    fun setRefreshInterval(refreshInterval: Long) {
        this.refreshInterval = refreshInterval
    }

    fun setMessageFontSize(size: Float) {
        attribute.messageFontSize = size
        setAttribute()
    }

    fun setUsernameFontSize(size: Float) {
        attribute.usernameFontSize = size
        setAttribute()
    }

    fun setTimeLabelFontSize(size: Float) {
        attribute.timeLabelFontSize = size
        setAttribute()
    }

    fun setMessageMaxWidth(width: Int) {
        attribute.messageMaxWidth = width
        setAttribute()
    }

    fun setDateSeparatorFontSize(size: Float) {
        attribute.dateSeparatorFontSize = size
        setAttribute()
    }

    private fun setAttribute() {
        messageAdapter.setAttribute(attribute)
    }
}