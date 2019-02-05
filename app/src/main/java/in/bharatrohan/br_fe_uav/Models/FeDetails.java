package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeDetails {

    @SerializedName("email")
    private String email;
    @SerializedName("fe_name")
    private String name;
    @SerializedName("contact")
    private String contact;
    @SerializedName("alt_contact")
    private String alt_contact;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("account_status")
    private Boolean accStatus;
    @SerializedName("address")
    private String address;
    @SerializedName("job_location")
    private JobLocation jobLocation;

    public FeDetails(String email, String name, String contact, String alt_contact, String avatar, Boolean accStatus, String address, JobLocation jobLocation) {
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.alt_contact = alt_contact;
        this.avatar = avatar;
        this.accStatus = accStatus;
        this.address = address;
        this.jobLocation = jobLocation;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAlt_contact() {
        return alt_contact;
    }

    public String getAvatar() {
        return avatar;
    }

    public Boolean getAccStatus() {
        return accStatus;
    }

    public String getAddress() {
        return address;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public class JobLocation{
        @SerializedName("state")
        private String state;
        @SerializedName("district")
        private String disrict;
        @SerializedName("tehsil")
        private String tehsil;
        @SerializedName("block")
        private String block;
        @SerializedName("village")
        private List<String> village;

        public JobLocation(String state, String disrict, String tehsil, String block, List<String> village) {
            this.state = state;
            this.disrict = disrict;
            this.tehsil = tehsil;
            this.block = block;
            this.village = village;
        }

        public String getState() {
            return state;
        }

        public String getDisrict() {
            return disrict;
        }

        public String getTehsil() {
            return tehsil;
        }

        public String getBlock() {
            return block;
        }

        public List<String> getVillage() {
            return village;
        }
    }
}
