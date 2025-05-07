package com.mramallo.pixltv.presentation.main

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.mramallo.pixltv.R
import com.mramallo.pixltv.domain.Movie


class MainFragment: BrowseSupportFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = getString(R.string.app_name)

        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        (1..5).forEach { categoryId ->
            val categoryTitle = "Category $categoryId"

            val listRowAdapter = ArrayObjectAdapter(CardPresenter())
            listRowAdapter.addAll(0, (1..10).map {
                Movie(
                    title = "Movie $it",
                    year = 2021,
                    poster = "https://picsum.photos/1062/200?random=$it"
                )
            })

            val header = HeaderItem(categoryId.toLong(), categoryTitle)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }

        adapter = rowsAdapter

    }

}
