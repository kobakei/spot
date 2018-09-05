package io.github.kobakei.spotsample;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowPreference;

import io.github.kobakei.spotsample.entity.SampleModel;
import io.github.kobakei.spotsample.entity.SampleModelSpotRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test of spot repository class.
 *
 * Created by keisukekobayashi on 2017/07/24.
 */
@RunWith(RobolectricTestRunner.class)
@Config(
        application = TestApplication.class,
        manifest = Config.NONE,
        shadows = ShadowPreference.class
)
public class SampleModelSpotRepositoryTest {

    @After
    public void tearDown() {
        SharedPreferences preferences = RuntimeEnvironment.application.getSharedPreferences("sample", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    @Test
    public void getEntity_isSuccess() {
        SharedPreferences preferences = RuntimeEnvironment.application.getSharedPreferences("sample", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("number_long", 1L);
        editor.putBoolean("is_enabled", false);
        editor.putString("text", "Hoge");
        editor.apply();

        SampleModel model = SampleModelSpotRepository.getEntity(RuntimeEnvironment.application);

        assertEquals(1L, model.numberLong);
        assertFalse(model.isEnabled);
        assertEquals("Hoge", model.text);
    }

    @Test
    public void putEntity_isSuccess() {
        SampleModel model = SampleModelSpotRepository.getEntity(RuntimeEnvironment.application);
        model.numberLong = 2L;
        model.text = "Hoge";
        SampleModelSpotRepository.putEntity(RuntimeEnvironment.application, model);

        SharedPreferences preferences = RuntimeEnvironment.application.getSharedPreferences("sample", Context.MODE_PRIVATE);
        assertEquals(2L, preferences.getLong("number_long", 0L));
        assertEquals("Hoge", preferences.getString("text", null));
    }

    @Test
    public void clear_isSuccess() {
        SharedPreferences preferences = RuntimeEnvironment.application.getSharedPreferences("sample", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("number_long", 1L);
        editor.putBoolean("is_enabled", false);
        editor.apply();

        SampleModelSpotRepository.clear(RuntimeEnvironment.application);

        // should return default values of SharedPreferences (not default values of entity)
        assertEquals(0L, preferences.getLong("number_long", 0L));
        assertTrue(preferences.getBoolean("is_enabled", true));
    }
}
