package com.mramallo.pixltv.presentation.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mramallo.pixltv.domain.Movie
import com.mramallo.pixltv.presentation.common.loadImageUrl
import com.mramallo.pixltv.presentation.detail.DetailActivity.Companion.MOVIE_EXTRA
import kotlinx.coroutines.launch

class DetailFragment : DetailsSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val detailBackgroundState = DetailBackgroundState(this)

    private val viewModel by viewModels<DetailViewModel> {
        val movie = checkNotNull(requireActivity().intent.parcelable<Movie>(MOVIE_EXTRA))

        DetailViewModelFactory(movie)
    }

    private lateinit var presenter: FullWidthDetailsMoviesRowPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = FullWidthDetailsMoviesRowPresenter(requireActivity())

        rowsAdapter = ArrayObjectAdapter(presenter)
        adapter = rowsAdapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect {
                    updateUi(it.movie)
                }
            }
        }
    }

    private fun updateUi(movie: Movie) {
        val row = DetailsOverviewRow(movie)
        row.loadImageUrl(requireContext(), movie.poster)
        row.actionsAdapter = presenter.buildActions(presenter)
        rowsAdapter.add(row)
        detailBackgroundState.loadUrl(movie.backdrop)
    }

}

inline fun <reified T: Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}
