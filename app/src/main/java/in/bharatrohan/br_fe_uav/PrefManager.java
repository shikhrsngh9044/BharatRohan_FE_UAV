package in.bharatrohan.br_fe_uav;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    Context context;

    public PrefManager(Context context) {
        this.context = context;
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

    public void saveFeAvatar(String avatar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FeAvatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Avatar", avatar);
        editor.apply();
    }

    public String getFeAvatar(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FeAvatar", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Avatar", "");
    }

    public String getAccStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Acc_Status", "");
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

    public void saveFarmDeatils(String landName, String cropName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LandName", landName);
        editor.putString("CropName", cropName);
        editor.apply();
    }

    public String getLandName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("LandName", "");
    }

    public String getCropName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("CropName", "");
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
}
