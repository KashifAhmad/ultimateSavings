package com.techease.ultimatesavings.utils.networking;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIServices {

//    @Multipart
//    @POST("register")
//    Call<SignUpResponseModel> userSignUp(@Part("name") RequestBody name,
//                                         @Part("email") RequestBody email,
//                                         @Part("password") RequestBody password,
//                                         @Part("confirmPassword") RequestBody confirmPassword,
//                                         @Part("phoneNumber") RequestBody phoneNumber,
//                                         @Part("latitute") RequestBody lat,
//                                         @Part("longitude") RequestBody lon,
//                                         @Part("deviceType") RequestBody deviceType,
//                                         @Part("deviceToken") RequestBody deviceToken,
//                                         @Part MultipartBody.Part photo,
//                                         @Part("profilePicture") RequestBody profilePicture);
//
//    @FormUrlEncoded
//    @POST("login")
//    Call<LoginResponseModel> userLogin(@Field("email") String email,
//                                       @Field("password") String password,
//                                       @Field("deviceToken") String devieToken);
//
//    @FormUrlEncoded
//    @POST("verify-code")
//    Call<LoginResponseModel> userVerification(@Field("code") String code);
//    @FormUrlEncoded
//    @POST("reset-password")
//    Call<GenericResponseModel> resetPassword(@Field("email") String email);
//    @FormUrlEncoded
//    @POST("changePassword")
//    Call<GenericResponseModel> changePassword(@Field("newPassword") String newPassword,
//                                              @Field("confirmPassword") String confirmPassword);
//
//    @GET("get-all-properties")
//    Call<AllPropertiesResponseModel> propertiesList();
//
//    @FormUrlEncoded
//    @POST("checkProperty")
//    Call<OwnerFoundResponseModel> propertiesByPin(@Field("latitude") String lat,
//                                                  @Field("longitude") String lon,
//                                                  @Field("address") String address);
//
//    @Multipart
//    @POST("property")
//    Call<ManualEnterAddressResponse> postProperty(@Part MultipartBody.Part photo,
//                                                  @Part("propertyPicture") RequestBody propertyPicture,
//                                                  @Part("latitude") RequestBody lat,
//                                                  @Part("longitude") RequestBody lon,
//                                                  @Part("state") RequestBody state,
//                                                  @Part("city") RequestBody city,
//                                                  @Part("zipcode") RequestBody zipCode,
//                                                  @Part("address") RequestBody address,
//                                                  @Part("ownerName") RequestBody ownerName,
//                                                  @Part("ownerEmail") RequestBody ownerEmail,
//                                                  @Part("ownerPhone") RequestBody ownerPhone,
//                                                  @Part("ownerAddress") RequestBody ownerAddress);
//    @GET("get-all-properties")
//    Call<AllPropertiesResponseModel> getAllProperties();
//    @FormUrlEncoded
//    @POST("send-mailer")
//    Call<GenericResponseModel> startMailer(@Field("property_id") int propertyID,
//                                           @Field("template_id") int templateID);
//    @FormUrlEncoded
//    @POST("update-owner-info")
//    Call<UpdateInforResponseModel> updateInfo(@Field("propertyId") int propertyID,
//                                              @Field("ownerEmail") String email,
//                                              @Field("ownerName") String name,
//                                              @Field("ownerPhoneNumber") String phoneNumber,
//                                              @Field("ownerAddress") String address);
}

