package com.example.tippy

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INIT_TIP = 15

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var tvTipPercent:TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipAmount:TextView
    private lateinit var tvTipTotal:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipTotal = findViewById(R.id.tvTipTotal)
        seekBarTip = findViewById(R.id.seekBarTip)

        seekBarTip.progress = INIT_TIP
        tvTipPercent.text = "$INIT_TIP%"

        seekBarTip.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
          override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
              Log.i(TAG,"On Progress Changed $progress")
              tvTipPercent.text = "$progress%"
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
            }

        })


    }
}