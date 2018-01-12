package pt.ipp.estg.sportcenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pmms8 on 12/10/2017.
 */

public class ProductDetails extends AppCompatActivity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String text = getIntent().getStringExtra("text");
        mTextView = (TextView) findViewById(R.id.textView3);
        mTextView.setText(text);
    }
}
