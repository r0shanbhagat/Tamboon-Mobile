package com.omise.tamboon.ui.listing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Charity(
        @SerializedName("id")
        @Expose
        var author: Integer? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("logo_url")
        @Expose
        var logoUrl: String? = null
)

class CharityResponse {
    @SerializedName("total")
    @Expose
    var total = 0
    @SerializedName("data")
    @Expose
    var charities: List<Charity?>? = null


}
