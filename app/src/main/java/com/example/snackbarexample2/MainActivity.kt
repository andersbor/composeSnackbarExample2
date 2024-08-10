package com.example.snackbarexample2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.snackbarexample2.ui.theme.SnackbarExample2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SnackbarExample2Theme {
                ScaffoldWithSnackbars()
            }
        }
    }
}

@Composable
fun ScaffoldWithSnackbars() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var message by remember { mutableStateOf("") }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Simple Snackbar!")
                }
            }) {
                Text("Show simple Snackbar")
            }
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                scope.launch {
                    val result: SnackbarResult = snackbarHostState
                        .showSnackbar(
                            message = "Snackbar with action",
                            actionLabel = "Action",
                            withDismissAction = true,
                            duration = SnackbarDuration.Long
                        )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            Log.d("Snackbar", "Action performed")
                            message = "Action performed"
                        }
                        SnackbarResult.Dismissed -> {
                            Log.d("Snackbar", "Dismissed")
                            message = "Dismissed"
                        }
                    }
                }
            }) {
                Text("Show Snackbar with action")
            }
            Text(message)
        }
    }
}

@Preview
@Composable
fun ScaffoldWithSnackbarsPreview() {
    SnackbarExample2Theme {
        ScaffoldWithSnackbars()
    }
}
