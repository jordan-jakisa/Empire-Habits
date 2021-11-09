package com.empire.habits.ui.chart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.empire.habits.R
import com.empire.habits.utils.MainViewChanger
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class ChartFragment : Fragment() {
    private lateinit var pieChart: PieChart
    private lateinit var chartHelperTv: TextView
    lateinit var mainViewChanger: MainViewChanger
    lateinit var mAdView: AdView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        pieChart = view.findViewById(R.id.pieChart)
        chartHelperTv = view.findViewById(R.id.chartHelperTv)
        mAdView = view.findViewById(R.id.adView)
        initView()
        setData()
        return view
    }

    private fun setData() {
        val values = mainViewChanger.getItems()
        val dataEntries = ArrayList<PieEntry>()
        val colors: ArrayList<Int> = ArrayList()
        if (values != null) {
            if (values.isEmpty()){
                chartHelperTv.visibility = View.VISIBLE
                pieChart.visibility = View.GONE
            } else {
                chartHelperTv.visibility = View.GONE
                pieChart.visibility = View.VISIBLE
            }
        }
        if (values != null) {
            for (i in values) {
                dataEntries.add(PieEntry(i.count, i.word))
                colors.add(Color.parseColor(i.color))
            }
        }

        pieChart.setUsePercentValues(true)
        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        //add text in center
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Activity Performance Share"

        pieChart.invalidate()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainViewChanger) mainViewChanger = context
    }

    private fun initView() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        pieChart.setUsePercentValues(true)
        pieChart.description.text = "Activity PieChart"
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChartFragment.
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
        ChartFragment().apply {
        arguments = Bundle().apply {
        putString(ARG_PARAM1, param1)
        putString(ARG_PARAM2, param2)
        }
        }
         */

    }
}