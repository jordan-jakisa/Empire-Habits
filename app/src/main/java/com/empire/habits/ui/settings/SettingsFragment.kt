package com.empire.habits.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.empire.habits.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {
    lateinit var timeSwitch: SwitchMaterial
    lateinit var snoozeSwitch: SwitchMaterial
    lateinit var hapticsSwitch: SwitchMaterial
    lateinit var themeTv: TextView
    lateinit var ringtoneTv: TextView
    lateinit var soundTv: TextView
    lateinit var feedbackTv: TextView
    lateinit var acknowledgmentsTv: TextView
    lateinit var licensesTv: TextView
    lateinit var rateTv: TextView
    lateinit var aboutTv: TextView
    lateinit var supportTv: TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        themeTv = view.findViewById(R.id.themeTv)
        ringtoneTv = view.findViewById(R.id.ringtoneTv)
        soundTv = view.findViewById(R.id.soundTv)
        timeSwitch = view.findViewById(R.id.timeSwitch)
        snoozeSwitch = view.findViewById(R.id.snoozeSwitch)
        hapticsSwitch = view.findViewById(R.id.hapticsSwitch)
        feedbackTv = view.findViewById(R.id.feedbackTv)
        acknowledgmentsTv = view.findViewById(R.id.acknowledgmentsTv)
        licensesTv = view.findViewById(R.id.licensesTv)
        aboutTv = view.findViewById(R.id.aboutTv)
        supportTv = view.findViewById(R.id.supportTv)
        supportTv.setOnClickListener{
            openSupport(view)
        }
        initViews()
        return view
    }

    private fun initViews() {
        themeTv.setOnClickListener {
            pickTheme()
        }
        ringtoneTv.setOnClickListener {
            pickRingtone()
        }
        soundTv.setOnClickListener {
            pickSound()
        }
        timeSwitch.setOnClickListener {
            changeTimeFormat()
        }
        snoozeSwitch.setOnClickListener {
            snoozeNotifications()
        }
        hapticsSwitch.setOnClickListener {
            enableVibrations()
        }
        feedbackTv.setOnClickListener {
            sendFeedback()
        }
        acknowledgmentsTv.setOnClickListener {
            showAcknowledgement()
        }
        licensesTv.setOnClickListener {
            showLicenses()
        }
        aboutTv.setOnClickListener { showAbout() }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openSupport(view: View) {
        showBottomSheetDialog(view)
    }

    private fun showAbout() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun rateApp() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun showLicenses() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun showAcknowledgement() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun sendFeedback() {
        val addresses = ArrayList<String>().add("dev.jordanempire@gmail.com")
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, "Empire Habits Feedback")
        }
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) startActivity(intent)
    }

    private fun enableVibrations() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun snoozeNotifications() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun changeTimeFormat() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun pickSound() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun pickRingtone() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun pickTheme() {
        Toast.makeText(themeTv.context, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showBottomSheetDialog(view: View) {
        val bottomSheetDialog = BottomSheetDialog(view.context)
        val view = LayoutInflater.from(view.context).inflate(
            R.layout.bs_support, view.findViewById(
                R.id.parent
            ), false)
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
