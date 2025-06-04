package com.mramallo.pixltv.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import com.mramallo.pixltv.domain.Movie
import com.mramallo.pixltv.presentation.common.loadImageUrl

class DetailFragment : DetailsSupportFragment() {

    private val detailBackgroundState = DetailBackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity()
            .intent.getParcelableExtra<Movie>(DetailActivity.MOVIE_EXTRA)
            ?: throw IllegalStateException("Movie not found")


        val presenter = FullWidthDetailsMoviesRowPresenter(requireActivity())

        val rowsAdapter = ArrayObjectAdapter(presenter)
        val detailsOverviewRow = DetailsOverviewRow(movie)
        detailsOverviewRow.loadImageUrl(requireContext(), movie.poster)
        detailsOverviewRow.actionsAdapter = presenter.buildActions(presenter)


        rowsAdapter.add(detailsOverviewRow)

        adapter = rowsAdapter

        detailBackgroundState.loadUrl(movie.backdrop)
    }

}

