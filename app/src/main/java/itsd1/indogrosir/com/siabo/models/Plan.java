package itsd1.indogrosir.com.siabo.models;

import android.widget.ListView;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Paulina on 1/18/2017.
 */
public class Plan
{
    @SerializedName("plan")
    private List<PlanObject> plan = new ArrayList<PlanObject>();

    public List<PlanObject> getPlan()
    {
        return plan;
    }
}
