package com.maloo.paggingapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.maloo.paggingapplication.data.api.PassangerService
import com.maloo.paggingapplication.databinding.FragmentPassengersBinding
import com.maloo.paggingapplication.ui.factory.PassengersViewModelFactory
import com.maloo.paggingapplication.ui.viewmodel.PassengersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PassengersFragment : Fragment() {
    private lateinit var viewModel: PassengersViewModel
    private lateinit var binding: FragmentPassengersBinding

    @Inject
    lateinit var service: PassangerService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPassengersBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = PassengersViewModelFactory(service)
        viewModel = ViewModelProvider(this, factory).get(PassengersViewModel::class.java)

        val passengersAdapter = PassengersAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter = passengersAdapter.withLoadStateHeaderAndFooter(
            header = PassengersLoadStateAdapter { passengersAdapter.retry() },
            footer = PassengersLoadStateAdapter { passengersAdapter.retry() }
        )

        passengersAdapter.addLoadStateListener { loadState ->
            run {
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(activity, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.passengers.collectLatest { pagedData ->
                passengersAdapter.submitData(pagedData)
            }
        }
    }
}