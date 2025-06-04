package com.mramallo.pixltv.presentation.detail

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper
import com.mramallo.pixltv.R

class FullWidthDetailsMoviesRowPresenter(
    private val activity: Activity
): FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter()) {

    private enum class OptionsVOD(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_trailer),
        FAVORITE(R.string.favorite)
    }

    init {
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(activity, DetailActivity.SHARED_ELEMENT_NAME)

        this.setListener(sharedElementHelper)
        this.isParticipatingEntranceTransition = true
    }

    fun buildActions(presenter: FullWidthDetailsOverviewRowPresenter): ArrayObjectAdapter {

        presenter.setOnActionClickedListener { action ->
            val option = OptionsVOD.entries.first { it.ordinal == action.id.toInt() }
            Toast.makeText(activity, option.stringRes, Toast.LENGTH_SHORT).show()
        }

        return ArrayObjectAdapter().apply {
            OptionsVOD.entries.forEach { option ->
                add(Action(option.ordinal.toLong(), activity.getString(option.stringRes)))
            }
        }
    }

}