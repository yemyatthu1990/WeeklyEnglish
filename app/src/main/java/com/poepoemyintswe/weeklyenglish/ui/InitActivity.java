package com.poepoemyintswe.weeklyenglish.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.poepoemyintswe.weeklyenglish.R;
import com.poepoemyintswe.weeklyenglish.api.LessonService;
import com.poepoemyintswe.weeklyenglish.model.Data;
import com.poepoemyintswe.weeklyenglish.utils.CustomRestAdapter;
import com.poepoemyintswe.weeklyenglish.utils.NetworkConnectivityCheck;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.poepoemyintswe.weeklyenglish.utils.LogUtils.LOGE;
import static com.poepoemyintswe.weeklyenglish.utils.LogUtils.makeLogTag;

public class InitActivity extends BaseActivity {

  private final String TAG = makeLogTag(InjectView.class);

  @InjectView(R.id.progress_bar) ProgressWheel progressWheel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.inject(this);

    String[] colors = getPrimaryColor();
    progressWheel.setBarColor(Color.parseColor(colors[0]));

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setNavigationBarTintEnabled(true);
      tintManager.setTintColor(Color.parseColor(colors[1]));
    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(Color.parseColor(colors[1]));
    }

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        Intent mainIntent = new Intent(InitActivity.this, MainActivity.class);
        InitActivity.this.startActivity(mainIntent);
        InitActivity.this.finish();
      }
    }, 2000);
    //downloadData();
  }

  private void downloadData() {
    if (NetworkConnectivityCheck.getInstance(this).isConnected()) {
      LessonService lessonService =
          CustomRestAdapter.getInstance(this).normalRestAdapter().create(LessonService.class);
      lessonService.getLessons(new Callback<Data>() {
        @Override public void success(Data data, Response response) {
          LOGE(TAG, "Response == " + response.getStatus());
        }

        @Override public void failure(RetrofitError error) {

        }
      });
    }
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_init;
  }

  @Override protected boolean getHomeUpEnabled() {
    return false;
  }

  @Override protected String getCustomTitle() {
    return null;
  }

  @Override protected boolean needToolbar() {
    return false;
  }
}