package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

public class AllFarmers {

    @SerializedName("farmer_name")
    private String name;
    @SerializedName("farmer_address")
    private String address;
    @SerializedName("contact")
    private String phone;

    public AllFarmers(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
