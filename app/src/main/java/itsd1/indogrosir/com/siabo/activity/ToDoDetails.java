package itsd1.indogrosir.com.siabo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.models.Bukti;
import itsd1.indogrosir.com.siabo.models.BuktiTP;
import itsd1.indogrosir.com.siabo.models.EksObject;
import itsd1.indogrosir.com.siabo.models.PlanDet;
import itsd1.indogrosir.com.siabo.models.ToDo;
import itsd1.indogrosir.com.siabo.models.TugasPokok;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 2/13/2017.
 */
public class ToDoDetails extends AppCompatActivity
{
    private ImageButton btnBack, btncheckin;
    private TextView txtJudul, txtDesc, txtKet;
    private Bundle extras;
    private int id_plan = 0, id_user = 0;
    private int id_todo = 0, tugas = 0;
    private String token = "";
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tododetail);

        extras = new Bundle();
        extras = getIntent().getExtras();

        token = extras.getString("token");
        id_plan = extras.getInt("id_plan");
        id_user = extras.getInt("id_user");
        id_todo = extras.getInt("id_todo");
        tugas = extras.getInt("tugas");

        txtJudul = (TextView) findViewById(R.id.txtJudul);
        txtDesc = (TextView) findViewById(R.id.txtDeskripsi);
        txtKet = (TextView) findViewById(R.id.txtKet);
        cardView = (CardView) findViewById(R.id.card_view);
        findViewById(R.id.txtKet).setVisibility(View.GONE);

        txtJudul.setText(extras.getString("judul"));
        txtDesc.setText(extras.getString("desc"));

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), KalenderDetails.class);
                startActivity(i);
            }
        });
        btncheckin = (ImageButton) findViewById(R.id.btnCheckin);

        btnCheck();
        //getDetailTodo();
//        Toast.makeText(getApplicationContext(), "pos"+position, Toast.LENGTH_LONG).show();
    }

    private void btnCheck()
    {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<EksObject> call2 = apiService.getCekCheckin(id_plan, token);
        call2.enqueue(new Callback<EksObject>()
        {
            @Override
            public void onResponse(Call<EksObject> call2, Response<EksObject> response)
            {
                if(response.body().getId_plan()==0)
                {
                    btncheckin.setVisibility(View.GONE);
                    //blm absen
                }
                else
                {
                    //sudah absen
                    if(tugas==1)
                    {
                        RestApi apiService = ApiClient.getClient().create(RestApi.class);
                        Call<TugasPokok.TPDetail> call = apiService.cekSubmitTP(id_todo, token);
                        call.enqueue(new Callback<TugasPokok.TPDetail>() {
                            @Override
                            public void onResponse(Call<TugasPokok.TPDetail> call, final Response<TugasPokok.TPDetail> response) {
                                if(response.body().getId()==0)
                                {
                                    //blm kerjain tugas
                                    btncheckin.setVisibility(View.VISIBLE);
                                    btncheckin.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            Bundle b = new Bundle();
                                            b.putString("token", token);
                                            b.putInt("id_user", id_user);
                                            b.putInt("id_todo", id_todo);
                                            b.putInt("id_plan", id_plan);
                                            b.putInt("tugas", tugas);
                                            b.putInt("foto", response.body().getFoto());
                                            Intent i = new Intent(getApplicationContext(), BuktiActivity.class);
                                            i.putExtras(b);
                                            startActivity(i);
                                        }
                                    });
                                }
                                else
                                {
                                    //sudah kerjain tugas
                                    btncheckin.setVisibility(View.GONE);
                                    txtKet.setVisibility(View.VISIBLE);
                                    txtKet.setText("Sudah dikerjakan : "+response.body().getCreated_at());
                                }
                            }
                            @Override
                            public void onFailure(Call<TugasPokok.TPDetail> call, Throwable t) {    Log.d("Error : ",t.toString()); }
                        });
                    }
                    else
                    {
                        RestApi apiService = ApiClient.getClient().create(RestApi.class);
                        Call<ToDo.ToDoDetail> call = apiService.cekSubmit(id_todo, token);
                        call.enqueue(new Callback<ToDo.ToDoDetail>() {
                            @Override
                            public void onResponse(Call<ToDo.ToDoDetail> call, final Response<ToDo.ToDoDetail> response)
                            {
                                if(response.body().getId()==0)
                                {
                                    //blm kerjain tugas
                                    btncheckin.setVisibility(View.VISIBLE);
                                    btncheckin.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            Bundle b = new Bundle();
                                            b.putString("token", token);
                                            b.putInt("id_user", id_user);
                                            b.putInt("id_todo", id_todo);
                                            b.putInt("id_plan", id_plan);
                                            b.putInt("tugas", tugas);
                                            Intent i = new Intent(getApplicationContext(), BuktiActivity.class);
                                            i.putExtras(b);
                                            startActivity(i);
                                        }
                                    });
                                }
                                else
                                {
                                    //sudah kerjain tugas
                                    btncheckin.setVisibility(View.GONE);
                                    txtKet.setVisibility(View.VISIBLE);
                                    txtKet.setText("Sudah dikerjakan : "+response.body().getCreated_at());
                                }
                            }

                            @Override
                            public void onFailure(Call<ToDo.ToDoDetail> call, Throwable t) {     Log.d("Error : ",t.toString());       }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<EksObject> call, Throwable t) { Log.d("Error : ",t.toString()); }
        });
    }
    void getDetailTodo()
    {
        final Typeface font_Robotomed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<ToDo> call = apiService.getDetailTugas(id_user, id_plan, id_todo, token);

        call.enqueue(new Callback<ToDo>()
        {
            @Override
            public void onResponse(Call<ToDo> call, Response<ToDo> response)
            {
                int size = response.body().getTodo().size();
                txtJudul.setText(response.body().getTodo().get(0).getJudul_tugas());
                txtJudul.setTypeface(font_Robotomed);
                txtDesc.setText(response.body().getTodo().get(0).getDeskripsi_tugas());
                txtDesc.setTypeface(font_Robotomed);
                txtKet.setText(response.body().getTodo().get(0).getKeterangan());
                txtKet.setTypeface(font_Robotomed);
            }
            @Override
            public void onFailure(Call<ToDo> call, Throwable t)
            {
                Log.d("Error : ",t.toString());
            }
        });
    }
}
