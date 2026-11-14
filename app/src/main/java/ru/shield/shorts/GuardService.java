package ru.shield.shorts;
import android.app.*;import android.app.usage.*;import android.content.*;import android.os.*;import java.util.*;
public class GuardService extends Service{
 static final int ID=7; Handler h=new Handler(Looper.getMainLooper());
 public int onStartCommand(Intent i,int f,int s){create();h.removeCallbacksAndMessages(null);h.post(check);return START_STICKY;}
 void create(){if(Build.VERSION.SDK_INT>=26){NotificationChannel c=new NotificationChannel("guard","Shorts Guard",NotificationManager.IMPORTANCE_LOW);getSystemService(NotificationManager.class).createNotificationChannel(c);}startForeground(ID,new Notification.Builder(this,"guard").setContentTitle("Shorts Guard включён").setContentText("Лимит коротких видео контролируется").setSmallIcon(android.R.drawable.ic_lock_lock).build());}
 Runnable check=this::runCheck;
 void runCheck(){try{UsageStatsManager u=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);long now=System.currentTimeMillis(),start=now-86400000L;long total=0;for(UsageStats x:u.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,start,now)){if(x.getPackageName().equals("com.google.android.youtube"))total+=x.getTotalTimeInForeground();}int limit=getSharedPreferences("guard",0).getInt("limit",30);if(total>limit*60000L)startActivity(new Intent(this,LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP));}catch(Exception ignored){}h.postDelayed(check,15000);}
 public android.os.IBinder onBind(Intent i){return null;}
}
