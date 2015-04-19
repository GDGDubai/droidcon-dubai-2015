package com.oxb.test.droidcon;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.support.v4.app.RemoteInput;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
    static final String EXTRA_REPLAY="VOCE_REPLAY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
        if(remoteInput!=null){
            String reply = remoteInput.getCharSequence(EXTRA_REPLAY).toString();
            Log.d("droidcon", reply);
        }

        Button button1= (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                Uri geoUri = Uri.parse("geo:24,54?q=dubai");
                mapIntent.setData(geoUri);
                PendingIntent mapPI =
                        PendingIntent.getActivity(MainActivity.this,0,mapIntent,0);

                RemoteInput ri = new RemoteInput.Builder(EXTRA_REPLAY)
                        .setLabel("Reply by voice")
                        .setChoices(getResources().getStringArray(R.array.reply_values))
                        .build();
                Intent replyIntent =
                        new Intent(MainActivity.this,MainActivity.class);
                PendingIntent replyPI =
                        PendingIntent.getActivity(MainActivity.this,
                                0,
                                replyIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action replyAction=
                        new NotificationCompat.Action.Builder(
                                R.mipmap.ic_launcher,"reply by voice",replyPI)
                                .addRemoteInput(ri).build();


                NotificationCompat.Action action=
                        new NotificationCompat.Action.Builder(
                                R.mipmap.ic_launcher,"open map wear",mapPI).build();

                NotificationCompat.Builder nb =
                        new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("Hello Droidcon")
                        .setContentText("GDG Dubai")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .addAction(R.mipmap.ic_launcher, "open map device", mapPI)
                        .extend(new NotificationCompat.WearableExtender()
                                .addAction(action)
                                .addAction(replyAction));

                NotificationManagerCompat nm =
                        NotificationManagerCompat.from(MainActivity.this);

                nm.notify(0,nb.build());
            }
        });
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
