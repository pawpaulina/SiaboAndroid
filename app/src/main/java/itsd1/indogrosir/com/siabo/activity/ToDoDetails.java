package itsd1.indogrosir.com.siabo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import itsd1.indogrosir.com.siabo.models.PlanDet;
import itsd1.indogrosir.com.siabo.models.ToDo;
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
    private int id_todo = 0, position = 0;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tododetail);

        extras = new Bundle();
        extras = getIntent().getExtras();

        token = extras.getString("token");
//        id_plan = extras.getInt("id_plan");
//        id_user = extras.getInt("id_user");
//        id_todo = extras.getInt("id_todo");
//        position = extras.getInt("position");

        txtJudul = (TextView) findViewById(R.id.txtJudul);
        txtDesc = (TextView) findViewById(R.id.txtDeskripsi);
        txtKet = (TextView) findViewById(R.id.txtKet);
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
        btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("token", token);
                Intent i = new Intent(getApplicationContext(), BuktiActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        //getDetailTodo();
//        Toast.makeText(getApplicationContext(), "pos"+position, Toast.LENGTH_LONG).show();
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
                Log.d("Log : ",t.toString());
            }
        });
    }
}
