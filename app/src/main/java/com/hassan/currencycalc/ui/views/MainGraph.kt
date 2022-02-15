package com.hassan.currencycalc.ui.views

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
fun MainGraph(lines: List<List<DataPoint>>, symbols: String, onDateSelected: (String) -> Unit ) {
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
                        val value = points.value

                        if (value.isNotEmpty()) {
                            val x = DecimalFormat("00").format(value[0].x)
                            val y = DecimalFormat("0").format(value[0].y)


                            Text(
                                text = "$x day",
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
                            dataPoints = if(clicked30Days) lines[0] else lines[1],
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
                    xAxis = LinePlot.XAxis(
                        steps = if(clicked30Days) lines[0].size else lines[1].size,
                        paddingTop = 0.dp
                    ) { min, offset, max ->
                        for (it in if(clicked30Days) lines[0] else lines[1]) {
                            val value = it.x * offset
                            Column {

                                val isRemainderOne = value % 3 == 1f

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

                                if(isRemainderOne) {
                                    Text(
                                        modifier = Modifier.padding(start = 24.dp),
                                        text = DecimalFormat("00 'day'").format(value),
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
                    yAxis = LinePlot.YAxis(steps = 7, paddingEnd = 0.dp) { min, offset, max ->
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
