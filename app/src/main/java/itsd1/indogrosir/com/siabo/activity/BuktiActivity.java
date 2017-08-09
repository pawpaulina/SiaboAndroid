package itsd1.indogrosir.com.siabo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itsd1.indogrosir.com.siabo.Manifest;
import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.models.Bukti;
import itsd1.indogrosir.com.siabo.models.BuktiTP;
import itsd1.indogrosir.com.siabo.models.Loc;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina on 3/23/2017.
 */
public class BuktiActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton camera, browse, save;
    private Button reupload;
    private TextView keterangan, lblFoto, lnrFotoMsg, txtUpload;
    private LinearLayout lnrAttachment, lnrPhoto;
    private Bundle extras;
    private String token = "", encodedSingleImage;
    private JSONObject foto = new JSONObject();
    final HashMap<String, String> params = new HashMap<String, String>();
    private static final int REQUEST_GALLERY_CODE = 200;
    private Uri imageUri;
    private int click = 1, id_todo = 0, id_user = 0, id_plan = 0, idfoto, tugas;
    private ArrayList<String> imagesPathList;
    private String[] imagesPathArray, encodedImage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = new Bundle();
        extras = getIntent().getExtras();

        token = extras.getString("token");
        id_user = extras.getInt("id_user");
        id_todo = extras.getInt("id_todo");
        id_plan = extras.getInt("id_plan");
        tugas = extras.getInt("tugas");
        idfoto = extras.getInt("foto");
        setContentView(R.layout.activity_bukti);

        camera = (ImageButton) findViewById(R.id.btnCamera);
        browse = (ImageButton) findViewById(R.id.btnBrowse);
        save = (ImageButton) findViewById(R.id.btnSimpan);
        reupload = (Button) findViewById(R.id.btnReupload);

        keterangan = (TextView) findViewById(R.id.txtketerangan);
        lblFoto = (TextView) findViewById(R.id.lblFoto);
        lnrFotoMsg = (TextView) findViewById(R.id.lnrPhotoMsg); //buat notif kalo select fotonya error
        txtUpload = (TextView) findViewById(R.id.txtUpload);

        lnrPhoto = (LinearLayout) findViewById(R.id.lnrButton);
        lnrAttachment = (LinearLayout) findViewById(R.id.lnrAttachment);

        if(tugas==1)
        {
            browse.setVisibility(View.GONE);
            camera.setVisibility(View.GONE);
            txtUpload.setVisibility(View.GONE);
        }

        camera.setOnClickListener(this);
        browse.setOnClickListener(this);
        save.setOnClickListener(this);
        reupload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBrowse:
                Intent intent = new Intent(BuktiActivity.this, CustomPhotoGalleryActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnCamera:
                ContentValues values = new ContentValues();
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cam.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                cam.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(cam, 2);
                break;
            case R.id.btnSimpan:
                if(click == 1)
                {
                    click = 0;
                    progressDialog = new ProgressDialog(BuktiActivity.this);
                    progressDialog.setMessage("Loading..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    if(tugas==0)
                    {
                        Bukti bukti = new Bukti(keterangan.getText().toString(), String.valueOf(foto), id_todo, id_user);
                        RestApi apiService = ApiClient.getClient().create(RestApi.class);
                        Call<Bukti> call = apiService.submitTugas(bukti, id_todo, token);
                        call.enqueue(new Callback<Bukti>() {
                            @Override
                            public void onResponse(Call<Bukti> call, Response<Bukti> response)
                            {
                                //bukti disimpan
                                progressDialog.dismiss();
                                Log.d("Log ", response.toString());
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BuktiActivity.this);
                                alertDialogBuilder.setMessage("Data Berhasil Disimpan");
                                alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        Log.d("Log ", "button di klik");
                                        Intent intent = new Intent(BuktiActivity.this, KalenderActivity.class);
                                        startActivityForResult(intent, 1);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Bukti> call, Throwable t)
                            {
                                Log.d("Error : ", t.toString());
                            }
                        });
                    }
                    else
                    {
                        BuktiTP buktiTP = new BuktiTP(keterangan.getText().toString(), String.valueOf(foto), id_todo, id_plan);
                        RestApi apiService = ApiClient.getClient().create(RestApi.class);
                        Call<BuktiTP> call = apiService.submitTP(buktiTP, id_todo, token);
                        call.enqueue(new Callback<BuktiTP>() {
                            @Override
                            public void onResponse(Call<BuktiTP> call, Response<BuktiTP> response) {
                                //bukti disimpan
                                progressDialog.dismiss();
                                Log.d("Log ", response.toString());
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BuktiActivity.this);
                                alertDialogBuilder.setMessage("Data Berhasil Disimpan");
                                alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        Log.d("Log ", "button di klik");
                                        Intent intent = new Intent(BuktiActivity.this, KalenderActivity.class);
                                        startActivityForResult(intent, 1);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<BuktiTP> call, Throwable t) {
                                Log.d("Error : ", t.toString());
                            }
                        });
                    }

                }

                break;
            case R.id.btnReupload:
                lnrPhoto.setVisibility(View.VISIBLE);
                lblFoto.setVisibility(View.GONE);
                reupload.setVisibility(View.GONE);
                camera.setVisibility(View.VISIBLE);
                browse.setVisibility(View.VISIBLE);
                foto = new JSONObject();
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e)
        {
            return contentUri.getPath();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                foto = new JSONObject();
                imagesPathList = new ArrayList<String>();
                imagesPathArray = data.getStringExtra("data").split("\\|");
                for(int i = 0 ; i < imagesPathArray.length ; i++)
                {
                    imagesPathList.add(imagesPathArray[i]);
                }
                encodedImage = new String[imagesPathArray.length];
                for(int i = 0 ; i < imagesPathArray.length ; i++ )
                {
                    Bitmap imgBitmap = BitmapFactory.decodeFile(imagesPathArray[i]);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

                    encodedImage[i] = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    try
                    {
                        foto.put("foto"+i, encodedImage[i]);
//                        Log.d("Log : ", encodedImage[i]);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                Log.d("Log:", foto.toString());

                if(imagesPathArray != null)
                {
                    if(imagesPathArray.length != 0)
                    {
                        camera.setVisibility(View.GONE);
                        browse.setVisibility(View.GONE);
                        lnrPhoto.setVisibility(View.GONE);
                        lblFoto.setVisibility(View.VISIBLE);
                        reupload.setVisibility(View.VISIBLE);
                        lblFoto.setText(imagesPathArray.length + " gambar dipilih");
                        lnrFotoMsg.setVisibility(View.GONE);
                    }
                }
                else
                {
                    lnrFotoMsg.setVisibility(View.VISIBLE);
                    lnrFotoMsg.setText("Tidak ada gambar yang dipilih");
                }
            }
        }
        else
            if(requestCode == 2)
            {
                foto = new JSONObject();

                Bitmap imgBitmap = BitmapFactory.decodeFile(getRealPathFromURI(imageUri));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

                encodedSingleImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                try
                {
                    foto.put("foto0", encodedSingleImage);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                lnrPhoto.setVisibility(View.GONE);
                lblFoto.setVisibility(View.VISIBLE);
                reupload.setVisibility(View.VISIBLE);
                lblFoto.setText("1 gambar dipilih");
                lnrFotoMsg.setVisibility(View.GONE);
            }
    }
}
