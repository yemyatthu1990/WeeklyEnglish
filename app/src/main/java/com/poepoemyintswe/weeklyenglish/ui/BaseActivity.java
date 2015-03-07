package com.poepoemyintswe.weeklyenglish.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import com.poepoemyintswe.weeklyenglish.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by poepoe on 7/3/15.
 */
public abstract class BaseActivity extends ActionBarActivity {
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(getHomeUpEnabled());
      getSupportActionBar().setTitle(getCustomTitle());
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setNavigationBarTintEnabled(true);
      tintManager.setTintColor(getResources().getColor(R.color.primary_dark));
    }
  }

  protected abstract int getLayoutResource();

  protected abstract boolean getHomeUpEnabled();

  protected abstract String getCustomTitle();

  public void startRefreshing(SwipeRefreshLayout swipeRefreshLayout) {
    if (!swipeRefreshLayout.isRefreshing()) {
      swipeRefreshLayout.setRefreshing(true);
    }
  }

  public void stopRefreshing(SwipeRefreshLayout swipeRefreshLayout) {
    if (swipeRefreshLayout.isRefreshing()) {
      swipeRefreshLayout.setRefreshing(false);
    }
  }

  public void swipeRefreshLayoutInit(SwipeRefreshLayout mSwipeRefreshLayout) {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
        R.color.swipe_refresh_color2, R.color.swipe_refresh_color3, R.color.swipe_refresh_color4);
    TypedValue typed_value = new TypedValue();
    this.getTheme()
        .resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
    mSwipeRefreshLayout.setProgressViewOffset(false, 0,
        getResources().getDimensionPixelSize(typed_value.resourceId));
  }
}