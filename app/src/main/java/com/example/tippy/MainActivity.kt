package com.example.tippy

import android.animation.ArgbEvaluator
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INIT_TIP = 15

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var tvTipPercent:TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipAmount:TextView
    private lateinit var tvTipTotal:TextView
    private lateinit var tvTipDescription:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipTotal = findViewById(R.id.tvTipTotal)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipDescription = findViewById(R.id.tvTipDescription)

        seekBarTip.progress = INIT_TIP
        tvTipPercent.text = "$INIT_TIP%"
        updateDescription(INIT_TIP)

        seekBarTip.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
          override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
              Log.i(TAG,"On Progress Changed $progress")
              tvTipPercent.text = "$progress%"
              computeTipAndTotal()
              updateDescription(progress)
          }

          override fun onStartTrackingTouch(seekBar: SeekBar?) {}

          override fun onStopTrackingTouch(seekBar: SeekBar?) {}

      })
        etBaseAmount.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"After Text Changed $s")
                computeTipAndTotal()

            }

        })


    }

    private fun updateDescription(tipPercent: Int) {
        val tipDescription = when(tipPercent){
            in 0..9-> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tvTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat()/seekBarTip.max,
            ContextCompat.getColor(this, R.color.colour_worst_tip),
            ContextCompat.getColor(this,R.color.colour_best_tip)
        ) as Int
        tvTipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTipTotal.text = ""
            return
        }
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTipTotal.text = "%.2f".format(totalAmount)
    }
}

