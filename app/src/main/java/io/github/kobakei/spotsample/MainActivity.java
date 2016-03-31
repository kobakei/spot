package io.github.kobakei.spotsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.editInt)
    EditText editInt;

    @Bind(R.id.editString)
    EditText editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Call init in Application or Activity's onCreate
        Spot.init(getApplicationContext());

        SampleModelRepository repository = Spot.getSampleModelRepository();
        SampleModel sampleModel = repository.getEntity();

        editInt.setText(sampleModel.numberInt);
        editString.setText(sampleModel.text);
    }

    @OnClick(R.id.button)
    void onButtonClicked() {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }
}
