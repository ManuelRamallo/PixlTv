package com.mramallo.pixltv.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mramallo.pixltv.R
import com.mramallo.pixltv.data.networking.MoviesRepository
import com.mramallo.pixltv.domain.Category
import com.mramallo.pixltv.domain.Movie
import com.mramallo.pixltv.presentation.detail.DetailActivity
import kotlinx.coroutines.launch


class MainFragment: BrowseSupportFragment() {

    private val backgroundState = BackgroundState(this)
    private val viewmodel by viewModels<MainViewModel> {
        MainViewModelFactory(MoviesRepository(getString(R.string.api_key)))
    }

    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = getString(R.string.app_name)

        adapter = rowsAdapter

        /*viewLifecycleOwner.lifecycleScope.launch {
            adapter =  buildAdapter()
        }*/

        onItemViewClickedListener = OnItemViewClickedListener { vh, movie, _, _ ->
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                (vh.view as ImageCardView).mainImageView,
                DetailActivity.SHARED_ELEMENT_NAME
            ).toBundle()

            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.MOVIE_EXTRA, movie as Movie)
            }
            startActivity(intent, bundle)

        }

        onItemViewSelectedListener = OnItemViewSelectedListener { _, movie, _, _ ->
            (movie as? Movie)?.let {
                backgroundState.loadUrl(it.backdrop)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewmodel.state.collect {
                    if(it.isLoading) progressBarManager.show() else progressBarManager.hide()
                    updateUi(it.categories)
                }
            }
        }

        viewmodel.onUiReady()
    }

    private fun updateUi(categories: Map<Category, List<Movie>>) {
        rowsAdapter.clear()
        val cardPresenter = CardPresenter()
        categories.forEach { (category, movies) ->

            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            listRowAdapter.addAll(0, movies)

            val header = HeaderItem(category.ordinal.toLong(), category.name)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }

}
