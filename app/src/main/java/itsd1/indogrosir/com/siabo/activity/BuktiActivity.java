package itsd1.indogrosir.com.siabo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import itsd1.indogrosir.com.siabo.R;

/**
 * Created by Paulina on 3/23/2017.
 */
public class BuktiActivity extends AppCompatActivity {
    private ImageButton camera, browse, save;
    private TextView keterangan;
    private Bundle extras;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = new Bundle();
        extras = getIntent().getExtras();

        token = extras.getString("token");
        setContentView(R.layout.activity_bukti);

        camera = (ImageButton) findViewById(R.id.btnCamera);
        browse = (ImageButton) findViewById(R.id.btnBrowse);
        save = (ImageButton) findViewById(R.id.btnSimpan);
        keterangan = (TextView) findViewById(R.id.txtketerangan);
    }
}
