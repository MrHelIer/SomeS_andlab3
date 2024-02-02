package com.example.somes_andlab3

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var dialog: AlertDialog

    private var firstNum: Double    = 0.0
    private var secondNum: Double   = 0.0
    private var thirdNum: Double    = 0.0
    private var isSum: Boolean      = false
    private var isMult: Boolean     = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BuildInputDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_input -> dialog.show()
            R.id.action_calc -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Result")
                builder.setPositiveButton("OK", null)

                var res = ""
                if(isSum) res += "Сумма элементов: ${firstNum+secondNum+thirdNum}"
                if(isMult) res += "\nНаименьшее общее кратное первых 2-х элементов: ${NOK(firstNum, secondNum)}"
                builder.setMessage(res)

                builder.create().show()
            }
            R.id.action_about -> {
                val aboutActivity = Intent(this, AboutActivity::class.java)
                startActivity(aboutActivity)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun BuildInputDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Input numbers")
        val dialogLayout = layoutInflater.inflate(R.layout.activity_input, null)
        builder.setView(dialogLayout)

        // Получаем поля ввода чисел
        val firstNumInput = dialogLayout.findViewById<EditText>(R.id.first_num)
        val secondNumInput = dialogLayout.findViewById<EditText>(R.id.second_num)
        val thirdNumInput = dialogLayout.findViewById<EditText>(R.id.third_num)

        // Получаем чекбоксы
        val sumCheckBox = dialogLayout.findViewById<CheckBox>(R.id.sum_cb)
        val multCheckBox = dialogLayout.findViewById<CheckBox>(R.id.mult_cb)

        // Добавляем кнопки
        builder.setPositiveButton("OK") { dialog: DialogInterface, witch: Int ->
            val first    = firstNumInput.text.toString().toDoubleOrNull()
            val second   = secondNumInput.text.toString().toDoubleOrNull()
            val third    = thirdNumInput.text.toString().toDoubleOrNull()

            if (first == null)      firstNum = 0.0
            else                    firstNum = first

            if (second == null)     secondNum = 0.0
            else                    secondNum = second

            if (third == null)      thirdNum = 0.0
            else                    thirdNum = third

            isSum   = sumCheckBox.isChecked
            isMult  = multCheckBox.isChecked
            dialog.dismiss()
        }
            .setNegativeButton("Cancel") { dialog: DialogInterface, witch: Int -> dialog.cancel() }
        dialog = builder.create()
    }

    fun NOD(x:Double, y:Double, i: Int): Double{
        if(i == 1000) return 0.0
        if(x == y) return x

        var div: Double
        var d: Double = x - y
        if(d < 0){
            d = -d
            div = NOD(x, d, i+1)
        }
        else div = NOD(y, d,i+1)
        return div
    }
    fun NOK(x: Double, y: Double): Double {
        return x*y / NOD(x, y, 0)
    }
}