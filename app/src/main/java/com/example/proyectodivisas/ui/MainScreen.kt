package com.example.proyectodivisas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.proyectodivisas.model.TipoCambio
import com.example.proyectodivisas.ui.theme.ProyectoDivisasTheme
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoDivisasTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var monedaSeleccionada by remember { mutableStateOf("USD") }
    var fechaInicio by remember { mutableStateOf(Date()) }
    var fechaFin by remember { mutableStateOf(Date()) }
    var datosGrafica by remember { mutableStateOf<List<TipoCambio>>(emptyList()) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Selector de moneda
        Text(text = "Selecciona una moneda:")
        Spacer(modifier = Modifier.height(8.dp))
        MonedaSelector(monedaSeleccionada) { monedaSeleccionada = it }

        // Selector de fechas
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Selecciona un rango de fechas:")
        Spacer(modifier = Modifier.height(8.dp))
        RangoFechasSelector(fechaInicio, fechaFin) { inicio, fin ->
            fechaInicio = inicio
            fechaFin = fin
        }

        // Botón para cargar datos
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Aquí llamarías al ContentProvider para obtener los datos
            // y actualizar `datosGrafica`
        }) {
            Text(text = "Cargar datos")
        }

        // Gráfica
        Spacer(modifier = Modifier.height(16.dp))
        if (datosGrafica.isNotEmpty()) {
            GraficaTipoCambio(datosGrafica)
        }
    }
}

@Composable
fun GraficaTipoCambio(datos: List<TipoCambio>) {
    val entries = datos.mapIndexed { index, tipoCambio ->
        Entry(index.toFloat(), tipoCambio.valor.toFloat())
    }

    val dataSet = LineDataSet(entries, "Tipo de Cambio")
    val lineData = LineData(dataSet)

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                data = lineData
                invalidate() // Refresca el gráfico
            }
        },
        modifier = Modifier.height(300.dp)
    )
}