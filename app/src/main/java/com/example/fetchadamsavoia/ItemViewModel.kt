package com.example.fetchadamsavoia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchadamsavoia.data.FetchModel
import com.example.fetchadamsavoia.data.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel: ViewModel() {
    private val repo = ItemRepository()

    private val _getItems = MutableLiveData<List<FetchModel>>()
    val getItems: LiveData<List<FetchModel>> = _getItems

    fun fetchItems() {
        viewModelScope.launch {
            try {
                // trying to get the items from the repo
                val items = repo.getItems()

                // Organizing items. First taking out the null or blank item names.
                // Then sort by "list id" and within "list id" sort by "id" because "id"
                // has the same number as "name"
                _getItems.value = items
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy<FetchModel> { item ->
                        item.listId
                    }.thenBy { item ->
                        item.id.toInt()
                    })
            } catch (e: Exception) {
                println("$e")
            }
        }
    }


}