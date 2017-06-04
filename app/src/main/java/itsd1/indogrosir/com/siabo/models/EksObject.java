package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Paulina on 2/14/2017.
 */
public class EksObject {
    @SerializedName("id_eks")
    private int id_eks;
    @SerializedName("id_store")
    private int id_store;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("id_plan")
    private int id_plan;
    @SerializedName("created_at")
    private String created_at;

    public EksObject(int store_code, int id_user, int id_plan)
    {
        this.id_store = id_store;
        this.id_user = id_user;
        this.id_plan = id_plan;
    }

    public void setId_store(int id_store)
    {
        this.id_store = id_store;
    }

    public int getId_store()
    {
        return id_store;
    }

    public int getId_user()
    {
        return id_user;
    }

    public int getId_plan()
    {
        return id_plan;
    }

    public int getId_eks() {
        return id_eks;
    }

    public String getCreated_at() {
        return created_at;
    }
}
