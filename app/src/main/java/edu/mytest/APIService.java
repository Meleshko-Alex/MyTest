package edu.mytest;

import java.util.List;
import java.util.Map;

import edu.mytest.models.OneReviewModel;
import edu.mytest.models.ProductModel;
import edu.mytest.models.RegisterModel;
import edu.mytest.models.ReviewAnswerModel;
import edu.mytest.models.ReviewModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @FormUrlEncoded
    @POST("/api/register/")
    Call<RegisterModel> registerUser(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/api/login/")
    Call<RegisterModel> checkUser(@FieldMap Map<String, String> map);

    @POST("/api/reviews/1")
    Call<ReviewAnswerModel> setReview(@Body OneReviewModel review);

    @GET("/api/products/")
    Call<List<ProductModel>> getProducts();

    @GET("/api/reviews/{id}")
    Call<List<ReviewModel>> getReviews(@Path("id") int productId);
}
