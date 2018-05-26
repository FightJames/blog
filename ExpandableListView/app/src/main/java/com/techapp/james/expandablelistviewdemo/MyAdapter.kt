package com.techapp.james.expandablelistviewdemo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Toast

/**
 * Created by James on 2018/3/4.
 */
class MyAdapter : BaseExpandableListAdapter {
    private var groupItem: Array<String>
    private var contentItem: Array<Array<String>>
    private var context: Context
    private var imageOnClickListener: View.OnClickListener

    constructor(context: Context, groupItem: Array<String>, contentItem: Array<Array<String>>, imageOnClickListener: View.OnClickListener) {
        this.groupItem = groupItem
        this.contentItem = contentItem
        this.context = context
        this.imageOnClickListener = imageOnClickListener
    }

    override fun getGroup(position: Int): Any {
        return groupItem[position]
    }

    override fun getGroupCount(): Int {
        return groupItem.size
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var groupHold: GroupHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.group_item, null)
            groupHold = GroupHolder(convertView)
            convertView.setTag(groupHold)
        } else {
            groupHold = convertView.getTag() as GroupHolder
        }
        groupHold.groupTextView.text = groupItem[groupPosition]
        if (isExpanded) {
            groupHold.groupImageView.setImageResource(R.drawable.delete)
        } else {
            groupHold.groupImageView.setImageResource(R.drawable.add)
        }
        //if you add icon on expandableListView, you must add listener on it.
        //it can let list expanded

        val tagMap = HashMap<String, Any>()
        tagMap.put("groupPosition", groupPosition)
        tagMap.put("isExpanded", isExpanded)
        groupHold.groupImageView.setTag(tagMap)
        groupHold.groupImageView.setOnClickListener(this.imageOnClickListener)
        return convertView!!
    }

    override fun getChildrenCount(p0: Int): Int {
        return contentItem[p0].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return contentItem[groupPosition][childPosition]
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p0.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val itemHold: ItemHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_item, null)
            itemHold = ItemHolder(convertView)
            convertView.setTag(itemHold)
        } else {
            itemHold = convertView.getTag() as ItemHolder
        }
        convertView!!.setOnClickListener {

        }
        itemHold.itemCheckBox.text = contentItem[groupPosition][childPosition]
        itemHold.itemCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(context, "Item is selected: " + contentItem[groupPosition][childPosition], Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Item is cancel selected: " + contentItem[groupPosition][childPosition], Toast.LENGTH_SHORT).show()

            }
        }
        convertView.setOnClickListener {
            println("Pass")
        }
        return convertView!!
    }


    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}