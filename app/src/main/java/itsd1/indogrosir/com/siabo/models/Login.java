package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 1/20/2017.
 */
public class Login
{
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;
    @SerializedName("error")
    private String error;

    public Login(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public void setToken(String token)
    {
        this.token=token;
    }

    public String getToken()
    {
        return token;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }
}
