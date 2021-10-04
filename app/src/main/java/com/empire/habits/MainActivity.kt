package com.empire.habits

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            showBottomSheetDialog()
            //val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            //startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bs_add_item, findViewById(R.id.parent), false)
        val editWordView = view.findViewById<TextInputEditText>(R.id.edit_word)
        val editDescription = view.findViewById<TextInputEditText>(R.id.editDescription)
        val pickTime = view.findViewById<TimePicker>(R.id.pickTime)
        val choseColor = view.findViewById<Button>(R.id.choseColor)
        val repeatCheckBox = view.findViewById<CheckBox>(R.id.repeatCheckBox)
        val button = view.findViewById<Button>(R.id.button_save)

        choseColor.setOnClickListener {
            showColorPicker()
        }
        button.setOnClickListener {
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            if (TextUtils.isEmpty(editWordView.text)) {
                Toast.makeText(this, "Empty Description", Toast.LENGTH_SHORT).show()

            } else {
                val title = editWordView.text.toString()
                val description = editDescription.text.toString()
                val hour = pickTime.hour.toLong()
                val minute = pickTime.minute.toLong()
                val color = pickColor()
                val repeat = repeatCheckBox.isChecked
                val word = Word(title, description, hour, minute, color, repeat)
                wordViewModel.insert(word)
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun showColorPicker() {
        MaterialColorPickerDialog.Builder(this)
                .setTitle("Pick Color")
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)
                .setColorListener { color, colorHex ->
                    val color = color.toString()
                    val colorHex = colorHex
                    Toast.makeText(this, "Works", Toast.LENGTH_SHORT).show()
                }
                .show()

    }

    fun pickColor(): Int {
        val colorInt = 2
        return colorInt
    }

}