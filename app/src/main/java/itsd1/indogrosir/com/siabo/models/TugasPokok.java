package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Paulina on 5/11/2017.
 */
public class TugasPokok {
    @SerializedName("tugasPokok") //deklarasinya harus sesuai dengan json
    @Expose
    private ArrayList<TPDetail> tugaspokok = new ArrayList<>();

    public ArrayList<TPDetail> getTP()
    {
        return tugaspokok;
    }

    public class TPDetail {
        @SerializedName("id")
        private int id;
        @SerializedName("judul")
        private String judul;
        @SerializedName("deskripsi")
        private String deskripsi;
        @SerializedName("foto")
        private String foto;
        @SerializedName("exp_date")
        private String exp_date;

        public TPDetail(int id, String judul, String deskripsi) {
            this.id = id;
            this.judul = judul;
            this.deskripsi = deskripsi;
        }

        public int getId() {
            return id;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public String getJudul() {
            return judul;
        }

        public String getFoto() {
            return foto;
        }

        public String getExp_date() {
            return exp_date;
        }
    }
}
