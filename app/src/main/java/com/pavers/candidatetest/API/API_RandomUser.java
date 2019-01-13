package com.pavers.candidatetest.API;

import com.pavers.candidatetest.Modals.UserImageModal;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface API_RandomUser {

    @GET("/?inc=picture&")
    Observable<UserImageModal> getImage(
            @Query("gender") String sex
    );


}
