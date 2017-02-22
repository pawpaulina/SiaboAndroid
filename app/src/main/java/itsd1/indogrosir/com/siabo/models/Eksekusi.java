package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Paulina on 1/18/2017.
 */
public class Eksekusi
{
    @SerializedName("eksekusi")
    private List<EksObject> eks = new ArrayList<EksObject>();

    public List<EksObject> getEks()
    {
        return eks;
    }
}
