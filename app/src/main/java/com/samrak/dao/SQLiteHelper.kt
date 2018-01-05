package com.samrak.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Created by samet on 3.2.2016.
 */
class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTable)
        db.execSQL(createTable2)
        db.execSQL(createTable3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + createTable)
        db.execSQL("DROP TABLE IF EXISTS " + createTable2)
        db.execSQL("DROP TABLE IF EXISTS " + createTable3)
        onCreate(db)
    }

    companion object {

        private var dataBaseHelper: SQLiteHelper? = null

        internal val KEY_ID = "keyId"
        internal val KEY_NAME = "keyName"
        internal val KEY_SURNAME = "keySurname"
        internal val KEY_DATA = "keyData"
        internal val KEY_INT = "keyInt"
        internal val KEY_FLOAT = "keyFloat"


        internal val DATABASE = "activate"
        internal val TABLE = "person"
        internal val DATABASE_VERSION = 1

        internal var createTable = ("CREATE TABLE " + TABLE + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " VARCHAR(10), "
                + KEY_SURNAME + " NVARCHAR(15), "
                + KEY_DATA + " TEXT, "
                + KEY_INT + " INTEGER, "
                + KEY_FLOAT + " FLOAT  )")

        internal var createTable2 = ("CREATE TABLE " + "material" + " ( " + "materialId" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "materialName" + " VARCHAR(10), "
                + "materialSurname" + " NVARCHAR(15), "
                + "materialData" + " TEXT  )")

        internal var createTable3 = ("CREATE TABLE " + "test" + " ( " + "testId" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "testName" + " VARCHAR(10), "
                + "testSurname" + " NVARCHAR(15), "
                + "testData" + " TEXT  )")


        fun getInstance(context: Context): SQLiteHelper {
            if (dataBaseHelper == null)
                return SQLiteHelper(context)
            else
                return dataBaseHelper as SQLiteHelper
        }
    }
}
