package io.github.kobakei.spotsample;

import android.content.Context;

/**
 * TODO 自動生成される予定
 * Created by keisukekobayashi on 16/03/31.
 */
public class Spot {

    private static SampleModelRepository sampleModelRepository;

    public static void init(Context context) {
        sampleModelRepository = new SampleModelRepository(context);
    }

    public static SampleModelRepository getSampleModelRepository() {
        if (sampleModelRepository == null) {
            throw new IllegalStateException("Call this method after init().");
        }
        return sampleModelRepository;
    }
}
