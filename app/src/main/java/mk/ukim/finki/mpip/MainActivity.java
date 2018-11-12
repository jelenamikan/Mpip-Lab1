package mk.ukim.finki.mpip;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void goToExplicitActivity(View view){
        Intent eIntent = new Intent(this, ExplicitActivity.class);
        startActivityForResult(eIntent, 1);
    }

    protected void goToImplicitActivity(View view){
        Intent iIntent = new Intent();
        iIntent.setAction("mk.ukim.finki.mpip.IMPLICIT_ACTION");
        iIntent.setType("text/plain");
        startActivity(iIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                TextView textView = findViewById(R.id.text_view);
                textView.setText(result);
            }
        }
        if (requestCode == 2 && data.getData() != null) {
            Uri selectedMedia = data.getData();
            ContentResolver cr = getContentResolver();
            String mime = cr.getType(selectedMedia);
            if(mime.toLowerCase().contains("image")) {
                Intent image = new Intent(Intent.ACTION_VIEW);
                image.setDataAndType(Uri.parse(getRealPathFromURI(selectedMedia)),"image/*");
                if(image.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(image, "Отвори со:"));
                }
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor       = managedQuery( contentUri, proj, null, null,null);
        if (cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return "file://" + cursor.getString(column_index);
    }

    protected void generateMail(View view){
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.putExtra(Intent.EXTRA_SUBJECT, "MPiP Send Title");
        mail.putExtra(Intent.EXTRA_TEXT, "Content send from MainActivity");
        mail.setType("mail/*");

        if(mail.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(mail, "Прати преко:"));
        }
    }

    protected void openGallery(View view){
        Intent pickImage = new Intent();
        pickImage.setType("image/*");
        pickImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickImage, "Избери слика:"), 2);
    }

}
