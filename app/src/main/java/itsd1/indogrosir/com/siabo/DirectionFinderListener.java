package itsd1.indogrosir.com.siabo;


import java.util.List;

import itsd1.indogrosir.com.siabo.models.Route;

/**
 * Created by Paulina on 1/27/2017.
 */
public interface DirectionFinderListener
{
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
