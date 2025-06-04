package com.mramallo.pixltv.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import com.mramallo.pixltv.R
import com.mramallo.pixltv.domain.Movie
import com.mramallo.pixltv.presentation.common.loadImageUrl
import java.lang.StackWalker.Option

class DetailFragment : DetailsSupportFragment() {

    private enum class OptionsVOD(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_trailer),
        FAVORITE(R.string.favorite)
    }


    private val detailBackgroundState = DetailBackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity()
            .intent.getParcelableExtra<Movie>(DetailActivity.MOVIE_EXTRA)
            ?: throw IllegalStateException("Movie not found")


        val presenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())

        val rowsAdapter = ArrayObjectAdapter(presenter)
        val detailsOverviewRow = DetailsOverviewRow(movie)
        detailsOverviewRow.loadImageUrl(requireContext(), movie.poster)
        detailsOverviewRow.actionsAdapter = buildActions(presenter)


        rowsAdapter.add(detailsOverviewRow)

        adapter = rowsAdapter

        detailBackgroundState.loadUrl(movie.backdrop)
    }

    private fun buildActions(presenter: FullWidthDetailsOverviewRowPresenter): ArrayObjectAdapter {

        presenter.setOnActionClickedListener { action ->
            val option = OptionsVOD.entries.first { it.ordinal == action.id.toInt() }
            Toast.makeText(requireContext(), option.stringRes, Toast.LENGTH_SHORT).show()
        }

        return ArrayObjectAdapter().apply {
            OptionsVOD.entries.forEach { option ->
                add(Action(option.ordinal.toLong(), getString(option.stringRes)))
            }
        }
    }

}

