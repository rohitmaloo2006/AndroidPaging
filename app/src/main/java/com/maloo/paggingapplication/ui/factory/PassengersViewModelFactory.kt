package com.maloo.paggingapplication.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maloo.paggingapplication.data.api.PassangerService
import com.maloo.paggingapplication.ui.viewmodel.PassengersViewModel
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class PassengersViewModelFactory @Inject constructor(
    private val api: PassangerService
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassengersViewModel(api) as T
    }
}