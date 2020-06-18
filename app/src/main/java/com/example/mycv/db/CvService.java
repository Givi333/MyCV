package com.example.mycv.db;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface CvService {
    @Streaming
    @GET("s/ccxnp2q20s10hny/556.txt?dl=0")
    Observable<MyResponse> getInfo();
}
