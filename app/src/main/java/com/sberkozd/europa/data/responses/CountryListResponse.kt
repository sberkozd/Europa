package com.sberkozd.europa.data.responses

import com.sberkozd.europa.data.models.Country
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CountryListResponse : MutableList<Country> by mutableListOf()