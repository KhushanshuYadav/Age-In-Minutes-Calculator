package com.khushanshu.ageinminutes

import  android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var selectedDateTv:TextView?=null
    private var tvAgeInMinutes:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button=findViewById<Button>(R.id.button1) //our button name is button

        //setting up what happens if button is clicked
        button.setOnClickListener {
            datePicked()  //function is called which pops up date dialog and shows toast if date is selected

        }

        selectedDateTv=findViewById<TextView>(R.id.tvSelectedDate)
        tvAgeInMinutes=findViewById<TextView>(R.id.tvAgeInMinutes) //both tvAgeInMinutes are different one is id one is name of var but kept same as convention


    }

    private fun datePicked(){
        //private as we cant access it from other class otherwise no need its just optimization
        //we need year month and day for creating dialog by DatePickerDialog class
        //will obtain them by java Calender class
        //these obtained values will be displayed in dialog by default

        val myCalendar=Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        //storing picked date in dpd

        val dpd =DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view,selectedYear, selectedMonth, selectedDayOfMonth ->
                //we can replace view with _ as view is never used in code
                //in programming months counts start from zero so to use correct month do selectedMonth+1

                Toast.makeText(this,"Date selected successfully",Toast.LENGTH_LONG).show()

                //storing selected date in below val and showing in below named TextView
                val selectedDate="$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                selectedDateTv?.text=selectedDate

                //creating a object of simpleDateFormat type to  parse and store  the selected date of string type to date type
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) //"m"=minutes in class

                // parsing
                val theDate= sdf.parse(selectedDate)

                //below block makes sure we do al work inside it if theDate is not empty let only executes code if theDate is not null
                theDate?.let{
                    //converting selected date in minutes x.time/x.getTime() gives time in milliseconds passed till x (date type) since 1970 jan 1st
                    val selectedDateInMinutes=theDate.time/60000

                    //obtaining the current time in milliseconds from system as shown and converting in string  then parsing to date then converting it in minutes
                    val currentDate=sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes=currentDate.time/60000

                        //finding difference
                        val differenceInMinutes=currentDateInMinutes-selectedDateInMinutes

                        //showing the age in minutes in desired text view
                        tvAgeInMinutes?.text=differenceInMinutes.toString()
                    }


                }

            } ,
            //above listener does what written in block when date is picked and passes them in block i.e selectedYear and etcc

            //below year month and day which will be shown by default
            year,month,day)


        //setting up max limit of date selection
        dpd.datePicker.maxDate=System.currentTimeMillis()

        //showing the dpd
        dpd.show()



    }
}
















