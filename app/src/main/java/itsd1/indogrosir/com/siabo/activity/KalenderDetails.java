package itsd1.indogrosir.com.siabo.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.adapter.AdapterTodo;
import itsd1.indogrosir.com.siabo.adapter.AdapterTugasPokok;
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
 * Created by Paulina on 1/31/2017.
 */
public class KalenderDetails extends AppCompatActivity implements LocationListener
        ,GoogleApiClient.ConnectionCallbacks {
    private ImageButton btnRute, btnCheck;
    private TextView txtToko, txtTglmulai, txtJam, txtAlamat, txtLongitude, txtLatitude, txtLate;
    private int id_plan = 0, id_user = 0, id_todo = 0, id_store = 0;
    private String token = "", latitude = "", longitude = "", namaToko = "", alamatToko = "", tglm, dateStamp, id_bukti;
    private Date timeStart = null, timeLimit = null, timeStamp = null;
    private Bundle extras;
    private RecyclerView recyclerView;
    private ArrayList<ToDo.ToDoDetail> todolist;
    private ArrayList<TugasPokok.TPDetail> tplist;
    private AdapterTodo adapterTodo;
    private AdapterTugasPokok adapterTP;
    private CircleOptions circleOptions;
    private Button btnTP, btnT;

    //map
    private GoogleMap mMap;
    private Circle mCircle;
    private Marker mMarker, mCurLocMarker;
    private Location mLastLocation, location;
    private String uLat = "", uLong = "";
    public LatLng posisiToko;
    public SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalenderdetail);

        extras = new Bundle();
        extras = getIntent().getExtras();
        token = extras.getString("token");
        id_plan = extras.getInt("id_plan");
        id_user = extras.getInt("id_user");

        btnRute = (ImageButton) findViewById(R.id.btnMap);
        btnCheck = (ImageButton) findViewById(R.id.btnCheck);

        btnT = (Button) findViewById(R.id.btnT);
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTugas();
            }
        });
        btnTP = (Button) findViewById(R.id.btnTP);
        btnTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTugasPokok();
            }
        });

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

        todolist = new ArrayList<>();

        btnCheck.setVisibility(View.INVISIBLE);
        txtLongitude.setVisibility(View.INVISIBLE);
        txtLatitude.setVisibility(View.INVISIBLE);
        btnRute.setVisibility(View.VISIBLE);
        btnRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("token", token);
                b.putString("latitude", latitude);
                b.putString("longitude", longitude);
                b.putString("namaToko", namaToko);
                b.putString("alamatToko", alamatToko);
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapK);
        //mapFragment.getMapAsync(this);

        initGoogleClient();
        setMap();
        getDetailPlan();
    }

    public void getDetailPlan() {
        progressDialog = new ProgressDialog(KalenderDetails.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        findViewById(R.id.loading_panel2).setVisibility(View.VISIBLE);
        final Typeface font_Robotomed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<PlanDet> call = apiService.getDetailPlan(id_plan, token);

        call.enqueue(new Callback<PlanDet>() {
            @Override
            public void onResponse(Call<PlanDet> call, Response<PlanDet> response) {
                progressDialog.dismiss();
                findViewById(R.id.loading_panel2).setVisibility(View.GONE);
                /***** ambil tgl hari ini*****/
                dateStamp = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());
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
                alamatToko = response.body().getPlanDetail().getAddress_s();

                tglm = date.format(response.body().getPlanDetail().getTgl_plan_mulai());
                try {
                    timeStart = time.parse(mulai);
                    timeLimit = time.parse(limit);
                    timeStamp = time.parse(now);
                } catch (ParseException e) {
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
                posisiToko = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                Log.wtf("lokasi dari api",latitude+longitude+"--"+posisiToko.latitude);
                namaToko = response.body().getPlanDetail().getStore_name();
                createMarker(posisiToko);
                txtLate.setTypeface(font_Robotomed);
//                showCheck();
            }

            @Override
            public void onFailure(Call<PlanDet> call, Throwable t) {
                Log.d("Log : ", t.toString());
            }
        });
    }

    public void showCheck()
    {
        Log.wtf("show check","is run");
//        Log.d("Log : ", String.valueOf(id_plan));
        if (tglm.equalsIgnoreCase(dateStamp) && (timeStamp.after(timeStart) && timeStamp.before(timeLimit))) {
            RestApi apiService = ApiClient.getClient().create(RestApi.class);
            Call<EksObject> call = apiService.getCekCheckin(id_plan, token);

            call.enqueue(new Callback<EksObject>() {
                 @Override
                 public void onResponse(Call<EksObject> call, Response<EksObject> response)
                 {
                     if(response.body().getId_plan()==0)
                     {
                         txtLate.setText("Absen");
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
                     else
                     {
                         txtLate.setText("Sudah Absen : \n"+response.body().getCreated_at().toString());
                         txtLate.setVisibility(View.VISIBLE);
                         btnCheck.setVisibility(View.GONE);
                     }
                 }

                 @Override
                 public void onFailure(Call<EksObject> call, Throwable t) {
                     Log.d("Error : ", t.toString());
                 }
             });
        }
        if (tglm.equalsIgnoreCase(dateStamp) && timeStamp.after(timeLimit)) {
            RestApi apiService = ApiClient.getClient().create(RestApi.class);
            Call<EksObject> call = apiService.getCekCheckin(id_plan, token);

            call.enqueue(new Callback<EksObject>() {
                @Override
                public void onResponse(Call<EksObject> call, Response<EksObject> response) {
                    if(response.body().getId_plan()!=0) {
                        txtLate.setText("Terlambat absen");
                        txtLate.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFailure(Call<EksObject> call, Throwable t){
                    Log.d("Error : ", t.toString());
                }
            });
        }
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void createMarker(LatLng mLatLng)
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions()
                .position(mLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.endpoint)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
        CircleOptions mCircleOptions= new CircleOptions()
                .center(mLatLng)
                .radius(50)
                .fillColor(R.color.blue_dark)
                .strokeColor(R.color.blue_dark);
        circleOptions = mCircleOptions;
        mMap.addCircle(mCircleOptions);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        float[] distance = new float[2];
        Location.distanceBetween(mLastLocation.getLatitude(), mLastLocation.getLongitude(), circleOptions.getCenter().latitude, circleOptions.getCenter().longitude, distance);
//        Log.wtf("after",distance[0]+"-"+circleOptions.getRadius());
        if(distance[0] <= circleOptions.getRadius())
        {
            showCheck();
        }

    }
    public void buttonCheckin(View v, int id_store) {
        final EksObject eksObject = new EksObject(id_store, id_user, id_plan);
        eksObject.setId_store(id_store);
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<EksObject> call = apiService.CheckIn(eksObject, id_user, token);

        call.enqueue(new Callback<EksObject>() {
            @Override
            public void onResponse(Call<EksObject> call, Response<EksObject> response)
            {
                if(response.isSuccessful()) // gak msk ke bagian ini, lsg ke ***
                {
                    findViewById(R.id.loading_panel2).setVisibility(View.GONE);
                    int ideks = eksObject.getId_eks();
//                Toast.makeText(getApplicationContext(), "Sudah check in"+ideks, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KalenderDetails.this);
                    alertDialogBuilder.setMessage("Sudah Absen ");
                    alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            txtLate.setVisibility(View.GONE);
                            btnCheck.setVisibility(View.GONE);
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                {
                    Log.d("Error : ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<EksObject> call, Throwable t) {    Log.d("Log : ", t.toString());    }
        });//***
    }

    void getTugas() {
//        btnTP.setVisibility(View.GONE);
//        btnT.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        btnT.setBackgroundColor(Color.LTGRAY);
        btnTP.setBackgroundColor(Color.WHITE);
//        findViewById(R.id.loading_panel2).setVisibility(View.VISIBLE);
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<ToDo> call = apiService.getTugas(id_user, id_plan, token);

        call.enqueue(new Callback<ToDo>() {
            @Override
            public void onResponse(Call<ToDo> call, Response<ToDo> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getTodo().size(); i++) {
                        todolist = response.body().getTodo();
                        id_todo = response.body().getTodo().get(i).getId();
                        id_bukti = response.body().getTodo().get(i).getId_bukti();
                        adapterTodo = new AdapterTodo(todolist);
                        adapterTodo.getIDTodo(id_todo);
                        adapterTodo.getIDUser(id_user);
                        adapterTodo.getToken(token);
                        adapterTodo.getIDPlan(id_plan);
                        adapterTodo.getIDBukti(id_bukti);
                        recyclerView.setAdapter(adapterTodo);
                    }
                }
                else
                {
                    //Snackbar.make(KalenderDetails.this,"something went wrong", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ToDo> call, Throwable t) {
                Log.d("Log : ", t.toString());
            }
        });
    }



    public void getTugasPokok(){
//        btnTP.setVisibility(View.GONE);
//        btnT.setVisibility(View.GONE);
        btnT.setBackgroundColor(Color.WHITE);
        btnTP.setBackgroundColor(Color.LTGRAY);
        recyclerView.setVisibility(View.VISIBLE);
//        findViewById(R.id.loading_panel2).setVisibility(View.VISIBLE);
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        Call<TugasPokok> call = apiService.getTugasPokok(id_user, id_plan, token);

        call.enqueue(new Callback<TugasPokok>() {
            @Override
            public void onResponse(Call<TugasPokok> call, Response<TugasPokok> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getTP().size(); i++) {
                        tplist = response.body().getTP();
                        id_todo = response.body().getTP().get(i).getId();
                        adapterTP = new AdapterTugasPokok(tplist);
                        adapterTP.getIDTodo(id_todo);
                        adapterTP.getIDUser(id_user);
                        adapterTP.getToken(token);
                        adapterTP.getIDPlan(id_plan);
                        adapterTP.getIDBukti(id_bukti);
                        recyclerView.setAdapter(adapterTP);
                    }
                }
                else
                {
                    //Snackbar.make(KalenderDetails.this,"something went wrong", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TugasPokok> call, Throwable t) {
                Log.d("Log : ", t.toString());
            }
        });
    }

    public void setMap() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);

            }
        });
    }

    private void initGoogleClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(getApplicationContext(), connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if(mCurLocMarker != null)
        {
            mCurLocMarker.remove();
        }
        uLat = Double.toString(location.getLatitude());
        uLong = Double.toString(location.getLongitude());
        float[] distance = new float[2];
        LatLng uLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions uMarker = new MarkerOptions();
        uMarker.position(uLatLng);
        uMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc24));
//        mMap.addCircle(new CircleOptions()
//                .center(uLatLng)
//                .radius(50)
//                .strokeColor(Color.BLUE)
//                .fillColor(Color.BLUE));
        mCurLocMarker = mMap.addMarker(uMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uLatLng,15));
        Log.wtf("before",distance[0]+"-");
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), circleOptions.getCenter().latitude,circleOptions.getCenter().longitude, distance);
        Log.wtf("after",distance[0]+"-"+circleOptions.getRadius());
        if(distance[0] <= circleOptions.getRadius())
        {
            showCheck();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mLastLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
