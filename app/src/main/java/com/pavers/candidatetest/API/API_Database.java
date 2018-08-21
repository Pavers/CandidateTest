package com.pavers.candidatetest.API;

import com.pavers.candidatetest.Modals.APIResponseModal;
import com.pavers.candidatetest.Modals.UserModal;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface API_Database {

    //Users
    @GET("users")
    Observable<List<UserModal>> getAllUsers();

    @DELETE("users")
    Observable<APIResponseModal> deleteUser(
            @Query("userID") int userID
    );

    // TODO maybe change back.
    // I changed this to POST and got slightly better results but still no success.
    @POST("users")
    Observable<APIResponseModal> updateUser(
            @Body RequestBody body
    );

    // I changed this to PUT and got slightly better results but still no success.
    @PUT("users")
    Observable<UserModal> createUser(
            @Body RequestBody body
    );

}
