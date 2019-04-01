package in.bharatrohan.br_fe_uav;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }


    public void saveBaseUrl(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BaseUrl", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }

    public String getBaseUrl() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BaseUrl", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }


    public void saveLoginDetails(String email, String password, String feId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("userId", feId);
        editor.apply();
    }

    public String getPass() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }


    public String getUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }

    public void saveUserDetails(String name, String contact, String email, String alt_contact, Boolean acc_status, String address, String job_state, String job_district, String job_tehsil, String job_block, String job_village) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", name);
        editor.putString("Contact", contact);
        editor.putString("Email", email);
        editor.putString("Alt_Contact", alt_contact);
        editor.putBoolean("Acc_Status", true);
        editor.putString("Address", address);
        editor.putString("Job_State", job_state);
        editor.putString("Job_District", job_district);
        editor.putString("Job_Tehsil", job_tehsil);
        editor.putString("Job_Block", job_block);
        editor.putString("Job_Village", job_village);
        editor.apply();
    }

    public String getState() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Job_State", "");
    }

    public String getDistrict() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Job_District", "");
    }

    public String getTehsil() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Job_Tehsil", "");
    }

    public String getBlock() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Job_Block", "");
    }

    public String getVillage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Job_Village", "");
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public String getName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Name", "");
    }

    public String getContact() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Contact", "");
    }

    public String getAltContact() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Alt_Contact", "");
    }

    public void saveFarmerAvatar(String avatar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerAvatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Avatar", avatar);
        editor.apply();
    }

    public String getFarmerAvatar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerAvatar", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Avatar", "");
    }

    public Boolean getAccStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Acc_Status", true);
    }

    public String getAddress() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Address", "");
    }


    public void saveToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tokens", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tokens", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token", "");
    }


    public void saveFarmerId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }

    public String getFarmerId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }

    public void saveFarmCount(int farm_count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("FarmCount", farm_count);
        editor.apply();
    }


    public int getFarmerFarmCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetail", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("FarmCount", 0);
    }


    public void saveFarmId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }

    public String getFarmId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }

    public void saveCropId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CropId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }

    public String getCropId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CropId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }

    public void saveUavId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }

    public String getUavId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }

    public void saveProblemId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProblemId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }

    public String getProblemId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProblemId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }

    public void saveFarmerDetails(String problemId, String email, String name, String contact, String address, String avatar, String farm_name, String farm_location, String farm_area, String kml_url, String fe_name, String fe_contact, String fe_email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ProblemId", problemId);
        editor.putString("Email", email);
        editor.putString("Name", name);
        editor.putString("Contact", contact);
        editor.putString("Address", address);
        editor.putString("Avatar", avatar);
        editor.putString("FarmName", farm_name);
        editor.putString("FarmLocation", farm_location);
        editor.putString("FarmArea", farm_area);
        editor.putString("KmlUrl", kml_url);
        editor.putString("FeName", fe_name);
        editor.putString("FeContact", fe_contact);
        editor.putString("FeEmail", fe_email);
        editor.apply();
    }

    public String getFProblemId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ProblemId", "");
    }

    public String getFEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public String getFName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Name", "");
    }

    public String getFContact() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Contact", "");
    }

    public String getFAddress() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Address", "");
    }

    public String getFAvatar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Avatar", "");
    }

    public String getFFarmName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FarmName", "");
    }

    public String getFFarmLocation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FarmLocation", "");
    }

    public String getFFarmArea() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FarmArea", "");
    }

    public String getFKmlUrl() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("KmlUrl", "");
    }

    public String getFFeName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FeName", "");
    }

    public String getFFeContact() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FeContact", "");
    }

    public String getFFeEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UavFarmerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FeEmail", "");
    }


    public void saveFarmNo(int farm_count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmNo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("No", farm_count);
        editor.apply();
    }


    public int getFarmerFarmNo() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmNo", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("No", 0);
    }


    public void saveUserType(String type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserType", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Type", type);
        editor.apply();
    }

    public String getUserType() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserType", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Type", "");
    }


    public void saveFarmStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }

    public Boolean getFarmStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }

    public void saveFarmerStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }

    public Boolean getFarmerStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }

    public void saveFarmImage(String image) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmImage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Image", image);
        editor.apply();
    }

    public String getFarmImage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmImage", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Image", "");
    }

    public void saveAvatar(String avatar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserAvatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Avatar", avatar);
        editor.apply();
    }

    public String getAvatar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserAvatar", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Avatar", "");
    }


    public void saveIsVisit(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("VisitBool", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }

    public Boolean getIsVisit() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("VisitBool", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }
}
