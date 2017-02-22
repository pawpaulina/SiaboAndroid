package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 1/18/2017.
 */
public class Role
{
    @SerializedName("id_role")
    private int id_role;
    @SerializedName("role")
    private String role;

    public Role(int id_role, String role)
    {
        this.id_role = id_role;
        this.role = role;
    }
    public int getId_role()
    {
        return id_role;
    }

    public String getRole()
    {
        return role;
    }

    public void setId_role(int id_role)
    {
        this.id_role = id_role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}
