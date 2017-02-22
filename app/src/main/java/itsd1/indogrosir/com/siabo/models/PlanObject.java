package itsd1.indogrosir.com.siabo.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paulina on 1/24/2017.
 */
public class PlanObject
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
    @SerializedName("store_code")
    private String store_code;
    @SerializedName("store_name")
    private String store_name;

    public PlanObject(int id, String tgl_plan_mulai, String tgl_plan_selesai, String jam_mulai, String jam_selesai, String store_code, String store_name)
    {
        this.id = id;

        this.tgl_plan_mulai = modifyDateLayout(tgl_plan_mulai, "DD-MM-YYYY");
        this.tgl_plan_selesai = modifyDateLayout(tgl_plan_selesai, "DD-MM-YYYY");
        this.jam_mulai = jam_mulai;//modifyDateLayout(jam_mulai,"HH:mm:ss");
        this.jam_selesai = jam_selesai;//modifyDateLayout(jam_selesai,"HH:mm:ss");
        this.store_code = store_code;
        this.store_name = store_name;
    }

    private Date modifyDateLayout(String inputDate, String format) {
        try
        {
            Date date = new SimpleDateFormat(format).parse(inputDate);
            return date;
        }
        catch (ParseException e)
        {
            Log.d("Log",e.toString());
            return null;
        }
    }

    public int getId()
    {
        return id;
    }

    public Date getTgl_plan_mulai()
    {
        return tgl_plan_mulai;
    }

    public Date getTgl_plan_selesai()
    {
        return tgl_plan_selesai;
    }

    public String getJam_mulai()
    {
        return jam_mulai;
    }

    public String getJam_selesai()
    {
        return jam_selesai;
    }

    public String getStore_code()
    {
        return store_code;
    }

    public String getStore_name()
    {
        return store_name;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public void setTgl_plan_mulai(Date tgl_plan_mulai)
    {
        this.tgl_plan_mulai = tgl_plan_mulai;
    }

    public void setTgl_plan_selesai(Date tgl_plan_selesai)
    {
        this.tgl_plan_selesai = tgl_plan_selesai;
    }

    public void setJam_mulai(String jam_mulai)
    {
        this.jam_mulai = jam_mulai;
    }

    public void setJam_selesai(String jam_selesai)
    {
        this.jam_selesai = jam_selesai;
    }

    public void setStore_code(String store_code)
    {
        this.store_code = store_code;
    }

    public void setStore_name(String store_name)
    {
        this.store_name = store_name;
    }
}
