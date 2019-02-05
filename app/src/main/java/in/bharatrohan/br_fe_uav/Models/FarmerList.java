package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmerList {

    @SerializedName("farmers")
    private List<FarmersList> farmersLists;

    public FarmerList(List<FarmersList> farmersLists) {
        this.farmersLists = farmersLists;
    }

    public List<FarmersList> getFarmersLists() {
        return farmersLists;
    }

    public class FarmersList {
        @SerializedName("farmer_name")
        private String farmer_name;
        @SerializedName("email")
        private String email;
        @SerializedName("contact")
        private String contact;
        @SerializedName("_id")
        private String id;

        public FarmersList(String farmer_name, String email, String contact, String id) {
            this.farmer_name = farmer_name;
            this.email = email;
            this.contact = contact;
            this.id = id;
        }

        public String getFarmer_name() {
            return farmer_name;
        }

        public String getEmail() {
            return email;
        }

        public String getContact() {
            return contact;
        }

        public String getId() {
            return id;
        }
    }
}
