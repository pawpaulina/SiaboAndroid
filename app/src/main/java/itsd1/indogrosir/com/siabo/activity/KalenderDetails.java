package itsd1.indogrosir.com.siabo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.adapter.AdapterTodo;
import itsd1.indogrosir.com.siabo.adapter.RecyclerItemClickListener;
import itsd1.indogrosir.com.siabo.models.EksObject;
import itsd1.indogrosir.com.siabo.models.Eksekusi;
import itsd1.indogrosir.com.siabo.models.Loc;
import itsd1.indogrosir.com.siabo.models.Plan;
import itsd1.indogrosir.com.siabo.models.PlanDet;
import itsd1.indogrosir.com.siabo.models.ToDo;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 1/31/2017.
 */
public class KalenderDetails extends AppCompatActivity
{
    private ImageButton btnRute, btnCheck;
    private TextView txtToko, txtTglmulai, txtJam, txtAlamat, txtLongitude, txtLatitude, txtLate;
    private int id_plan = 0, id_user = 0, id_todo = 0, id_store = 0;
    private String token = "", latitude = "", longitude = "";
    private Bundle extras;

    //Recyclerview
    private RecyclerView recyclerView;
    private ArrayList<ToDo.ToDoDetail> todolist;
    private AdapterTodo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalenderdetail);

        extras = new Bundle();
        extras = getIntent().getExtras();
        token = extras.getString("token");
        id_plan = extras.getInt("id_plan");
        id_user = extras.getInt("id_user");

        btnRute = (ImageButton) findViewById(R.id.btnMap);
        btnCheck = (ImageButton) findViewById(R.id.btnCheck);

        txtToko = (TextView) findViewById(R.id.txtToko);
        txtToko.setText("");
        txtTglmulai = (TextView) findViewById(R.id.Tglmulai);
        txtJam = (TextView) findViewById(R.id.Jam);
        txtAlamat = (TextView) findViewById(R.id.txtAlamat);
        txtLongitude = (TextView) findViewById(R.id.txtlongitude);
        txtLatitude = (TextView) findViewById(R.id.txtlatitude);
        txtLate = (TextView) findViewById(R.id.txtLate);

        recyclerView = (RecyclerView) findViewById(R.id.TDLView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override public void onItemClick(View view, int position)
                    {
//
//                        Bundle b = new Bundle();
//                        b.putInt("id_user", id_user);
//                        b.putString("token", token);
//                        b.putInt("id_plan", id_plan);
//                        b.putInt("id_todo", id_todo);
//
//                        recyclerView.getLayoutManager().scrollToPosition(position);
//                        b.putInt("position", position);
//                        Intent i = new Intent(getApplicationContext(), ToDoDetails.class);
//                        i.putExtras(b);
//                        Toast.makeText(getApplicationContext(), "pos" + position, Toast.LENGTH_LONG).show();
//                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {}
                })
        );

        todolist = new ArrayList<>();

        btnCheck.setVisibility(View.INVISIBLE);
        txtLongitude.setVisibility(View.INVISIBLE);
        txtLatitude.setVisibility(View.INVISIBLE);
        btnRute.setVisibility(View.VISIBLE);
        btnRute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle b = new Bundle();
                b.putString("token", token);
                b.putString("latitude", latitude);
                b.putString("longitude", longitude);
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
        getDetailPlan();
        getTugas();
    }

    void getDetailPlan()
    {
        final Typeface font_Robotomed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<PlanDet> call = apiService.getDetailPlan(id_plan,token);

        call.enqueue(new Callback<PlanDet>()
        {
            @Override
            public void onResponse(Call<PlanDet> call, Response<PlanDet> response)
            {
                Date timeStart = null, timeLimit = null, timeStamp = null;
                /***** ambil tgl hari ini*****/
                String dateStamp = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());
                /*****ambil jam hari ini*****/
                String now = getCurrentTimeStamp();
                /*****parsing tanggal dan jam*****/
                SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                /*****ambil value jam mulai dan jam selesai dari jadwal*****/
                String mulai = response.body().getPlanDetail().getJam_mulai();
                String limit = response.body().getPlanDetail().getJam_selesai();

                txtToko.setText(response.body().getPlanDetail().getStore_name() + "-" + response.body().getPlanDetail().getStore_code());
                txtToko.setTypeface(font_Robotomed);
                id_store = response.body().getPlanDetail().getId_store();
                txtAlamat.setText(response.body().getPlanDetail().getAddress_s());
                txtAlamat.setTypeface(font_Robotomed);

                String tglm = date.format(response.body().getPlanDetail().getTgl_plan_mulai());
                try
                {
                    timeStart = time.parse(mulai);
                    timeLimit = time.parse(limit);
                    timeStamp = time.parse(now);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                txtTglmulai.setText(tglm);
                txtTglmulai.setTypeface(font_Robotomed);
                txtJam.setText(response.body().getPlanDetail().getJam_mulai() + "-" + response.body().getPlanDetail().getJam_selesai());
                txtJam.setTypeface(font_Robotomed);
                txtLatitude.setText(response.body().getPlanDetail().getLatitude_s());
                txtLongitude.setText(response.body().getPlanDetail().getLongitude_s());
                latitude = response.body().getPlanDetail().getLatitude_s();
                longitude = response.body().getPlanDetail().getLongitude_s();
                if(tglm.equalsIgnoreCase(dateStamp) && (timeStamp.after(timeStart) && timeStamp.before(timeLimit)))
                {
                    txtLate.setText("Absen");
                    txtLate.setTypeface(font_Robotomed);
                    txtLate.setTextColor(Color.GREEN);
                    btnCheck.setVisibility(View.VISIBLE);
                    btnCheck.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            buttonCheckin(v, id_store);
                        }
                    });
                }
                if (tglm.equalsIgnoreCase(dateStamp) && timeStamp.after(timeLimit))
                {
                    txtLate.setText("Terlambat absen");
                    txtLate.setTypeface(font_Robotomed);
                    txtLate.setTextColor(Color.RED);
                }
            }
            @Override
            public void onFailure(Call<PlanDet> call, Throwable t)
            {
                Log.d("Log : ",t.toString());
            }
        });
    }

    public static String getCurrentTimeStamp()
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void buttonCheckin(View v, int id_store)
    {
        EksObject eksObject = new EksObject(id_store, id_user, id_todo);
        eksObject.setId_store(id_store);
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<EksObject> call = apiService.CheckIn(eksObject, token);

        call.enqueue(new Callback<EksObject>()
        {
            @Override
            public void onResponse(Call<EksObject> call, Response<EksObject> response)
            {
                //tabel eks di update
                Toast.makeText(getApplicationContext(), "Sudah check in", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<EksObject> call, Throwable t)
            {
                Log.d("Log : ",t.toString());
            }
        });
    }

    void getTugas()
    {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<ToDo> call = apiService.getTugas(id_user, id_plan, token);

        call.enqueue(new Callback<ToDo>()
        {
            @Override
            public void onResponse(Call<ToDo> call, Response<ToDo> response)
            {
                if(response.isSuccessful())
                {

//                    todolist = response.body().getTodo();
////                    id_todo = response.body().getTodo().get(i).getId();
//                    adapter = new AdapterTodo(todolist);
//                    recyclerView.setAdapter(adapter);
                    for (int i = 0; i < response.body().getTodo().size(); i++)
                    {

                        todolist = response.body().getTodo();
                        id_todo = response.body().getTodo().get(i).getId();
                        adapter = new AdapterTodo(todolist);
                        recyclerView.setAdapter(adapter);
                    }
                }
                else
                {
                    //Snackbar.make(KalenderDetails.this,"something went wrong", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ToDo> call, Throwable t)
            {
                Log.d("Log : ",t.toString());
            }
        });
    }
}
