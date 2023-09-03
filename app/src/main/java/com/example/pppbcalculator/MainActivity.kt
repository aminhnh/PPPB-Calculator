package com.example.pppbcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.pppbcalculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun update(view: View) {
        with(binding){
            val text = numberTop.text
            when((view as Button).text.toString()) {
                // CANT HAVE OPERATOR RIGHT AFTER THE OTHER
                "+" -> {
                    numberTop.setText("${text}+")
                }
                "-" -> {
                    numberTop.setText("${text}-")
                }
                "/" -> {
                    numberTop.setText("${text}/")
                }
                "x" -> {
                    numberTop.setText("${text}x")
                }
                else -> {
                    numberTop.setText("${text}${view.text}")
                }
            }
            updateResult()
        }
    }

    private fun hasOperator(expression: String): Boolean{
        val splitString = expression.split("+", "-", "/", "x")
        println("STR SIZE: ${splitString.size}")
        return splitString.size > 1
    }
    private fun endIsOperand(expression: String): Boolean{
        val last = expression.takeLast(1)
        println("last: ${last}")
        return last.toIntOrNull() != null
    }
    private fun updateResult() {
        with(binding){
            val text = numberTop.text
            println("masuk pudate result")
            if ( hasOperator(text.toString()) && endIsOperand(text.toString())){
                var result = calculate(text.toString()).toString()
                result = result.dropLastWhile { it == '0' }.dropLastWhile { it == '.' }
                numberBottom.setText(result)
            } else if (!endIsOperand(text.toString())){
                numberBottom.setText("")
            }else {
                println("masuk else")
            }
        }
    }
    private fun calculate(expression: String): Double{
        println("masuk calculate")
        val operandString = expression.split("+","-","x","/")
        val operandDouble = operandString.map{ it.toDouble() }
        var operand = operandDouble.toMutableList()

        val operatorString = expression.filter { it == '+' || it == '-' || it == 'x' || it == '/' }
        var operator = operatorString.toMutableList()

        var result: BigDecimal
        var currentResult = 0.0

        println(operator)
        while(operator.size > 0){
            println("masuk while")
            if (operator.contains('x')){
                println("Masuk x")
                val index = operator.indexOf('x')
                currentResult = operand[index] * operand[index+1]
                operator.removeAt(index)
                operand.removeAt(index)
                operand.removeAt(index)
                operand.add(index, currentResult)
                println("end x")
            } else if ('/' in operator){
                println("msk /")
                val index = operator.indexOf('/')
                currentResult = operand[index] / operand[index+1]
                operator.removeAt(index)
                operand.removeAt(index)
                operand.removeAt(index)
                operand.add(index, currentResult)
                println("ed /")
            } else if ('+' in operator){
                println("op +")
                val index = operator.indexOf('+')
                currentResult = operand[index] + operand[index+1]
                operator.removeAt(index)
                operand.removeAt(index)
                operand.removeAt(index)
                operand.add(index, currentResult)
                println("ed +")
            } else if ('-' in operator){
                println("op -")
                val index = operator.indexOf('-')
                currentResult = operand[index] - operand[index+1]
                operator.removeAt(index)
                operand.removeAt(index)
                operand.removeAt(index)
                operand.add(index, currentResult)
                println("ed -")
            }
        }
        result = BigDecimal(operand[0])
        print("RESULT: ${result}")
        return operand[0]
    }
    fun backspace(view: View){
        with(binding){
            numberTop.text = numberTop.text.dropLast(1)
            updateResult()
        }
    }
    fun clear(view: View) {
        with(binding){
            numberTop.text = ""
            numberBottom.text = ""
        }
    }
    fun calculateFinal(view: View){
        with(binding){
//            calculate(numberTop.text.toString())
            updateResult()
            numberTop.text = numberBottom.text.toString()
            numberBottom.text = ""
        }
    }
}