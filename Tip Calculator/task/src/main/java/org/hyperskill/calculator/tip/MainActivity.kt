package org.hyperskill.calculator.tip

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import org.hyperskill.calculator.tip.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextViews()
        setSeekBarListener()
    }

    fun tipAmountAsText(billTotal: Double): String {
        val tipAmount = (billTotal * binding.seekBar.progress / 100.00).toBigDecimal()
        return String.format(resources.getString(R.string.tipAmount),
            "\$", tipAmount.setScale(2, RoundingMode.HALF_UP).toString())
    }

    fun setSeekBarListener() {
        binding.seekBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (binding.editText.text.isNotEmpty()) {
                    binding.tipPercentTv.text = String.format(resources.getString(R.string.tip), p1)
                    binding.tipAmountTv.text = tipAmountAsText(binding.editText.text.toString().toDouble())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    fun setTextViews() {
        binding.editText.doAfterTextChanged {
            val number = it?.toString()?.toBigDecimalOrNull() ?: BigDecimal.ZERO
            if (number > BigDecimal.ZERO) {
                binding.billValueTv.text = String.format(resources.getString(R.string.bill_display),
                    "\$", number.setScale(2, RoundingMode.HALF_UP).toString())
                binding.tipPercentTv.text = String.format(resources.getString(R.string.tip), binding.seekBar.progress)
                val tipAmount = number * binding.seekBar.progress.toBigDecimal() / BigDecimal(100)
                binding.tipAmountTv.text = tipAmountAsText(number.toDouble())
            } else {
                binding.billValueTv.text = ""
                binding.tipPercentTv.text = ""
                binding.tipAmountTv.text = ""
            }
        }
    }
}