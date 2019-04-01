package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CropProblem {
    @SerializedName("data")
    private Data data;

    public CropProblem(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        @SerializedName("solution")
        private Solution solution;


        public Data(Solution solution) {
            this.solution = solution;
        }

        public Solution getSolution() {
            return solution;
        }

        public class Solution {

            @SerializedName("_solution")
            private List<SolutionData> solutionDataList;
            @SerializedName("solution_img")
            private String solutionImage;


            public Solution(List<SolutionData> solutionDataList, String solutionImage) {
                this.solutionDataList = solutionDataList;
                this.solutionImage = solutionImage;
            }

            public List<SolutionData> getSolutionDataList() {
                return solutionDataList;
            }

            public String getSolutionImage() {
                return solutionImage;
            }

            public class SolutionData {
                @SerializedName("solution_color")
                private String solColor;
                @SerializedName("crop_solution")
                private String solText;

                public SolutionData(String solColor, String solText) {
                    this.solColor = solColor;
                    this.solText = solText;
                }

                public String getSolColor() {
                    return solColor;
                }

                public String getSolText() {
                    return solText;
                }
            }
        }

    }
}
