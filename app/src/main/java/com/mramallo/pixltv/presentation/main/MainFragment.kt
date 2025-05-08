package com.mramallo.pixltv.presentation.main

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.lifecycleScope
import com.mramallo.pixltv.R
import com.mramallo.pixltv.data.mappers.toMovie
import com.mramallo.pixltv.data.networking.MoviesRepository
import com.mramallo.pixltv.data.networking.RemoteConnection
import kotlinx.coroutines.launch


class MainFragment: BrowseSupportFragment() {

    private lateinit var moviesRepository: MoviesRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = getString(R.string.app_name)

        moviesRepository = MoviesRepository(getString(R.string.api_key))

        viewLifecycleOwner.lifecycleScope.launch {
            adapter =  buildAdapter()
        }
    }

    private suspend fun buildAdapter(): ArrayObjectAdapter {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()
        moviesRepository.getCategories().forEach { (category, movies) ->

            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            listRowAdapter.addAll(0, movies)

            val header = HeaderItem(category.ordinal.toLong(), category.name)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
        return rowsAdapter
    }

}
