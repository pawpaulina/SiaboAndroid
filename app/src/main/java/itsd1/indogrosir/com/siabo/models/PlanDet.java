package itsd1.indogrosir.com.siabo.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paulina on 1/31/2017.
 */
public class PlanDet
{
    @SerializedName("planDetail")
    private PlanDetail planDetail;

    public PlanDetail getPlanDetail()
    {
        return planDetail;
    }

    public class PlanDetail
    {
        @SerializedName("id")
        private int id;
        @SerializedName("tgl_plan_mulai")
        private Date tgl_plan_mulai;
        @SerializedName("tgl_plan_selesai")
        private Date tgl_plan_selesai;
        @SerializedName("jam_mulai")
        private String jam_mulai;
        @SerializedName("jam_selesai")
        private String jam_selesai;
        @SerializedName("id_store")
        private int id_store;
        @SerializedName("store_code")
        private String store_code;
        @SerializedName("store_name")
        private String store_name;
        @SerializedName("latitude_u")
        private String latitude_u;
        @SerializedName("longitude_u")
        private String longitude_u;
        @SerializedName("latitude_s")
        private String latitude_s;
        @SerializedName("longitude_s")
        private String longitude_s;
        @SerializedName("address_s")
        private String address_s;

        public PlanDetail(int id, String tgl_plan_mulai, String tgl_plan_selesai, String jam_mulai, String jam_selesai, String store_code, String store_name, String latitude_u, String longitude_u, String latitude_s, String longitude_s, String address_s)
        {
            this.id = id;
            this.tgl_plan_mulai = modifyDateLayout(tgl_plan_mulai, "DD-MM-YYYY");
            this.tgl_plan_selesai = modifyDateLayout(tgl_plan_selesai, "DD-MM-YYYY");
            this.jam_mulai = jam_mulai;
            this.jam_selesai = jam_selesai;
            this.store_code = store_code;
            this.store_name = store_name;
            this.latitude_u = latitude_u;
            this.longitude_u = longitude_u;
            this.latitude_s = latitude_s;
            this.longitude_s = longitude_s;
            this.address_s = address_s;
        }

        private Date modifyDateLayout(String inputDate, String format)
        {
            try
            {
                Date date = new SimpleDateFormat(format).parse(inputDate);
                return date;
            }
            catch (ParseException e)
            {
                Log.d("Log", e.toString());
                return null;
            }
        }

        public int getId()
        {
            return id;
        }

        public int getId_store()
        {
            return id_store;
        }

        public String getStore_code()
        {
            return store_code;
        }

        public String getStore_name()
        {
            return store_name;
        }

        public Date getTgl_plan_mulai()
        {
            return tgl_plan_mulai;
        }

        public Date getTgl_plan_selesai()
        {
            return tgl_plan_selesai;
        }

        public String getLongitude_u()
        {
            return longitude_u;
        }

        public String getLatitude_u()
        {
            return latitude_u;
        }

        public String getJam_mulai()
        {
            return jam_mulai;
        }

        public String getJam_selesai()
        {
            return jam_selesai;
        }

        public String getLatitude_s()
        {
            return latitude_s;
        }

        public String getLongitude_s()
        {
            return longitude_s;
        }

        public String getAddress_s()
        {
            return address_s;
        }
    }
}
