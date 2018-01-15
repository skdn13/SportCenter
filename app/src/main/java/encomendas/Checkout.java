package encomendas;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ipp.estg.sportcenter.R;


public class Checkout extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        ButterKnife.bind(this);
        Intent mIntent = getIntent();
        int numero = mIntent.getIntExtra("numero", 0);
        TextView numeroText = findViewById(R.id.textView21);
        numeroText.append(" " + numero);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle("SportCenter");
        notification.setContentText("Encomenda efetuada: " + numero + ", Obrigado!");
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.ic_stat_name);
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
        Handler h = new Handler();
        long delayInMilliseconds = 10000;
        h.postDelayed(new Runnable() {
            public void run() {
                manager.cancel(0);
            }
        }, delayInMilliseconds);
    }

    @OnClick(R.id.button3)
    public void inicio() {
        startActivity(new Intent(getApplicationContext(), pt.ipp.estg.sportcenter.EcraInicial.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), pt.ipp.estg.sportcenter.Preferences.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
