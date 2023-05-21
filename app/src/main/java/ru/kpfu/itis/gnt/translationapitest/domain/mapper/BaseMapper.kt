package ru.kpfu.itis.gnt.translationapitest.domain.mapper

interface BaseMapper<From, To> {
    fun toModel(model: To): From
    fun fromModem(model: From): To?
}
