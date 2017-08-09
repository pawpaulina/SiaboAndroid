package itsd1.indogrosir.com.siabo.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.agenda.AgendaView;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.models.Plan;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 1/23/2017.
 */
public class KalenderActivity extends AppCompatActivity implements CalendarPickerController
{
    public int id_user = 0, id_plan=0;
    private Bundle extras;
    private String token = "";
    private AgendaCalendarView calendarView;
    private AgendaView mAgendaView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        extras = new Bundle();
        extras = getIntent().getExtras();
        token = extras.getString("token");
        id_user = extras.getInt("id_user");

        setContentView(R.layout.activity_kalender);

        List<CalendarEvent> eventList = new ArrayList<>();
        getKalender(eventList);


        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        calendarView = (AgendaCalendarView) findViewById(R.id.agenda_calendar_view);
        calendarView.init(eventList, minDate, maxDate, Locale.getDefault(), KalenderActivity.this);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    private void mockList(List<CalendarEvent> eventList, Response<Plan> response)
    {
        for (int i = 0; i < response.body().getPlan().size(); i++)
        {
            if(i%2==0)
            {
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();

                Date mulai = response.body().getPlan().get(i).getTgl_plan_mulai();
                Date selesai = response.body().getPlan().get(i).getTgl_plan_selesai();

                String judul = response.body().getPlan().get(i).getStore_name();
                String jam = response.body().getPlan().get(i).getJam_mulai() + " - " + response.body().getPlan().get(i).getJam_selesai();
                String isi = "Kode Toko : " + response.body().getPlan().get(i).getStore_code() + " Jam mulai : " + response.body().getPlan().get(i).getJam_mulai() + " Jam selesai : " + response.body().getPlan().get(i).getJam_selesai() ;

                startDate.setTime(mulai);
                endDate.setTime(selesai);

                if(startDate!=endDate)
                {
                    endDate.add(Calendar.DATE, 1);
                   // endTime.add(Calendar.HOUR,1);
                }
                BaseCalendarEvent event = new BaseCalendarEvent(jam, isi, judul, ContextCompat.getColor(KalenderActivity.this.getApplicationContext(), R.color.yellow), startDate, endDate, false);
                event.setId(response.body().getPlan().get(i).getId());
                eventList.add(event);
            }
            else
            {
                Calendar startTime1 = Calendar.getInstance();
                Calendar endTime1 = Calendar.getInstance();
                Date mulai1 = response.body().getPlan().get(i).getTgl_plan_mulai();
                Date selesai1 = response.body().getPlan().get(i).getTgl_plan_selesai();
                startTime1.setTime(mulai1);
                endTime1.setTime(selesai1);
                String judul = response.body().getPlan().get(i).getStore_name();
                String jam = response.body().getPlan().get(i).getJam_mulai() + " - " + response.body().getPlan().get(i).getJam_selesai();
                String isi = "Kode Toko : " + response.body().getPlan().get(i).getStore_code() + " Jam mulai : " + response.body().getPlan().get(i).getJam_mulai() + " Jam selesai : " + response.body().getPlan().get(i).getJam_selesai() ;
                if(startTime1!=endTime1)
                {
                    endTime1.add(Calendar.DATE, 1);
                }
                BaseCalendarEvent event2 = new BaseCalendarEvent(jam, isi, judul, ContextCompat.getColor(KalenderActivity.this.getApplicationContext(), R.color.blue_dark), startTime1, endTime1, false);
                eventList.add(event2);
                event2.setId(response.body().getPlan().get(i).getId());
            }
        }
    }

    void getKalender(final List<CalendarEvent> eventList)
    {
        progressDialog = new ProgressDialog(KalenderActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<Plan> call = apiService.getKalender(id_user, token);

        call.enqueue(new Callback<Plan>()
        {
            @Override
            public void onResponse(Call<Plan> call, Response<Plan> response)
            {
                progressDialog.dismiss();
                try
                {
                    mockList(eventList, response);

                    Calendar minDate = Calendar.getInstance();
                    Calendar maxDate = Calendar.getInstance();

                    minDate.add(Calendar.MONTH, -2);
                    minDate.set(Calendar.DAY_OF_MONTH, 1);
                    maxDate.add(Calendar.YEAR, 1);

                    calendarView.init(eventList, minDate, maxDate, Locale.getDefault(), KalenderActivity.this);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Plan> call, Throwable t)
            {
                Log.d("Log", t.toString());
            }
        });
    }

    @Override
    public void onDaySelected(DayItem dayItem) {    }

    @Override
    public void onEventSelected(CalendarEvent event)
    {
        mAgendaView = (AgendaView) findViewById(R.id.agenda_view);
        if(event.getId() != 0)
        {
//            Log.wtf("stt : ", ""+CalendarManager.getInstance().getToday());
//            mAgendaView.getAgendaListView().scrollToCurrentDate(CalendarManager.getInstance().getToday());
            Bundle b = new Bundle();
            b.putString("token", token);
            b.putInt("id_plan", (int) event.getId());
            b.putInt("id_user", id_user);
            Intent i = new Intent(getApplicationContext(), KalenderDetails.class);
            i.putExtras(b);
            startActivity(i);
        }
    }

    @Override
    public void onScrollToDate(Calendar calendar) {    }
}
