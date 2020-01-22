package com.omise.tamboon.ui.donation.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DonationResponse {
    @SerializedName("success")
    @Expose
    var success = false
    @SerializedName("error_code")
    @Expose
    var errorCode: String? = null
    @SerializedName("error_message")
    @Expose
    var errorMessage: String? = null


}
