package ru.shield.shorts;

import android.app.*;import android.content.*;import android.graphics.Color;import android.net.Uri;import android.os.*;import android.provider.Settings;import android.view.*;import android.widget.*;

public class MainActivity extends Activity {
  final String PREF="guard"; EditText minutes,pin;
  public void onCreate(Bundle b){super.onCreate(b); show();}
  TextView t(String s,int z){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(Color.WHITE);v.setPadding(0,8,0,8);return v;}
  void show(){ LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(36,48,36,24);l.setBackgroundColor(Color.rgb(18,18,22));
    l.addView(t("Shorts Guard",30)); l.addView(t("Ограничение коротких видео без уговоров",16));
    minutes=new EditText(this);minutes.setHint("Лимит в минутах (например, 30)");minutes.setInputType(2);l.addView(minutes);
    pin=new EditText(this);pin.setHint("PIN для изменения настроек");pin.setInputType(2);l.addView(pin);
    Button save=new Button(this);save.setText("Сохранить и включить защиту");l.addView(save);save.setOnClickListener(v->save());
    Button usage=new Button(this);usage.setText("1. Разрешить доступ к статистике");l.addView(usage);usage.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
    l.addView(t("Приложение отслеживает YouTube, TikTok и Instagram. После лимита экран блокируется PIN-кодом. Для усиленного режима включите Shorts Guard в настройках специальных возможностей.",14)); setContentView(l);
  }
  void save(){int m=30;try{m=Integer.parseInt(minutes.getText().toString());}catch(Exception ignored){} if(m<1)m=30;getSharedPreferences(PREF,0).edit().putInt("limit",m).putString("pin",pin.getText().toString()).apply();startService(new Intent(this,GuardService.class));Toast.makeText(this,"Защита включена",Toast.LENGTH_SHORT).show();}
}
