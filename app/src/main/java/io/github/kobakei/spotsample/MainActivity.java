package io.github.kobakei.spotsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call init in Application or Activity's onCreate
        Spot.init(getApplicationContext());

        SampleModelRepository repository = Spot.getSampleModelRepository();
        SampleModel sampleModel = repository.getEntity();
    }
}
