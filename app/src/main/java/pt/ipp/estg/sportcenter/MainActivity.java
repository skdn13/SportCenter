package pt.ipp.estg.sportcenter;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.prefs.Preferences;

public class MainActivity extends FragmentActivity implements MyDialog.NoticeDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDialog dialog = new MyDialog();
        dialog.show(getFragmentManager(), "dialog");
    }

    public void onDialogPositiveClick(DialogFragment dialog) {
    }

    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
