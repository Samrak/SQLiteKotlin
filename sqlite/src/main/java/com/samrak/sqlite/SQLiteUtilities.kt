package com.samrak.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Arrays

/**
 * Created by samet on 31-May-16.
 */
abstract class SQLiteUtilities<TT : SQLiteOpenHelper> {
    var dbHelper: TT? = null
    private fun getElement(member: String): String {
        val elements = member.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        return elements[elements.size - 1]
    }

    protected fun getTableName(obj: Any): String {
        val c = obj.javaClass
        val className = getElement(c.getName()).toLowerCase()
        return className.replace("entity".toRegex(), "")
    }

    protected fun getDeclaredFields(obj: Any): ArrayList<Field> {
        val c = obj.javaClass
        val fields = ArrayList<Field>()
        fields.addAll(Arrays.asList(*c.getDeclaredFields()))
        if (c.getSuperclass().getDeclaredFields() != null)
            fields.addAll(Arrays.asList(*c.getSuperclass().getDeclaredFields()))
        return fields
    }

    protected fun getField(obj: Any, idColumnName: String): Field? {
        var field: Field? = null
        try {
            field = obj.javaClass.getDeclaredField(idColumnName)
            field!!.isAccessible = true
        } catch (e: NoSuchFieldException) {
            // field = obj.getClass().getSuperclass().getDeclaredField(idColumnName);
            e.printStackTrace()
        } finally {
            //db.close();
        }
        return field
    }

    protected operator fun getValue(field: Field?, obj: Any): String? {
        var result: String? = null
        try {
            result = if (field!!.get(obj) == null) "" else field!!.get(obj).toString()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return result
    }

    protected fun getAllColumnNames(tableName: String): ArrayList<String> {
        val tableNameIndex = 1
        val columns = ArrayList<String>()
        val db = dbHelper!!.readableDatabase
        val query = "PRAGMA table_info($tableName)"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            columns.add(cursor.getString(tableNameIndex))
            cursor.moveToNext()
        }
        //db.close();
        cursor.close()
        return columns
    }

    protected fun getColumnNames(tableName: String, idColumnName: String): ArrayList<String> {
        val columnNames = getAllColumnNames(tableName)
        columnNames.remove(idColumnName)
        return columnNames
    }

    protected fun getContentValues(tableName: String, idColumnName: String, obj: Any): ContentValues {
        val values = ContentValues()
        try {
            val entityClass = obj.javaClass
            for (column in getColumnNames(tableName, idColumnName)) {
                val field = entityClass.getDeclaredField(column)
                field.setAccessible(true)

                if (field.getType() == String::class.java) {
                    values.put(field.getName(), field.get(obj) as String)
                } else if (field.getType() == ByteArray::class.java) {
                    values.put(field.getName(), field.get(obj) as ByteArray)
                } else if (field.getType() == Short::class.javaPrimitiveType) {
                    values.put(field.getName(), field.get(obj) as Short)
                } else if (field.getType() == Int::class.javaPrimitiveType) {
                    values.put(field.getName(), field.get(obj) as Int)
                } else if (field.getType() == Long::class.javaPrimitiveType) {
                    values.put(field.getName(), field.get(obj) as Long)
                } else if (field.getType() == Double::class.javaPrimitiveType) {
                    values.put(field.getName(), field.get(obj) as Double)
                } else if (field.getType() == Float::class.javaPrimitiveType) {
                    values.put(field.getName(), field.get(obj) as Float)
                } else if (field.getType() == Boolean::class.javaPrimitiveType) {
                    values.put(field.getName(), field.get(obj) as Boolean)
                } else {
                    values.put(field.getName(), getValue(field, obj))
                }
            }
        } catch (e: IllegalAccessException) {
            Log.d(logLabel, e.message)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } finally {
            // db.close();
        }
        return values
    }


    abstract fun <T : Any> select(obj: T): ArrayList<T>

    abstract fun <T : Any> select(obj: T, value: String): ArrayList<T>  //select with value

    abstract fun <T : Any> select(obj: T, key: String, value: String): ArrayList<T>  //select with condition

    abstract fun <T : Any> insert(obj: T, idColumnName: String): Long

    abstract fun <T : Any> delete(obj: T, idColumnName: String): Long

    abstract fun <T : Any> update(obj: T, idColumnName: String): Long

    companion object {
        protected val logLabel = "SQLiteAdapter"
    }
}