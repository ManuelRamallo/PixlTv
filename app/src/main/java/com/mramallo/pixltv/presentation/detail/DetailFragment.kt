package com.mramallo.pixltv.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import com.mramallo.pixltv.domain.Movie

class DetailFragment: DetailsSupportFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie: Movie = TODO()
        val presenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())


    }


}

