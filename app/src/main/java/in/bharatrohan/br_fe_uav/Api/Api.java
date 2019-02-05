package in.bharatrohan.br_fe_uav.Api;

import java.util.List;

import in.bharatrohan.br_fe_uav.Models.CreatesVisit;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.Models.FeDetails;
import in.bharatrohan.br_fe_uav.Models.LoginFE;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

    /*@POST("farmer/signup")
    Call<UserFarmer> createUser(
            @Body UserFarmer userFarmer);

    @POST("farmer/login")
    Call<LoginFarmer> loginUser(@Body LoginFarmer loginFarmer);

    @FormUrlEncoded
    @POST("farm")
    Call<FarmResponse> createFarm(@Header("token") String token,
                                  @Field("farm_name") String landName,
                                  @Field("location") String location,
                                  @Field("farm_area") String landArea,
                                  @Field("crop_id") String cropId,
                                  @Field("farmer_id") String farmerId);

    @Multipart
    @POST("farm/{farmId}")
    Call<ResponseBody> uploadKml(@Path("farmId") String farmid,
                                 @Part MultipartBody.Part kml_map,
                                 @Part MultipartBody.Part kml_image);

    @GET("states")
    Call<List<States>> stateList();

    @GET("state/{id}")
    Call<District> districtList(@Path("id") String id1);

    @GET("district/{id}")
    Call<Tehsil> tehsilList(@Path("id") String id2);

    @GET("tehsil/{id}")
    Call<Block> blockList(@Path("id") String id3);

    @GET("block/{id}")
    Call<Village> villageList(@Path("id") String id4);

    @GET("crops")
    Call<Crops> cropList();*/

    @POST("fe/login")
    Call<LoginFE> login(@Body LoginFE loginFE);


    @GET("fe/{id}")
    Call<FeDetails> getFeDetail(@Header("token") String token,
                                @Path("id") String feId);

    @GET("fe/{id}")
    Call<FarmerList> getFarmerList(@Header("token") String token,
                                   @Path("id") String feId);

    @GET("farmer/{id}")
    Call<Farmer> getFarmerDetail(@Header("token") String token,
                                 @Path("id") String feId);

    @GET("farm/{id}")
    Call<Farm> getFarmDetail(@Header("token") String token,
                             @Path("id") String feId);

    @POST("crop-problem")
    Call<CreatesVisit> createVisit(@Body CreatesVisit createVisit);

    @Multipart
    @POST("problem-img/{id}")
    Call<ResponseBody> uploadProblemImage(
            @Path("id") String id,
            /*@Part("description") RequestBody description,
            @Part("size") RequestBody size,*/
            @Part List<MultipartBody.Part> files);


}
