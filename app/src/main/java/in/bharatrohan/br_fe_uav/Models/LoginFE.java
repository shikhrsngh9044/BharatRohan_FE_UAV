package in.bharatrohan.br_fe_uav.Models;

import com.google.gson.annotations.SerializedName;

public class LoginFE {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String pass;
    @SerializedName("fe_id")
    private String id;
    @SerializedName("token")
    private String token;
    @SerializedName("message")
    private String message;

    public LoginFE(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
