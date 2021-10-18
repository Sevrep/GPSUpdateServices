package com.sevrep.gpsupdateservices.interfaces;

import com.sevrep.gpsupdateservices.models.PostmenPositionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by : Ivan Peter Semilla on 18/10/2021
 */
public interface PostmanPositionWebApiRequest {
    @GET("postman-position-mobile/get-postman-position")
    Call<List<PostmenPositionModel>> GetPostmanPosition();
}
