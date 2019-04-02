package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SolutionComment {

    @SerializedName("problemId")
    private String probId;
    @SerializedName("fe_comment")
    private String comment;
    @SerializedName("solution")
    private List<CropProblem.Data.Solution.SolutionData> solutionData;

    public SolutionComment(String probId, String comment, List<CropProblem.Data.Solution.SolutionData> solutionData) {
        this.probId = probId;
        this.comment = comment;
        this.solutionData = solutionData;
    }

    public String getProbId() {
        return probId;
    }

    public String getComment() {
        return comment;
    }

    public List<CropProblem.Data.Solution.SolutionData> getSolutionData() {
        return solutionData;
    }
}
