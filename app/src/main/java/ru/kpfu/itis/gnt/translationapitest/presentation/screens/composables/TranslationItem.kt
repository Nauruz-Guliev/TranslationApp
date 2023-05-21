package ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TranslationItem(
    modifier: Modifier = Modifier,
    definition: TranslationUiModel.Definition,
    onItemClicked: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.size_4),
                vertical = dimensionResource(id = R.dimen.size_2)
            ),
        onClick = { onItemClicked(definition.id) }
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            RegularText(
                text = definition.text,
                fontSize = dimensionResource(id = R.dimen.font_size_4),
                modifier = modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
            )
            Column(modifier = modifier) {
                definition.ts?.let {
                    RegularText(
                        text = stringResource(
                            id = R.string.transcription,
                            definition.ts
                        ),
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
                RegularText(
                    text = stringResource(
                        id = R.string.type,
                        definition.pos
                    ),
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
                RegularText(
                    text = stringResource(
                        id = R.string.definition_amount,
                        definition.tr.size.toString()
                    ),
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
