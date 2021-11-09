package com.empire.habits.ui.items

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.empire.habits.R
import com.empire.habits.adapters.ItemListAdapter
import com.empire.habits.entity.Word
import com.empire.habits.utils.MainViewChanger
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import kotlin.properties.Delegates

class ItemFragment : androidx.fragment.app.Fragment() {
    private lateinit var colorField: String
    private lateinit var noItemsCard: MaterialCardView
    var repeat by Delegates.notNull<Boolean>()
    lateinit var mainViewChanger: MainViewChanger

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        noItemsCard = view.findViewById(R.id.noItemsCard)
        mainViewChanger.itemSize(noItemsCard)
        val adapter = ItemListAdapter(mainViewChanger)
        mainViewChanger.observeWords(adapter)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            showBottomSheetDialog(view)
        }
       return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainViewChanger) mainViewChanger = context
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showBottomSheetDialog(view: View) {
        val bottomSheetDialog = BottomSheetDialog(view.context)
        val view = LayoutInflater.from(view.context).inflate(
            R.layout.bs_add_item, view.findViewById(
                R.id.parent
            ), false)
        val editWordView = view.findViewById<EditText>(R.id.edit_word)
        val editDescription = view.findViewById<EditText>(R.id.editDescription)
        val pickTime = view.findViewById<TimePicker>(R.id.pickTime)
        pickTime.setIs24HourView(true)
        val choseColor = view.findViewById<MaterialButton>(R.id.choseColor)
        val repeatCheckBox = view.findViewById<MaterialCheckBox>(R.id.repeatCheckBox)
        val buttonSave = view.findViewById<ShapeableImageView>(R.id.saveSIV)
        repeatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            this.repeat = isChecked
        }

        choseColor.setOnClickListener {
            MaterialColorPickerDialog.Builder(view.context)
                .setTitle("Pick Color")
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)
                .setColorListener { _, colorHex ->
                    this.colorField = colorHex
                    choseColor.setBackgroundColor(Color.parseColor(colorField))
                }
                .show()

        }

        buttonSave.setOnClickListener {
            Toast.makeText(view.context, "Added", Toast.LENGTH_SHORT).show()
            if (TextUtils.isEmpty(editWordView.text)) {
                Toast.makeText(view.context, "Empty Item", Toast.LENGTH_SHORT).show()
            } else if(TextUtils.isEmpty(editDescription.text)){
                Toast.makeText(view.context, "Empty Description", Toast.LENGTH_SHORT).show()
            }else if (pickTime.hour == null){
                Toast.makeText(view.context, "Pick a time", Toast.LENGTH_SHORT).show()
            }else if (pickTime.minute == null){
                Toast.makeText(view.context, "Pick a time", Toast.LENGTH_SHORT).show()
            }else if (colorField == null){
                Toast.makeText(view.context, "Select a color", Toast.LENGTH_SHORT).show()
            }else {
                val title = editWordView.text.toString()
                val description = editDescription.text.toString()
                val hour = pickTime.hour.toLong()
                val minute = pickTime.minute.toLong()
                val color = colorField
                val repeat = repeat
                val word = Word(title, description, hour, minute, color, 0f,repeat)
                mainViewChanger.insertWord(word)
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.setContentView(view)

        bottomSheetDialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(R.id.bottom_sheet)
            parentLayout?.let { it ->
                val behavior = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        bottomSheetDialog.show()
    }

    private fun setupFullHeight(view: View?) {
        val layoutParams = view?.layoutParams
        layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
        view?.layoutParams = layoutParams

    }
}