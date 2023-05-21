package ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel

@Composable
fun TranslationList(
    modifier: Modifier = Modifier,
    list: List<TranslationUiModel.Definition>,
    title: String,
    onItemClicked: (TranslationUiModel.Definition) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TitleText(
            text = title,
            modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.size_6))
        )
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.size_6),
                vertical = dimensionResource(id = R.dimen.size_4)
            )
        ) {
            items(
                items = list,
                itemContent = {
                    TranslationItem(definition = it, onItemClicked = onItemClicked)
                })
        }
    }
}

