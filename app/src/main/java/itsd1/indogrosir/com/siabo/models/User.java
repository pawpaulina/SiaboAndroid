package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 1/18/2017.
 */
public class User
{
    @SerializedName("user")
    private UserObject user;

    public UserObject getUser()
    {
        return user;
    }
}
