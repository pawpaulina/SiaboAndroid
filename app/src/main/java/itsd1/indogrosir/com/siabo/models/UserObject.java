package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 1/24/2017.
 */
public class UserObject
{
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("role")
    String role;
    @SerializedName("branch_name")
    String branch_name;

    public UserObject(int id_user, String username, String name, String password, String email, String phone, String longitude, String latitude, String role, String branch_name)
    {
        this.id_user=id_user;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.role = role;
        this.branch_name = branch_name;
    }

    public int getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public String getRole()
    {
        return role;
    }

    public String getCabang() { return branch_name; }

    public void setId_user(int id_user)
    {
        this.id_user = id_user;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public void setCabang(String branch_name)
    {
        this.branch_name = branch_name;
    }
}
