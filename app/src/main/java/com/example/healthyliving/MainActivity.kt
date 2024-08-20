package com.example.healthyliving

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import coil.compose.rememberAsyncImagePainter
import com.example.healthyliving.ui.theme.HealthyLivingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthyLivingTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RecipeApp()
                }
            }
        }
    }
}

@Composable
fun RecipeApp() {
    var recipeName by remember { mutableStateOf(TextFieldValue("")) }
    var imageUrl by remember { mutableStateOf(TextFieldValue("")) }
    val itemList = remember { mutableStateListOf<Pair<String, String>>() }

    Column(modifier = Modifier.padding(16.dp)) {
        BasicTextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                if (recipeName.text.isEmpty()) {
                    Text(text = "Nombre de la receta", style = MaterialTheme.typography.bodyMedium)
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                if (imageUrl.text.isEmpty()) {
                    Text(text = "URL de la imagen", style = MaterialTheme.typography.bodyMedium)
                }
                innerTextField()
            }
        )

        Button(
            onClick = {
                if (recipeName.text.isNotEmpty() && imageUrl.text.isNotEmpty()) {
                    itemList.add(Pair(recipeName.text, imageUrl.text))
                    recipeName = TextFieldValue("")
                    imageUrl = TextFieldValue("")
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Agregar")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(itemList) { item ->
                RecipeCard(name = item.first, imageUrl = item.second)
            }
        }
    }
}

@Composable
fun RecipeCard(name: String, imageUrl: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = name,
                modifier = Modifier.size(256.dp).padding(end = 8.dp)
            )

            Text(text = name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HealthyLivingTheme {
        RecipeApp()
    }
}