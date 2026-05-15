package com.example.calculator



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val buttons = listOf(
        listOf("7","8","9","+"),
        listOf("4","5","6","-"),
        listOf("1","2","3","*"),
        listOf("0","C","=","/")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = input,
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Text(
                text = result,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    CalcButton(label) {
                        when (label) {
                            "C" -> {
                                input = ""
                                result = ""
                            }
                            "=" -> {
                                result = calculateSimple(input)
                            }
                            else -> input += label
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalcButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 22.sp)
    }
}

fun calculateSimple(expression: String): String {
    return try {
        val operator = when {
            expression.contains("+") -> "+"
            expression.contains("-") -> "-"
            expression.contains("*") -> "*"
            expression.contains("/") -> "/"
            else -> return ""
        }

        val parts = expression.split(operator)
        if (parts.size != 2) return ""

        val a = parts[0].toDouble()
        val b = parts[1].toDouble()

        when (operator) {
            "+" -> (a + b).toString()
            "-" -> (a - b).toString()
            "*" -> (a * b).toString()
            "/" -> (a / b).toString()
            else -> ""
        }
    } catch (e: Exception) {
        "Error"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalc() {
    CalculatorScreen()
}