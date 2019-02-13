package in.bharatrohan.br_fe_uav.Api;

import java.util.List;

import in.bharatrohan.br_fe_uav.Models.CreatesVisit;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.Models.FeDetails;
import in.bharatrohan.br_fe_uav.Models.LoginFE;
import in.bharatrohan.br_fe_uav.Models.LoginUAV;
import in.bharatrohan.br_fe_uav.Models.UavDetails;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("fe/{id}")
    Call<FarmerList> getFarmerList(@Header("Authorization") String token,
                                   @Path("id") String feId);

    @GET("fe/{id}")
    Call<FarmerList> getUnFarmerList(@Header("Authorization") String token,
                                     @Path("id") String feId,
                                     @Query("isVerified") Boolean isVerified);

    @GET("farmer/{id}")
    Call<Farmer> getFarmerDetail(@Header("Authorization") String token,
                                 @Path("id") String feId);

    @GET("farm/{id}")
    Call<Farm> getFarmDetail(@Header("Authorization") String token,
                             @Path("id") String feId);

    @POST("crop-problem")
    Call<CreatesVisit> createVisit(@Body CreatesVisit createVisit);

    @Multipart
    @POST("problem-img/{id}")
    Call<ResponseBody> uploadProblemImage(
            @Path("id") String id,
            @Part List<MultipartBody.Part> files);

    //get()

//uav login- g
}
