package com.sberkozd.europa.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.europa.data.models.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _countryListResponse: MutableStateFlow<List<Country>> = MutableStateFlow(
        mutableListOf()
    )
    val countryListResponse: StateFlow<List<Country>> = _countryListResponse

    private var lastSelectedFilter: String = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCountryList(
                onError = {
                    // do something
                }
            )
                .stateIn(viewModelScope)
                .collect {
                    _countryListResponse.emit(it)
                }
        }
    }

    fun sortByAlphabetically() {

        viewModelScope.launch {
            _countryListResponse.value = _countryListResponse.value.sortedBy {
                it.name?.common
            }
            eventChannel.send(
                SortEvent.SortAlphabetically(_countryListResponse.value)
            )
        }

    }

    fun sortByPopulation() {

        viewModelScope.launch {
            _countryListResponse.value = _countryListResponse.value.sortedBy {
                it.population
            }
            eventChannel.send(
                SortEvent.SortByPopulation(_countryListResponse.value)
            )
        }

    }

    fun sortByArea() {

        viewModelScope.launch {
            _countryListResponse.value = _countryListResponse.value.sortedBy {
                it.area
            }
            eventChannel.send(
                SortEvent.SortByArea(_countryListResponse.value)
            )
        }
    }

    fun showFilterDialog() {

        viewModelScope.launch {

            val subRegionList = _countryListResponse.value.map { it.subregion.orEmpty() }.distinct()

            eventChannel.send(
                Event.ShowFilterDialog(subRegionList, lastSelectedFilter)
            )
        }
    }

    fun filterBySubRegion(subRegion: String) {
        viewModelScope.launch {
            lastSelectedFilter = subRegion
            eventChannel.send(
                Event.FilterBySubRegion(_countryListResponse.value.filter {
                    it.subregion == subRegion
                })
            )
        }
    }

    sealed class Event {
        data class ShowFilterDialog(
            var subRegionList: List<String>,
            val lastSelectedFilter: String
        ) : Event()

        data class FilterBySubRegion(var list: List<Country>) : Event()
    }

    sealed class SortEvent(var list: List<Country>) : Event() {
        data class SortAlphabetically(var mList: List<Country>) : SortEvent(mList)
        data class SortByPopulation(var mList: List<Country>) : SortEvent(mList)
        data class SortByArea(var mList: List<Country>) : SortEvent(mList)
    }
}