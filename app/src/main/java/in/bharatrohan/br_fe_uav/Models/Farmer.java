package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Farmer {
    @SerializedName("_id")
    private String farmer_id;
    @SerializedName("farmer_name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("contact")
    private String contact;
    @SerializedName("full_address")
    private String full_address;
    @SerializedName("account_status")
    private Boolean acc_status;
    @SerializedName("isVerified")
    private Boolean isVerified;
    @SerializedName("dob")
    private String dob;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("uav_id")
    private String uav_id;
    @SerializedName("farms")
    private ArrayList<FarmerFarm> farms;
    @SerializedName("address")
    private Address address;

    public Farmer(String farmer_id, String name, String email, String contact, String full_address, Boolean acc_status, Boolean isVerified, String dob, String avatar, String uav_id, ArrayList<FarmerFarm> farms, Address address) {
        this.farmer_id = farmer_id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.full_address = full_address;
        this.acc_status = acc_status;
        this.isVerified = isVerified;
        this.dob = dob;
        this.avatar = avatar;
        this.uav_id = uav_id;
        this.farms = farms;
        this.address = address;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUav_id() {
        return uav_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getFull_address() {
        return full_address;
    }

    public Boolean getAcc_status() {
        return acc_status;
    }

    public String getDob() {
        return dob;
    }

    public ArrayList<FarmerFarm> getFarms() {
        return farms;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public Address getAddress() {
        return address;
    }

    public class Address {
        @SerializedName("state")
        private State state;
        @SerializedName("district")
        private District district;
        @SerializedName("tehsil")
        private Tehsil tehsil;
        @SerializedName("block")
        private Block block;
        @SerializedName("village")
        private Village village;

        public Address(State state, District district, Tehsil tehsil, Block block, Village village) {
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

        public Village getVillage() {
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

    public class FarmerFarm {
        @SerializedName("_id")
        private String farmid;
        @SerializedName("isVerified")
        private Boolean isVerified;
        @SerializedName("problems_id")
        private List<String> problemId;
        @SerializedName("farm_name")
        private String farm_name;
        @SerializedName("map_image")
        private String map_image;
        @SerializedName("crop_id")
        private Crop crop;

        public FarmerFarm(String farmid, Boolean isVerified, List<String> problemId, String farm_name, String map_image, Crop crop) {
            this.farmid = farmid;
            this.isVerified = isVerified;
            this.problemId = problemId;
            this.farm_name = farm_name;
            this.map_image = map_image;
            this.crop = crop;
        }


        public String getFarmid() {
            return farmid;
        }

        public List<String> getProblemId() {
            return problemId;
        }

        public String getMap_image() {
            return map_image;
        }

        public Boolean getVerified() {
            return isVerified;
        }

        public String getFarm_name() {
            return farm_name;
        }

        public Crop getCrop() {
            return crop;
        }

        public class Crop {
            @SerializedName("crop_name")
            private String crop_name;

            @SerializedName("_id")
            private String crop_id;

            public Crop(String crop_name, String crop_id) {
                this.crop_name = crop_name;
                this.crop_id = crop_id;
            }

            public String getCrop_name() {
                return crop_name;
            }

            public String getCrop_id() {
                return crop_id;
            }
        }
    }
}
