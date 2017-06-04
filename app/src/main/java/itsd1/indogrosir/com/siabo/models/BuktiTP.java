package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/18/2017.
 */
public class BuktiTP {
    @SerializedName("id")
    private int id;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("id_plan")
    private int id_plan;
    @SerializedName("id_tp")
    private int id_tp;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("foto")
    private int foto;

    public BuktiTP(String keterangan, String gambar, int id_tp, int id_plan)
    {
        this.keterangan = keterangan;
        this.gambar = gambar;
        this.id_tp = id_tp;
        this.id_plan = id_plan;
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

    public int getId_tp() {
        return id_tp;
    }

    public int getId_plan() {
        return id_plan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getFoto() {
        return foto;
    }
}
