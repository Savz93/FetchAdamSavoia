package com.example.fetchadamsavoia

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.fetchadamsavoia.data.FetchModel
import com.example.fetchadamsavoia.data.ItemRepository
import com.example.fetchadamsavoia.network.ApiService
import com.example.fetchadamsavoia.ui.theme.FetchAdamSavoiaTheme
import kotlinx.coroutines.launch

// Display all the items grouped by "listId"
// Sort the results first by "listId" then by "name" when displaying.
// Filter out any items where "name" is blank or null.

class MainActivity : ComponentActivity() {
    private val viewModel: ItemViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApp(viewModel)
        }
    }
}

// This composable will pull all the data and UI together creating the final product
@Composable
fun MyApp(viewModel: ItemViewModel) {
    val items by viewModel.getItems.observeAsState(emptyList())
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.fetchItems()
        }
    }

    if (items.isEmpty()) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(120.dp))
        }
    } else {
        ListOfCards(itemsFetch = items)
    }

}

// This composable takes each item and passes it through the CardLayout composable
@Composable
fun ListOfCards(itemsFetch: List<FetchModel>) {
    LazyColumn {
        items(itemsFetch) {
            CardLayout(name = it.name, id = it.id, listId = it.listId)
        }
    }
}

// This composable will set up the view for each card that the data will be populating
@Composable
fun CardLayout(name: String, id: String, listId: String) {
    Card (
        modifier = Modifier
            .fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {

            Column(modifier = Modifier){

                Text(
                    text = "List Id: $listId",
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Text(
                    text = "Id: $id",
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 12.dp)
                )

            }

            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 12.dp)
            )
            
        }
    }
}

@Preview
@Composable
fun CardLayoutPreview() {
    CardLayout(name = "Item 176", id = "176", listId = "2")
}

