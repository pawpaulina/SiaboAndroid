package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
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
    @SerializedName("id_todo")
    private int id_todo;

    public EksObject(int store_code, int id_user, int id_todo)
    {
        this.id_store = id_store;
        this.id_user = id_user;
        this.id_todo = id_todo;
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

    public int getId_todo()
    {
        return id_todo;
    }

}
