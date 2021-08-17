package com.maloo.paggingapplication.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maloo.paggingapplication.data.api.PassangerService
import com.maloo.paggingapplication.data.model.Passenger
import javax.inject.Inject


class PassengersDataSource @Inject constructor
    (private val passService: PassangerService) :
    PagingSource<Int, Passenger>() {

    override fun getRefreshKey(state: PagingState<Int, Passenger>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Passenger> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = passService.getPassengersData(nextPageNumber)
            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalPages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
