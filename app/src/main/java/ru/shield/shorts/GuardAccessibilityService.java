package ru.shield.shorts;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.content.Intent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;

public class GuardAccessibilityService extends AccessibilityService {
  static boolean watched(String p){return "com.google.android.youtube".equals(p)||"com.zhiliaoapp.musically".equals(p)||"com.instagram.android".equals(p);}
  public void onAccessibilityEvent(AccessibilityEvent e){String p=String.valueOf(e.getPackageName());if(!watched(p))return;long now=System.currentTimeMillis(),used=0;UsageStatsManager u=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);for(UsageStats x:u.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,now-86400000L,now)){if(watched(x.getPackageName()))used+=x.getTotalTimeInForeground();}int limit=getSharedPreferences("guard",0).getInt("limit",30);if(used>limit*60000L){performGlobalAction(GLOBAL_ACTION_HOME);startActivity(new Intent(this,LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP));}}
  public void onInterrupt(){}
}
