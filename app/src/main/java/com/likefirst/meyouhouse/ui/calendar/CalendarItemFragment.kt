package com.likefirst.meyouhouse.ui.calendar

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.likefirst.meyouhouse.data.dto.calendar.CalendarData
import com.likefirst.meyouhouse.databinding.ItemCalendarVpBinding
import com.likefirst.meyouhouse.ui.BaseFragment
import java.time.LocalDate
import java.util.*

class CalendarItemFragment : BaseFragment<ItemCalendarVpBinding>(ItemCalendarVpBinding::inflate) {

    val pageIndex by lazy {
        requireArguments().getInt("pageIndex")
    }

    override fun initAfterBinding() {
        makeDateInfo()
        makeCalendar()
    }

    fun makeDateInfo(){
        val mCalendar: Calendar = GregorianCalendar.getInstance()
        val MONTH_TODAY = mCalendar.get(Calendar.MONTH)
        val YEAR_TODAY = mCalendar.get(Calendar.YEAR)
        var month = 0
        var year = 0
        if(MONTH_TODAY + pageIndex >= 0){
            month = (MONTH_TODAY + pageIndex) % 12 + 1
            year = (MONTH_TODAY + pageIndex) / 12 + YEAR_TODAY
        } else {
            val monthIndexAbs = kotlin.math.abs(MONTH_TODAY + pageIndex)
            year = YEAR_TODAY - ((monthIndexAbs-1) / 12) - 1
            val monthIndex = 12 - (monthIndexAbs % 12)
            if (monthIndex == 12){
                month = 1
            } else {
                month = monthIndex+1
            }
        }
        binding.itemCalendarMonthTv.text = month.toString() + "월"
        binding.itemCalendarYearTv.text = year.toString()
    }

    fun getCalendar() : Date {
        val calendar = Calendar.getInstance()
        val MONTH_TODAY = calendar.get(Calendar.MONTH)
        val YEAR_TODAY = calendar.get(Calendar.YEAR)

        //
        if(MONTH_TODAY + pageIndex >= 0){
            val monthIndex = (MONTH_TODAY + pageIndex) % 12
            val year = (MONTH_TODAY + pageIndex) / 12 + YEAR_TODAY
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthIndex)
            return calendar.time
        } else {
            val monthIndexAbs = kotlin.math.abs(MONTH_TODAY + pageIndex)
            val year = YEAR_TODAY - ((monthIndexAbs-1) / 12) - 1
            val monthIndex = 12 - (monthIndexAbs % 12)
            if (monthIndex == 12){
                calendar.set(Calendar.MONTH, 0)
            } else {
                calendar.set(Calendar.MONTH, monthIndex)
            }
            calendar.set(Calendar.YEAR, year)
            return calendar.time
        }
    }

    fun createCalendarList() : ArrayList<CalendarData> {
        val customCalendar = CustomCalendar(getCalendar())
        customCalendar.initBaseCalendar()
        return customCalendar.dateList
    }

    fun makeCalendar() {
        val date = getCalendar()
        val today = if (date != Calendar.getInstance().time){
            "0"
        } else {
            Calendar.getInstance().get(Calendar.DATE).toString()
        }
        val calendarRVAdapter = CalendarItemRVAdapter(createCalendarList(), requireContext(), today)
        binding.calendarItemRv.apply {
            adapter = calendarRVAdapter
            layoutManager = GridLayoutManager(requireContext(), 7)
            calendarRVAdapter.setDate(date)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//            calendarRVAdapter.setOnDateSelectedListener(object : ArchiveCalendarRVAdapter.CalendarDateSelectedListener {
//                override fun onDateSelectedListener(date: Date, dayInt : Int) {
//                    val calendar = GregorianCalendar.getInstance()
//                    calendar.time = date
//                    calendar.set(Calendar.DAY_OF_MONTH, dayInt)
//
//                    // 오늘 날짜랑 비교 (1. calendar = GregorianCalendar.getInstance() : 0
//                    //                   2. calendar > GregorianCalendar.getInstance() : 1
//                    //                   3. calendar < GregorianCalendar.getInstance() : -1)
//                    if(calendar.compareTo(GregorianCalendar.getInstance()) == 1){
//                        Snackbar.make(view!!, "미래의 일기는 작성할 수 없어요!!!", Snackbar.LENGTH_SHORT).show()
//                    } else {
//                        val dateString = dateToString(calendar.time)
//                        val intent = Intent(requireContext(), DiaryActivity::class.java)
//                        intent.putExtra("diaryDate", dateString)
//                        startActivity(intent)
//                    }
//                }
//                override fun onDiarySelectedListener(diaryIdx: Int) {
//                    val service = ArchiveDiaryService()
//                    service.setArchiveCalendarView(this@ArchiveCalendarItemFragment)
//                    service.getDiary(diaryIdx)
//                }
//            })
        }
    }
}