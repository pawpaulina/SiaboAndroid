package itsd1.indogrosir.com.siabo.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Paulina on 1/27/2017.
 */
public class Route
{
    public itsd1.indogrosir.com.siabo.models.Distance distance;
    public itsd1.indogrosir.com.siabo.models.Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
