package com.maloo.paggingapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.maloo.paggingapplication.data.api.PassangerService
import com.maloo.paggingapplication.data.datasource.PassengersDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PassengersViewModel @Inject constructor(
    private val service: PassangerService
) : ViewModel() {
    val passengers = Pager(PagingConfig(pageSize = 10)) {
        PassengersDataSource(service)
    }.flow.cachedIn(viewModelScope)

}