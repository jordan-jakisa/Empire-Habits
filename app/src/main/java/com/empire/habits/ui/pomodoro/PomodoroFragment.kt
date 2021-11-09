package com.empire.habits.ui.pomodoro

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.empire.habits.R
import com.empire.habits.utils.MainViewChanger
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.button.MaterialButton


class PomodoroFragment : androidx.fragment.app.Fragment() {
    lateinit var timeLeftTv: TextView
    lateinit var startBtn: MaterialButton
    lateinit var progressBar: ProgressBar
    lateinit var mainViewChanger: MainViewChanger
    lateinit var stopPhubing: TextView
    lateinit var mAdView: AdView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pomodoro, container, false)
        timeLeftTv = view.findViewById(R.id.timeLeftTv)
        startBtn = view.findViewById(R.id.startBtn)
        progressBar = view.findViewById(R.id.progressBar)
        stopPhubing = view.findViewById(R.id.stopPhubing)
        mAdView = view.findViewById(R.id.adView)
        initViews()
        return view
    }

    private fun initViews() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        startBtn.setOnClickListener{ mainViewChanger.startTimer(timeLeftTv, progressBar, startBtn, stopPhubing)}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainViewChanger) mainViewChanger = context
    }


}