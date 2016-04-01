package io.github.kobakei.spotsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.kobakei.spotsample.entity.SampleModel;
import io.github.kobakei.spotsample.entity.SampleModelSpotRepository;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.editInt)
    EditText editInt;

    @Bind(R.id.editLong)
    EditText editLong;

    @Bind(R.id.editFloat)
    EditText editFloat;

    @Bind(R.id.editBoolean)
    EditText editBoolean;

    @Bind(R.id.editString)
    EditText editString;

    @Bind({R.id.editStringSet1, R.id.editStringSet2, R.id.editStringSet3})
    List<EditText> editStringSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Call init in Application or Activity's onCreate
        SampleModel sampleModel = SampleModelSpotRepository.getEntity(getApplicationContext());

        try {
            editInt.setText(Integer.toString(sampleModel.numberInt));
            editLong.setText(Long.toString(sampleModel.numberLong));
            editFloat.setText(Float.toString(sampleModel.numberFloat));
        } catch (NumberFormatException e) {
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
            SampleModelSpotRepository.putEntity(getApplicationContext(), sampleModel);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format error", Toast.LENGTH_SHORT).show();
        }
    }
}
