package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Farm {

    @SerializedName("data")
    private Data data;

    public Farm(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        @SerializedName("isVerified")
        private Boolean isVerified;
        @SerializedName("farm_name")
        private String farm_name;
        @SerializedName("map_image")
        private String map_image;
        @SerializedName("crop_id")
        private Crop crop;

        public Data(Boolean isVerified, String farm_name, Crop crop) {
            this.isVerified = isVerified;
            this.farm_name = farm_name;
            this.crop = crop;
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
