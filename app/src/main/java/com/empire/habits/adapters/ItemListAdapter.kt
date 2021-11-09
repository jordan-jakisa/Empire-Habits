package com.empire.habits.adapters

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.empire.habits.R
import com.empire.habits.entity.Word
import com.empire.habits.utils.MainViewChanger
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class ItemListAdapter(private var mainViewChanger: MainViewChanger) : ListAdapter<Word, ItemListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.wordItemView.text = current.word
        holder.descrTv.text = current.description
        holder.vColor.setBackgroundColor(Color.parseColor(current.color))
        holder.hourTv.text = current.hour.toString()
        holder.minuteTv.text = current.minute.toString()
        //TODO: some condition compare time
        mainViewChanger.startAlarm(current.word, current.description, current.hour.toInt(), current.minute.toInt())

        if (current.repeat) holder.repeatTv.visibility = View.VISIBLE
        else holder.repeatTv.visibility = View.GONE

        //TODO: ("Hide items on click and wait until midnight to re show them")
        //TODO: "set long click listener to delete item from database"
        //if (LocalTime.now() == LocalTime.MIDNIGHT) holder.container.visibility = ViewGroup.VISIBLE

        holder.checkbox.setOnClickListener{
            mainViewChanger.showSnackBar()
            updateCount(current.count + 1f, holder.container, current.word)
            showKonfetti(holder.checkbox.context)
        }
        holder.expandIV.setOnClickListener {
            if (holder.descrTv.visibility == View.GONE)  {
                holder.descrTv.visibility = View.VISIBLE
                holder.expandIV.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
            else {
                holder.descrTv.visibility = View.GONE
                holder.expandIV.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
    }

    private fun showKonfetti(context: Context) {
            KonfettiView(context)
            .build()
            .addColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(
            Shape.Square, Shape.Circle).addSizes(Size(12))
            .setPosition(-50f, KonfettiView(context).width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    private fun updateCount(count: Float, container: ViewGroup, word: String) {
        mainViewChanger.updateCount(count, word)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: MaterialTextView = itemView.findViewById(R.id.textView)
        val descrTv: MaterialTextView = itemView.findViewById(R.id.descrTv)
        val vColor: View = itemView.findViewById(R.id.v_color)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)
        val repeatTv: MaterialTextView = itemView.findViewById(R.id.repeatTv)
        val hourTv: MaterialTextView = itemView.findViewById(R.id.hourTv)
        val minuteTv: MaterialTextView = itemView.findViewById(R.id.minuteTv)
        val container: MaterialCardView = itemView.findViewById(R.id.container)
        val expandIV: CircleImageView = itemView.findViewById(R.id.expandIv)

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
}