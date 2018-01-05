package com.samrak.sqlitekt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.samrak.dao.SQLiteHelper
import com.samrak.model.EntityMaterial
import com.samrak.model.EntityPerson
import com.samrak.model.EntityTest
import com.samrak.sqlite.SQLiteAdapter

class MainActivity : AppCompatActivity() {

    var helper = SQLiteHelper.getInstance(this)
    var db = SQLiteAdapter(helper)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInsert = findViewById<Button>(R.id.btnInsert)
        btnInsert!!.setOnClickListener { v ->
            Toast.makeText(v.getContext(), db.insert( EntityPerson(0, "samet", "öztoprak","data",3, 4.3f), "keyId").toString() +  " id inserted", Toast.LENGTH_SHORT).show()
            Toast.makeText(v.context, db.insert(EntityMaterial(0, "samet", "öztoprak", "data"), "materialId").toString() + " id inserted", Toast.LENGTH_SHORT).show()
            Toast.makeText(v.context, db.insert(EntityTest(0, "samet", "öztoprak", "data"), "testId").toString() + " id inserted", Toast.LENGTH_SHORT).show()

        }

        val btnDelete = findViewById<Button>(R.id.btnDelete)
        btnDelete!!.setOnClickListener { v ->
            Toast.makeText(v.getContext(), db.delete( EntityPerson(2, "samet", "öztoprak","data",3, 4.3f),"keyId").toString() + " id deleted", Toast.LENGTH_SHORT).show();
            Toast.makeText(v.context, db.delete(EntityMaterial(2, "samet", "öztoprak", "data"), "materialId").toString() + " id deleted", Toast.LENGTH_SHORT).show()
        }

        val btnList = findViewById<Button>(R.id.btnList)
        btnList!!.setOnClickListener {
            val materials = db.select(EntityMaterial())
            val entities = db.select(EntityPerson())
            val tests = db.select(EntityTest())
            for (e in entities)
                Log.d("dbColumns :", e.keyId.toString() + " " + e.keyName + " " + e.keySurname + " " + e.keyData + " " + e.keyInt + " " + e.keyFloat)
        }

        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnUpdate!!.setOnClickListener { v ->
            Toast.makeText(v.context, db.update(EntityPerson(5, "Toygur", "Bozkurt", "data", 3, 4.3f), "keyId").toString() + " id updated.", Toast.LENGTH_SHORT).show()
            Toast.makeText(v.context, db.update(EntityMaterial(5, "Toygur", "Bozkurt", "data"), "materialId").toString() + " id updated", Toast.LENGTH_SHORT).show()
        }
    }
}
