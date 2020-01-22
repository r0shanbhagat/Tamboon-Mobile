package com.omise.tamboon.service.repository;


import com.omise.tamboon.utility.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TamboonRepository {
    private static TamboonRepository projectRepository;
    private TamboonService tamboonService;

    private TamboonRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Companion.getAPI_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tamboonService = retrofit.create(TamboonService.class);
    }

    public static synchronized TamboonRepository getInstance() {
        if (projectRepository == null) {
            projectRepository = new TamboonRepository();
        }
        return projectRepository;
    }

    public TamboonService getTamboonService() {//please verify the cases
        return tamboonService;
    }
}
