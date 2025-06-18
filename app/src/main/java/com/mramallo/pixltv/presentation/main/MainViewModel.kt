package com.mramallo.pixltv.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mramallo.pixltv.data.networking.MoviesRepository
import com.mramallo.pixltv.domain.Category
import com.mramallo.pixltv.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            _state.value = UiState(categories = repository.getCategories())
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val categories: Map<Category, List<Movie>> = emptyMap()
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}