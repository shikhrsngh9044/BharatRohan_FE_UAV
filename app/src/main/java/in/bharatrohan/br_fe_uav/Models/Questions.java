package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

public class Questions {
    private String question;
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
