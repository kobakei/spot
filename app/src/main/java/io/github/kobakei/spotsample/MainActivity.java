package io.github.kobakei.spotsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.kobakei.spotsample.entity.SampleModel;
import io.github.kobakei.spotsample.entity.SampleModelSpotRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editInt)
    EditText editInt;

    @BindView(R.id.editLong)
    EditText editLong;

    @BindView(R.id.editFloat)
    EditText editFloat;

    @BindView(R.id.editBoolean)
    EditText editBoolean;

    @BindView(R.id.editString)
    EditText editString;

    @BindViews({ R.id.editStringSet1, R.id.editStringSet2, R.id.editStringSet3 })
    List<EditText> editStringSet;

    @BindView(R.id.date)
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        // Call init in Application or Activity's onCreate
        SampleModel sampleModel = SampleModelSpotRepository.getEntity(getApplicationContext());

        try {
            editInt.setText(Integer.toString(sampleModel.numberInt));
            editLong.setText(Long.toString(sampleModel.numberLong));
            editFloat.setText(Float.toString(sampleModel.numberFloat));
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
        editBoolean.setText(Boolean.toString(sampleModel.isEnabled));
        editString.setText(sampleModel.text);
        if (sampleModel.textSet != null) {
            String[] tokens = sampleModel.textSet.toArray(new String[3]);
            for (int i = 0; i < 3; i++) {
                editStringSet.get(i).setText(tokens[i]);
            }
        }

        timePicker.setIs24HourView(true);
    }

    @OnClick(R.id.button)
    void onButtonClicked() {
        try {
            SampleModel sampleModel = SampleModelSpotRepository.getEntity(getApplicationContext());
            sampleModel.numberInt = Integer.valueOf(editInt.getText().toString());
            sampleModel.numberLong = Long.valueOf(editLong.getText().toString());
            sampleModel.numberFloat = Float.valueOf(editFloat.getText().toString());
            sampleModel.isEnabled = Boolean.valueOf(editBoolean.getText().toString());
            sampleModel.text = editString.getText().toString();
            Set<String> set = new HashSet<>();
            set.add(editStringSet.get(0).getText().toString());
            set.add(editStringSet.get(1).getText().toString());
            set.add(editStringSet.get(2).getText().toString());
            sampleModel.textSet = set;
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            sampleModel.date = cal.getTime();
            SampleModelSpotRepository.putEntity(getApplicationContext(), sampleModel);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format error", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.button2)
    void onClearClicked() {
        SampleModelSpotRepository.clear(getApplicationContext());
        Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show();
    }
}
