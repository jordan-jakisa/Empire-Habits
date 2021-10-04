package com.empire.habits

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class NewWordActivity : AppCompatActivity() {
    private lateinit var editWordView: TextInputEditText
    private lateinit var editDescription: TextInputEditText
    private lateinit var pickTime: TimePicker
    private lateinit var repeatCheckBox: CheckBox


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
        editDescription = findViewById(R.id.editDescription)
        pickTime = findViewById(R.id.pickTime)
        repeatCheckBox = findViewById(R.id.repeatCheckBox)


        val button = findViewById<Button>(R.id.button_save)

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                if (TextUtils.isEmpty(editDescription.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                }
            } else {
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}