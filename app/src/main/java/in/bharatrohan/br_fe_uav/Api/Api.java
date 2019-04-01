package in.bharatrohan.br_fe_uav.Api;

import java.util.List;

import in.bharatrohan.br_fe_uav.Models.CreatesVisit;
import in.bharatrohan.br_fe_uav.Models.CropProblem;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.Models.FeDetails;
import in.bharatrohan.br_fe_uav.Models.FeVisitsModel;
import in.bharatrohan.br_fe_uav.Models.LoginFE;
import in.bharatrohan.br_fe_uav.Models.LoginUAV;
import in.bharatrohan.br_fe_uav.Models.Responses;
import in.bharatrohan.br_fe_uav.Models.UavDetails;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface Api {

    @POST("fe/login")
    Call<LoginFE> loginFE(@Body LoginFE loginFE);

    @POST("uav/login")
    Call<LoginUAV> loginUAV(@Body LoginUAV loginUAV);


    @GET("fe/{id}")
    Call<FeDetails> getFeDetail(@Header("Authorization") String token,
                                @Path("id") String feId);

    @GET("uav/{id}")
    Call<UavDetails> getUavDetail(@Header("Authorization") String token,
                                  @Path("id") String feId);

    @GET()
    @Streaming
    Call<ResponseBody> downloadKml(@Url String fileUrl);

    @GET("uav/{id}")
    Call<UavDetails> getUavFarmerDetail(@Header("Authorization") String token,
                                        @Path("id") String feId);

    @GET("fe/{id}")
    Call<FarmerList> getFarmerList(@Header("Authorization") String token,
                                   @Path("id") String feId);

    @GET("fe/{id}")
    Call<FarmerList> getUnFarmerList(@Header("Authorization") String token,
                                     @Path("id") String feId);

    @GET("farmer/{id}")
    Call<Farmer> getFarmerDetail(@Header("Authorization") String token,
                                 @Path("id") String feId);

    @GET("farm/{id}")
    Call<Farm> getFarmDetail(@Header("Authorization") String token,
                             @Path("id") String feId);

    @POST("crop-problem")
    Call<CreatesVisit> createVisit(@Header("Authorization") String token,
                                   @Body CreatesVisit createVisit);

    @Multipart
    @POST("problem-img/{id}")
    Call<ResponseBody> uploadProblemImage(@Header("Authorization") String token,
                                          @Path("id") String id,
                                          @Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST("uav/submit-data-to-rse/{id}")
    Call<ResponseBody> submitToRse(@Header("Authorization") String token,
                                   @Path("id") String id,
                                   @Field("rseId") String rseId,
                                   @Field("problemId") String problemId);

    @FormUrlEncoded
    @PATCH("fe/verify-farm")
    Call<ResponseBody> verifyFarm(@Header("Authorization") String token,
                                  @Field("farmId") String farmId);

    @FormUrlEncoded
    @PATCH("fe/verify-farmer")
    Call<ResponseBody> verifyFarmer(@Header("Authorization") String token,
                                    @Field("farmerId") String farmerId);

    @FormUrlEncoded
    @POST("uav/otp")
    Call<ResponseBody> getOtpUav(@Header("Authorization") String token,
                                 @Field("email") String email);

    @FormUrlEncoded
    @POST("uav/change-password")
    Call<ResponseBody> changePassUav(@Header("Authorization") String token,
                                     @Field("email") String email,
                                     @Field("otp") String otp,
                                     @Field("Password") String password);

    @FormUrlEncoded
    @POST("fe/otp")
    Call<ResponseBody> getOtpFe(@Header("Authorization") String token,
                                @Field("email") String email);

    @FormUrlEncoded
    @POST("fe/change-password")
    Call<ResponseBody> changePassFe(@Header("Authorization") String token,
                                    @Field("email") String email,
                                    @Field("otp") String otp,
                                    @Field("Password") String password);


    @Multipart
    @PATCH("fe/change-avatar/{id}")
    Call<Responses.AvatarResponse> updateFeAvatar(@Header("Authorization") String token,
                                                  @Path("id") String id,
                                                  @Part MultipartBody.Part avatar);

    @Multipart
    @PATCH("uav/change-avatar/{id}")
    Call<Responses.AvatarResponse> updateUavAvatar(@Header("Authorization") String token,
                                                   @Path("id") String id,
                                                   @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @PATCH("uav/{id}")
    Call<ResponseBody> updateUavDetail(@Header("Authorization") String token,
                                       @Path("id") String feId,
                                       @Field("contact") String contact,
                                       @Field("alt_contact") String alt_contact,
                                       @Field("address") String address);

    @FormUrlEncoded
    @PATCH("fe/{id}")
    Call<ResponseBody> updateFeDetail(@Header("Authorization") String token,
                                      @Path("id") String feId,
                                      @Field("contact") String contact,
                                      @Field("alt_contact") String alt_contact,
                                      @Field("address") String address);


    @GET("visits/fe/{id}")
    Call<FeVisitsModel> getUpcomingVisit(@Header("Authorization") String token,
                                         @Path("id") String feId);


    @GET("crop-problem/{id}")
    Call<CropProblem> getProblemDetail(@Header("Authorization") String token,
                                       @Path("id") String feId);

}
