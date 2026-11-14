package ru.shield.shorts; import android.content.*; public class BootReceiver extends BroadcastReceiver{public void onReceive(Context c,Intent i){c.startService(new Intent(c,GuardService.class));}}
