package com.hassan.currencycalc.ui.views

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hassan.currencycalc.Utils
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import java.text.DecimalFormat

@Composable
fun MainGraph(line: List<DataPoint>, symbols: String, onDateSelected: (String) -> Unit ) {
    val totalWidth = remember { mutableStateOf(0) }

    Column(Modifier.onGloballyPositioned {
        totalWidth.value = it.size.width
    }) {
        val cardWidth = remember { mutableStateOf(0) }
        val greenColor = MaterialTheme.colors.primaryVariant
        val blueColor = MaterialTheme.colors.onPrimary
        val xOffset = remember { mutableStateOf(0f) }
        val visibility = remember { mutableStateOf(false) }
        val points = remember { mutableStateOf(listOf<DataPoint>()) }
        var clicked30Days by remember { mutableStateOf(true) }
        var clicked90Days by remember { mutableStateOf(false) }


        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {

            //30 days Text Button
            TextButton(onClick = {
                if(!clicked30Days) {
                    clicked30Days = !clicked30Days
                    clicked90Days = !clicked90Days
                    onDateSelected(Utils.calculatePastDate(30))
                }
            },
                shape = RoundedCornerShape(6.dp)
            ) {
                Column {
                    Text(text = "Past 30 days", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Canvas(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(20.dp)
                            .size(4.dp),
                        onDraw = {
                            drawCircle(
                                color = if (clicked30Days) greenColor else blueColor,
                                3f * density,
                                Offset(0f, 10f * density)
                            )
                        })
                }

            }

            //90 days Text Button
            TextButton(onClick = {
                if(!clicked90Days) {
                    clicked90Days = !clicked90Days
                    clicked30Days = !clicked30Days
                    onDateSelected(Utils.calculatePastDate(90))
                }
            },
                shape = RoundedCornerShape(6.dp)
            ) {
                Column {
                    Text(text = "Past 90 days", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Canvas(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(20.dp)
                            .size(4.dp),
                        onDraw = {
                            drawCircle(
                                color = if (clicked90Days) greenColor else blueColor,
                                3f * density,
                                Offset(0f, 10f * density)
                            )
                        })
                }

            }
        }

        //box for displaying highlighted selection of a data point in the graph
        Box(Modifier.height(100.dp)) {
            if (visibility.value) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .onGloballyPositioned {
                            //this is used to retrieve the composable layout coordinates after every
                            //composition
                            cardWidth.value = it.size.width
                        }
                        .graphicsLayer(translationX = xOffset.value),
                    shape = RoundedCornerShape(8.dp),
                    color = greenColor
                ) {
                    Column(
                        Modifier
                            .padding(8.dp)
                    ) {

                        val lineValues =  arrayListOf<DataPointModel>()
                        if( points.value.isNotEmpty()) {
                            for (it in points.value) {
                                Log.d("line", "${it}")
                                val x = it.x.toInt().toString()
                                val y = DecimalFormat("0").format(it.y)
                                lineValues.add(DataPointModel(x, y))
                            }

                            val x = lineValues[0].x.takeLast(2)
                            val y = lineValues[0].y

                            Log.d("xy", "$x $y")

                            val month = Utils.calculateMonth(lineValues[0].x.toString()
                                .substring(2, 4).toInt())

                            Text(
                                text = "$x $month",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                            Text(
                                text = "1 EUR = $y $symbols",
                                style = MaterialTheme.typography.body2,
                                color = Color.White
                            )

                        }
                    }
                }

            }
        }

        MaterialTheme(colors = MaterialTheme.colors.copy(surface = MaterialTheme.colors.onPrimary)) {
            LineGraph(
                plot = LinePlot(
                    listOf(
                        LinePlot.Line(
                            dataPoints = line.map { dataPoint ->
                                DataPoint(
                                    dataPoint.x.toInt().toString().takeLast(6).toFloat(),
                                    dataPoint.y)
                            },
                            connection = null,
                            intersection = null,
                            highlight = LinePlot.Highlight { center ->
                                drawCircle(Color.White, 5.dp.toPx(), center)
                                drawCircle(greenColor, 3.dp.toPx(), center)
                            },
                            areaUnderLine = LinePlot.AreaUnderLine(
                                color = MaterialTheme.colors.primary, alpha = 0.5f
                            )
                        )
                    ),
                    selection = LinePlot.Selection(
                        highlight = LinePlot.Connection(
                            Color.Red,
                            strokeWidth = 2.dp,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
                        )
                    ),
                    isZoomAllowed = false,
                    //x-axis
                    xAxis = LinePlot.XAxis(
                        steps = 100,
                        paddingTop = 0.dp
                    ) { min, offset, max ->
                        for (it in line) {
                            Log.d("it", "${it}")
                            val value = it.x.toInt().toString().takeLast(2).toFloat()
                            Column {

                                val space = line.indexOf(it) % 3 == 1

                                Canvas(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(20.dp)
                                        .size(4.dp),
                                    onDraw = {
                                        drawRect(
                                            color = Color.White,
                                            alpha = 1f,
                                            size = Size(4f, 8f)
                                        )
                                    })

                                if(space) {
                                    val month = Utils.calculateMonth(
                                        it.x.toInt().toString().substring(2, 4).toInt()
                                    )
                                    //log
                                    if (month != null) {
                                        Log.d("month", "$month + ${
                                            it.x.toInt().toString().substring(2, 4)
                                        }")
                                    }

                                    Text(
                                        modifier = Modifier.padding(start = 24.dp),
                                        text = DecimalFormat("00 '${month ?: ""}'").format(value),
                                        maxLines = 1,
                                        overflow = TextOverflow.Visible,
                                        style = MaterialTheme.typography.caption,
                                        color = Color.White
                                    )
                                }
                            }
                            if (value > max) {
                                break
                            }
                        }
                    },
                    yAxis = LinePlot.YAxis(steps = 2, paddingEnd = 0.dp) { min, offset, max ->
                        for (it in 0..7) {
                            val value = it * offset + min
                            Column {
                                Canvas(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(20.dp)
                                        .size(4.dp),
                                    onDraw = {
                                        drawRect(
                                            color = Color.White,
                                            alpha = 1f,
                                            size = Size(8f, 4f)
                                        )
                                    })
                            }
                            if (value > max) {
                                break
                            }
                        }
                    }
                ),
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onPrimary,
                    )
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 24.dp, 8.dp)
                    .height(250.dp),
                onSelectionStart = { visibility.value = true },
                onSelectionEnd = {  }
            ) { x, pts ->
                val cWidth = cardWidth.value.toFloat()
                var xStart = x
                xStart = when {
                    xStart + cWidth > totalWidth.value -> totalWidth.value - cWidth - 24f
                    else -> xStart
                }
                xOffset.value = xStart
                points.value = pts
            }
        }

    }

}

data class DataPointModel(
    val x: String,
    val y: String
)