package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UavDetails {

    @SerializedName("email")
    private String email;
    @SerializedName("user_type")
    private String userType;
    @SerializedName("uav_name")
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
    @SerializedName("crop_problems")
    private List<CropProblem> cropProblem;

    public UavDetails(String email, String userType, String name, String contact, String alt_contact, String avatar, Boolean accStatus, String address, JobLocation jobLocation, List<CropProblem> cropProblem) {
        this.email = email;
        this.userType = userType;
        this.name = name;
        this.contact = contact;
        this.alt_contact = alt_contact;
        this.avatar = avatar;
        this.accStatus = accStatus;
        this.address = address;
        this.jobLocation = jobLocation;
        this.cropProblem = cropProblem;
    }

    public String getUserType() {
        return userType;
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

    public List<CropProblem> getCropProblem() {
        return cropProblem;
    }

    public class JobLocation {
        @SerializedName("state")
        private State state;
        @SerializedName("district")
        private District district;
        @SerializedName("tehsil")
        private Tehsil tehsil;
        @SerializedName("block")
        private Block block;
        @SerializedName("village")
        private List<Village> village;

        public JobLocation(State state, District district, Tehsil tehsil, Block block, List<Village> village) {
            this.state = state;
            this.district = district;
            this.tehsil = tehsil;
            this.block = block;
            this.village = village;
        }

        public State getState() {
            return state;
        }

        public District getDistrict() {
            return district;
        }

        public Tehsil getTehsil() {
            return tehsil;
        }

        public Block getBlock() {
            return block;
        }

        public List<Village> getVillage() {
            return village;
        }

        public class State {
            @SerializedName("state_name")
            private String state_name;

            public State(String state_name) {
                this.state_name = state_name;
            }

            public String getState_name() {
                return state_name;
            }
        }

        public class District {
            @SerializedName("district_name")
            private String district_name;

            public District(String district_name) {
                this.district_name = district_name;
            }

            public String getDistrict_name() {
                return district_name;
            }
        }

        public class Tehsil {
            @SerializedName("tehsil_name")
            private String tehsil_name;

            public Tehsil(String tehsil_name) {
                this.tehsil_name = tehsil_name;
            }

            public String getTehsil_name() {
                return tehsil_name;
            }
        }

        public class Block {
            @SerializedName("block_name")
            private String block_name;

            public Block(String block_name) {
                this.block_name = block_name;
            }

            public String getBlock_name() {
                return block_name;
            }
        }

        public class Village {
            @SerializedName("village_name")
            private String village_name;

            public Village(String village_name) {
                this.village_name = village_name;
            }

            public String getVillage_name() {
                return village_name;
            }
        }
    }

    public class CropProblem {

        @SerializedName("_id")
        private String problemId;
        @SerializedName("farmer_id")
        private FarmerDetail farmer;
        @SerializedName("farm_id")
        private FarmDetail farm;

        public CropProblem(String problemId, FarmerDetail farmer, FarmDetail farm) {
            this.problemId = problemId;
            this.farmer = farmer;
            this.farm = farm;
        }
        public String getProblemId() {
            return problemId;
        }

        public FarmerDetail getFarmer() {
            return farmer;
        }

        public FarmDetail getFarm() {
            return farm;
        }

        public class FarmerDetail {
            @SerializedName("email")
            private String email;
            @SerializedName("farmer_name")
            private String farmerName;
            @SerializedName("contact")
            private String contact;
            @SerializedName("full_address")
            private String full_address;
            @SerializedName("avatar")
            private String avatar;

            public FarmerDetail(String email, String farmerName, String contact, String full_address, String avatar) {
                this.email = email;
                this.farmerName = farmerName;
                this.contact = contact;
                this.full_address = full_address;
                this.avatar = avatar;
            }

            public String getEmail() {
                return email;
            }

            public String getFarmerName() {
                return farmerName;
            }

            public String getContact() {
                return contact;
            }

            public String getFull_address() {
                return full_address;
            }

            public String getAvatar() {
                return avatar;
            }
        }

        public class FarmDetail {
            @SerializedName("farm_name")
            private String farmName;
            @SerializedName("location")
            private String location;
            @SerializedName("farm_area")
            private String farmArea;
            @SerializedName("kml_file")
            private String kml;
            @SerializedName("map_image")
            private String image;

            public FarmDetail(String farmName, String location, String farmArea, String kml, String image) {
                this.farmName = farmName;
                this.location = location;
                this.farmArea = farmArea;
                this.kml = kml;
                this.image = image;
            }


            public String getFarmName() {
                return farmName;
            }

            public String getLocation() {
                return location;
            }

            public String getFarmArea() {
                return farmArea;
            }

            public String getKml() {
                return kml;
            }

            public String getImage() {
                return image;
            }
        }
    }
}
