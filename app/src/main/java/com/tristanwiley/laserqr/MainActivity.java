package com.tristanwiley.laserqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    private static final int RC_BARCODE_CAPTURE = 1;
    private static final String TAG = "BarcodeMain";
    Button joinGame, createGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinGame = (Button) findViewById(R.id.joinGame);
        createGame = (Button) findViewById(R.id.createGame);

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch barcode activity.
                Intent intent = new Intent(MainActivity.this, QRScanner.class);

                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(QRScanner.BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Log.d(TAG, "An Error Occured: " + CommonStatusCodes.getStatusCodeString(resultCode));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
