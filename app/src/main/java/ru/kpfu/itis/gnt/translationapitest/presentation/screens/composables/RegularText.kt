package ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.gnt.translationapitest.R

@Composable
fun RegularText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Dp = dimensionResource(id = R.dimen.font_size_2),
    fontColor: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start
) {
    MaterialTheme.colors.error
    Text(
        text = text,
        textAlign = textAlign,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.size_4),
                vertical = dimensionResource(id = R.dimen.size_2)
            ),
        fontSize = fontSize.value.sp,
        color = fontColor
    )
}
