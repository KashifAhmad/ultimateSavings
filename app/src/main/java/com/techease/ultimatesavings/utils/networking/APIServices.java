package com.techease.ultimatesavings.utils.networking;

import com.techease.ultimatesavings.models.allShopsModel.AllShopsModel;
import com.techease.ultimatesavings.models.changePasswordModel.ChangePasswordModel;
import com.techease.ultimatesavings.models.filteredShops.FilteredShopsModel;
import com.techease.ultimatesavings.models.verifyEmailModels.VerifyEmailModel;
import com.techease.ultimatesavings.models.loginModels.LoginModel;
import com.techease.ultimatesavings.models.searchShop.SearchShop;
import com.techease.ultimatesavings.models.signModels.SignupModels;
import com.techease.ultimatesavings.models.verifyCodeModel.VerifyCodeModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServices {
    @FormUrlEncoded
    @POST("signup/register")
    Call<SignupModels> signUp(@Field("email") String email,
                              @Field("password") String password,
                              @Field("first_name") String fName,
                              @Field("last_name") String lName,
                              @Field("dob") String dob,
                              @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("signup/login")
    Call<LoginModel> login(@Field("email") String email,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("signup/forgot")
    Call<VerifyEmailModel> emailVerificaiton(@Field("email") String email);

    @FormUrlEncoded
    @POST("signup/CheckCode")
    Call<VerifyCodeModel> verifyCode(@Field("code") String code);

    @FormUrlEncoded
    @POST("signup/Resetpassword")
    Call<ChangePasswordModel> changePassword(@Field("code") String code,
                                             @Field("password") String password);

    @FormUrlEncoded
    @POST("app/shops")
    Call<AllShopsModel> shops(@Field("lat") String lat,
                              @Field("lng") String lng);

    @FormUrlEncoded
    @POST("app/filterShops")
    Call<FilteredShopsModel> filteredShops(@Field("lat") String lat,
                                           @Field("lon") String lng,
                                           @Field("distance") String distance,
                                           @Field("category") String category,
                                           @Field("price") String price);


    @FormUrlEncoded
    @POST("app/search")
    Call<SearchShop> searchShop(@Field("lat") String lat,
                                @Field("lon") String lng,
                                @Field("title") String title);

}

