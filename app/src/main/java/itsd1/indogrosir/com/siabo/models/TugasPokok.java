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
        private int foto;
        @SerializedName("exp_date")
        private String exp_date;
        @SerializedName("id_bukti")
        private int id_bukti;
        @SerializedName("created_at")
        private String created_at;

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

        public int getFoto() {
            return foto;
        }

        public String getExp_date() {
            return exp_date;
        }

        public int getId_bukti() {
            return id_bukti;
        }

        public void setId_bukti(int id_bukti) {
            this.id_bukti = id_bukti;
        }

        public String getCreated_at() {
            return created_at;
        }
    }
}
