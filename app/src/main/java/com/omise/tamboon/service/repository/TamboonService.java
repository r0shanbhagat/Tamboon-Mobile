package com.omise.tamboon.service.repository;

import com.omise.tamboon.ui.donation.model.DonationRequestModel;
import com.omise.tamboon.ui.donation.model.DonationResponse;
import com.omise.tamboon.ui.listing.CharityResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TamboonService {
    @GET("charities")
    Call<CharityResponse> getCharityList();

    @POST("donations")
    Call<DonationResponse> updateDonation(@Body DonationRequestModel login);
}
