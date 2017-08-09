package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Paulina on 2/8/2017.
 */
public class ToDo {
    @SerializedName("todoDetail") //deklarasinya harus sesuai dengan json
    @Expose
    private ArrayList<ToDoDetail> todo = new ArrayList<>();

    public ArrayList<ToDoDetail> getTodo()
    {
        return todo;
    }

    public class ToDoDetail
    {
        @SerializedName("id")
        private int id;
        @SerializedName("id_plan")
        private int id_plan;
        @SerializedName("judul_tugas")
        private String judul_tugas;
        @SerializedName("deskripsi_tugas")
        private String deskripsi_tugas;
        @SerializedName("keterangan")
        private String keterangan;
        @SerializedName("id_bukti")
        private int id_bukti;
        @SerializedName("created_at")
        private String created_at;

        public ToDoDetail(int id, String judul_tugas, String deskripsi_tugas, String keterangan)
        {
            this.id = id;
            this.judul_tugas = judul_tugas;
            this.deskripsi_tugas = deskripsi_tugas;
            this.keterangan = keterangan;
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getDeskripsi_tugas()
        {
            return deskripsi_tugas;
        }

        public void setDeskripsi_tugas(String deskripsi_tugas)
        {
            this.deskripsi_tugas = deskripsi_tugas;
        }

        public String getJudul_tugas()
        {
            return judul_tugas;
        }

        public void setJudul_tugas(String judul_tugas) {
            this.judul_tugas = judul_tugas;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public int getId_plan() {
            return id_plan;
        }

        public void setId_plan(int idplan) {
            this.id_plan = id_plan;
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
