package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 1/30/2017.
 */
public class Loc
{
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public Loc(String latitude, String longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
