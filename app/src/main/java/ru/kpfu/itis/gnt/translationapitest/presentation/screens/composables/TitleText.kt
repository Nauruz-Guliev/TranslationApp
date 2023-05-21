package ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.gnt.translationapitest.R

@Composable
fun TitleText(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.size_4)),
        fontSize = dimensionResource(id = R.dimen.font_size_4).value.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
private fun Preview() {
    TitleText(text = "TEXT")
}
