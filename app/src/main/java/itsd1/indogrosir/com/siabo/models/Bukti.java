package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 3/29/2017.
 */
public class Bukti {
    @SerializedName("id")
    private int id;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("id_todo")
    private int id_todo;
    @SerializedName("created_at")
    private String created_at;

    public Bukti(String keterangan, String gambar, int id_todo, int id_user)
    {
        this.keterangan = keterangan;
        this.gambar = gambar;
        this.id_todo = id_todo;
        this.id_user = id_user;
    }

    public int getId()
    {
        return id;
    }

    public String getKeterangan()
    {
        return keterangan;
    }

    public String getGambar()
    {
        return gambar;
    }

    public int getId_todo() {
        return id_todo;
    }

    public int getId_user() {
        return id_user;
    }

    public String getCreated_at() {
        return created_at;
    }
}
