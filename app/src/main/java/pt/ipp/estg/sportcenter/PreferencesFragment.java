package pt.ipp.estg.sportcenter;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by pmms8 on 23/11/2017.
 */

public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);

    }
}
