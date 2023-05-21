package ru.kpfu.itis.gnt.translationapitest.core.resource

import android.content.Context
import androidx.annotation.AnyRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class Resource(
    @AnyRes open val id: Int
) {
    /**
     * Утилитный класс для хранения, получения и передачи строк.
     */
    data class String(
        @StringRes override var id: Int,
    ) : Resource(id) {
        fun getValue(context: Context, vararg arguments: Any): kotlin.String =
            context.resources.getString(id, arguments)

        fun getValue(context: Context): kotlin.String = context.resources.getString(id)

        @Composable
        fun getValue(): kotlin.String {
            return stringResource(id = id)
        }

        @Composable
        fun getValue(vararg arguments: Any): kotlin.String {
            return stringResource(id = id, arguments)
        }
    }
}
