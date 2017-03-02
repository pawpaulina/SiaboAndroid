package itsd1.indogrosir.com.siabo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.models.Login;
import itsd1.indogrosir.com.siabo.models.User;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY="";
    private static String token="";
    private Button btnLogin;
    private TextView txttitle, txtsubtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final Typeface font_Sourcesp = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Black.otf");
        final Typeface font_Robotomed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txttitle = (TextView) findViewById(R.id.title);
        txttitle.setTypeface(font_Robotomed);
        txtsubtitle = (TextView) findViewById(R.id.subtitle);
        txtsubtitle.setTypeface(font_Sourcesp);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonLogin(v);
            }
        });
    }

    public void buttonLogin(View v)
    {
        findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
        EditText email=(EditText) findViewById(R.id.txtemail);
        EditText pass=(EditText) findViewById(R.id.txtpassword);
        String e_mail = email.getText().toString();
        String password = pass.getText().toString();

        Login login = new Login(e_mail,password);
        RestApi ra = ApiClient.getClient().create(RestApi.class);
        Call<Login> call = ra.getLogin(login);
        call.enqueue(new Callback<Login>()
        {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
            {
                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                token = (response.body().getToken());
                Bundle b = new Bundle();
                b.putString("token", token);
                Intent i = new Intent(MainActivity.this, UserDetails.class);
                i.putExtras(b);
                startActivity(i);
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t)
            {
                if(isOnline())
                {
                    Log.d("Log",t.toString());
                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Email atau Password Anda salah");
                    alertDialogBuilder.setNegativeButton("OK",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
//                Toast.makeText(getApplicationContext(), "Email atau Password Anda salah", Toast.LENGTH_LONG).show();
                }
                else
                {
                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Koneksi internet tidak tersedia");
                    alertDialogBuilder.setNegativeButton("OK",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
