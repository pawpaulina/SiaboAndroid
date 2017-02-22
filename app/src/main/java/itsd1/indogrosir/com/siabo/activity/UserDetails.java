package itsd1.indogrosir.com.siabo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.models.Branch;
import itsd1.indogrosir.com.siabo.models.Role;
import itsd1.indogrosir.com.siabo.models.User;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 1/19/2017.
 */
public class UserDetails extends AppCompatActivity
{
    private TextView txt_name, txt_uname, txt_email, txt_phone, txt_cabang, txt_role;
    private String token="";
    private int id_user=0;
    private Bundle extras;
    private ImageButton btnKalender,btnMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        extras = new Bundle();
        extras = getIntent().getExtras();
        token = extras.getString("token");
        setContentView(R.layout.activity_userdetail);
        txt_name = (TextView) findViewById(R.id.txtName);
        txt_uname = (TextView) findViewById(R.id.txtUname);
        txt_email = (TextView) findViewById(R.id.txtEmail);
        txt_phone = (TextView) findViewById(R.id.txtPhone);
        txt_cabang = (TextView) findViewById(R.id.txtCabang);
        txt_role = (TextView) findViewById(R.id.txtRole);
        getUserDetail();
        btnKalender = (ImageButton) findViewById(R.id.btnKalender);
        btnKalender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle b = new Bundle();
                b.putString("token", token);
                b.putInt("id_user", id_user);
                Intent i = new Intent(getApplicationContext(), KalenderActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
        btnMap = (ImageButton) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle b = new Bundle();
                b.putString("token", token);
                b.putInt("id_user", id_user);
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    void getUserDetail()
    {
        final Typeface font_Sourcesp = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Black.otf");
        final Typeface font_Robotomed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<User> call = apiService.getUserDetail(token);
        call.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                try
                {
                    id_user = response.body().getUser().getId_user();
                    String name = response.body().getUser().getName();
                    String uname = response.body().getUser().getUsername();
                    String email = response.body().getUser().getEmail();
                    String phone = response.body().getUser().getPhone().toString();
                    String cabang = response.body().getUser().getCabang();
                    String role = response.body().getUser().getRole();
                    txt_name.setText(name);
                    txt_name.setTypeface(font_Robotomed);
                    txt_uname.setText(uname);
                    txt_uname.setTypeface(font_Robotomed);
                    txt_email.setText(email);
                    txt_email.setTypeface(font_Robotomed);
                    txt_phone.setText(phone);
                    txt_phone.setTypeface(font_Robotomed);
                    txt_cabang.setText(cabang);
                    txt_cabang.setTypeface(font_Robotomed);
                    txt_role.setText(role);
                    txt_role.setTypeface(font_Robotomed);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                Log.d("Log : ",t.toString());
            }
        });
    }
}
