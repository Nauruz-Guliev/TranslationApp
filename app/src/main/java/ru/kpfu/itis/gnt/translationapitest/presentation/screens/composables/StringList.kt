package ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.gnt.translationapitest.R

@Composable
fun StringList(
    modifier: Modifier = Modifier,
    list: List<String>,
    title: String,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TitleText(
            text = title,
            modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.size_6))
        )
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.size_6),
                vertical = dimensionResource(id = R.dimen.size_4)
            )
        ) {
            items(
                items = list,
                itemContent = {
                    Card(
                        modifier = modifier
                            .height(80.dp)
                            .padding(
                                top = dimensionResource(id = R.dimen.size_2),
                                start = dimensionResource(id = R.dimen.size_4),
                                end = dimensionResource(id = R.dimen.size_4),
                                bottom = dimensionResource(id = R.dimen.size_2)
                            )
                    ) {
                        RegularText(text = it, fontSize = dimensionResource(id = R.dimen.font_size_6))
                    }
                })
        }
    }
}
