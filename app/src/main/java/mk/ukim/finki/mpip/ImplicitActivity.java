package mk.ukim.finki.mpip;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ImplicitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        getApps();
    }

    protected void getApps(){
        PackageManager manager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resInfos = manager.queryIntentActivities(intent, 0);
        HashSet<String> packageNames = new HashSet<>(0);
        List<ApplicationInfo> appInfos = new ArrayList<>(0);
        List<String> text = new ArrayList<>();

        for(ResolveInfo resolveInfo : resInfos) {
            packageNames.add(resolveInfo.activityInfo.packageName);
        }
        for(String packageName : packageNames) {
            try {
                appInfos.add(manager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
            } catch (PackageManager.NameNotFoundException e) {
                //Do Nothing
            }
        }

        TextView textView = findViewById(R.id.text_view);
        textView.setText("");
        for(ApplicationInfo str : appInfos){
            text.add(manager.getApplicationLabel(str)+System.getProperty("line.separator"));
        }
        Collections.sort(text);
        for(String str : text)
            textView.append(str);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
