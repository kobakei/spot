package io.github.kobakei.spotsample;

import android.content.Context;

import io.github.kobakei.spot.PreferencesUtil;

/**
 * TODO 自動生成される予定
 * Created by keisukekobayashi on 16/03/31.
 */
public class SampleModelRepository {

    private Context context;

    public SampleModelRepository(Context context) {
        this.context = context;
    }

    private String getName() {
        return "sample";
    }

    public SampleModel getEntity() {
        SampleModel entity = new SampleModel();
        entity.numberInt = PreferencesUtil.getInt(context, getName(), "number_int", 100);
        entity.text = PreferencesUtil.getString(context, getName(), "text", "default value");
        return entity;
    }

    public void putEntity(SampleModel entity) {
        PreferencesUtil.putInt(context, getName(), "number_int", entity.numberInt);
        PreferencesUtil.putString(context, getName(), "number_text", entity.text);
    }
}
