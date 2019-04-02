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
            @SerializedName("fe_comment")
            private String fe_comment;


            public Solution(List<SolutionData> solutionDataList, String solutionImage, String fe_comment) {
                this.solutionDataList = solutionDataList;
                this.solutionImage = solutionImage;
                this.fe_comment = fe_comment;
            }

            public String getFe_comment() {
                return fe_comment;
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
                @SerializedName("_status")
                private Boolean _status;

                public SolutionData(String solColor, String solText, Boolean _status) {
                    this.solColor = solColor;
                    this.solText = solText;
                    this._status = _status;
                }

                public Boolean get_status() {
                    return _status;
                }

                public String getSolColor() {
                    return solColor;
                }

                public String getSolText() {
                    return solText;
                }


                public void setSolColor(String solColor) {
                    this.solColor = solColor;
                }

                public void setSolText(String solText) {
                    this.solText = solText;
                }

                public void set_status(Boolean _status) {
                    this._status = _status;
                }
            }
        }

    }
}
