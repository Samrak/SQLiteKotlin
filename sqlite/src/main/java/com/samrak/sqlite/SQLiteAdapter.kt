package com.samrak.sqlite

import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

/**
 * Created by sametoztoprak on 30.12.2017.
 */

class SQLiteAdapter<TT : SQLiteOpenHelper>(dbHelper: TT) : SQLiteUtilities<TT>() {

    init {
        super.dbHelper = dbHelper;
        //SQLiteHelperss.getInstance(context)
    }

    override fun <T : Any> select(obj: T): ArrayList<T> {
        val query = getSql(obj)
        return fillFields(query, obj)
    }

    override fun <T : Any> select(obj: T, value: String): ArrayList<T> {
        var query = getSql(obj)
        val columns = getAllColumnNames(getTableName(obj))

        if (!value.isEmpty()) {
            var conditions = ""
            for (index in columns.indices) {
                val condition = columns[index] + " like '%" + value + "%' "
                conditions = if (index == 0) conditions + condition else conditions + " or " + condition
            }
            query = query + " where " + conditions
        }
        return fillFields(query, obj)
    }

    override fun <T : Any> select(obj: T, key: String, value: String): ArrayList<T> {
        var query = getSql(obj)
        if (!key.isEmpty()) {
            query = "$query where $key='$value'"
        }
        val entityList = ArrayList<T>()
        return fillFields(query, obj)
    }

    private fun <T : Any> getSql(obj: T): String {
        val tableName = getTableName(obj)
        return "select * from " + tableName
    }

    private fun <T : Any> fillFields(query: String, obj: T): ArrayList<T> {
        val entityList = ArrayList<T>()
        val db = dbHelper!!.readableDatabase

        val tableName = getTableName(obj)
        val entityClass = obj.javaClass
        val columns = getAllColumnNames(tableName)

        try {
            val cursor = db.rawQuery(query, null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                var newObj = entityClass.newInstance()

                for (index in columns.indices) {

                    val field = getField(obj, columns[index])
                    field!!.isAccessible = true
                    if (field.type == String::class.javaObjectType) {
                        field.set(newObj, cursor.getString(index))
                    } else if (field.type == ByteArray::class.javaObjectType) {
                        field.set(newObj, cursor.getBlob(index))
                    } else if (field.type == Short::class.javaObjectType) {
                        field.set(newObj, cursor.getShort(index))
                    } else if (field.type == Int::class.javaObjectType) {
                        field.set(newObj, cursor.getInt(index))
                    } else if (field.type == Long::class.javaObjectType) {
                        field.set(newObj, cursor.getLong(index))
                    } else if (field.type == Double::class.javaObjectType) {
                        field.set(newObj, cursor.getDouble(index))
                    } else if (field.type == Float::class.javaObjectType) {
                        field.set(newObj, cursor.getFloat(index))
                    } else if (field.type == Boolean::class.javaObjectType) {
                        field.set(newObj, java.lang.Boolean.parseBoolean(cursor.getString(index)))
                    } else {
                        field.set(newObj, cursor.getString(index))
                    }
                }
                entityList.add(newObj as T)
                cursor.moveToNext()
            }
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return entityList
    }

    //insert entity
    override fun <T : Any> insert(obj: T, idColumnName: String): Long {
        val tableName = getTableName(obj)
        val db = dbHelper!!.writableDatabase

        val values = getContentValues(tableName, idColumnName, obj)
        return db.insert(tableName, null, values)
    }


    //update entity
    override fun <T : Any> update(obj: T, idColumnName: String): Long {
        val tableName = getTableName(obj)
        val db = dbHelper!!.writableDatabase

        val field = getField(obj, idColumnName)
        val idColumnValue = getValue(field, obj)
        val values = getContentValues(tableName, idColumnName, obj)
        return db.update(tableName, values, idColumnName + "=" + idColumnValue, null).toLong()
    }

    //delete entity
    override fun <T : Any> delete(obj: T, idColumnName: String): Long {

        val tableName = getTableName(obj)
        val db = dbHelper!!.writableDatabase

        val field = getField(obj, idColumnName)
        val idColumnValue = getValue(field, obj)
        return db.delete(tableName, "$idColumnName='$idColumnValue'", null).toLong()
    }
}