package com.samrak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.samrak.model.EntityPerson
import com.samrak.sqlitekt.R

import java.util.ArrayList


/**
 * Created by Samet OZTOPRAK on 25.4.2016.
 */
class CustomPersonAdaptor(aContext: Context, private val listData: ArrayList<EntityPerson>) : BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(aContext)
    }

    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(position: Int): Any {
        return listData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_subscriber_layout, null)
            holder = ViewHolder()
            holder.txtId = convertView!!.findViewById(R.id.txtId) as TextView
            holder.txtName = convertView.findViewById(R.id.txtName) as TextView
            holder.txtData = convertView.findViewById(R.id.txtData) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.txtId!!.text = listData[position].keyId.toString()
        holder.txtName!!.text = listData[position].keyName
        holder.txtData!!.text = listData[position].keySurname
        return convertView
    }

    internal class ViewHolder {
        var txtId: TextView? = null
        var txtName: TextView? = null
        var txtData: TextView? = null
    }
}
