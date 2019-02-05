package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreatesVisit {

    @SerializedName("farmer_id")
    private String farmerId;
    @SerializedName("farm_id")
    private String farmId;
    @SerializedName("crop_id")
    private String cropId;
    @SerializedName("fe_id")
    private String feId;
    @SerializedName("questions")
    private List<Questions> questionsList;

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public static class Questions {

        @SerializedName("question")
        private String question;
        @SerializedName("answer")
        private String answer;

        public Questions(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

    }

    public CreatesVisit(String farmerId, String farmId, String cropId, String feId, List<Questions> questionsList) {
        this.farmerId = farmerId;
        this.farmId = farmId;
        this.cropId = cropId;
        this.feId = feId;
        this.questionsList = questionsList;
    }

    public CreatesVisit(String message, Data data) {
        this.message = message;
        this.data = data;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public String getFarmId() {
        return farmId;
    }

    public String getCropId() {
        return cropId;
    }

    public String getFeId() {
        return feId;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public class Data{

        @SerializedName("problem")
        private Problem problem;

        public Data(Problem problem) {
            this.problem = problem;
        }

        public Problem getProblem() {
            return problem;
        }

        public class Problem{
            @SerializedName("_id")
            private String id;

            public Problem(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }
        }
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

}
