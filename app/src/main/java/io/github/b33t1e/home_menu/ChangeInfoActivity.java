package io.github.b33t1e.home_menu;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ChangeInfoActivity extends BaseActivity {
    public static final int CHOOSE_PHOTO = 2;
    public Bitmap ImageBitmap = null;
    private TextView TopText;
    private TextView TextFirst;
    private EditText editTextThird;
    private EditText editTextFourth;
    private boolean isMenuInfoActivity;
    private String NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        TopText = (TextView) findViewById(R.id.ChangeInfoTopText);
        TextFirst = (TextView) findViewById(R.id.ChangeInfoFirst);
        Button buttonSecond = (Button) findViewById(R.id.ChangeInfoSecond);
        editTextThird = (EditText) findViewById(R.id.ChangeInfoThird);
        editTextFourth = (EditText) findViewById(R.id.ChangeInfoFourth);
        Button buttonCancle = (Button) findViewById(R.id.ChangeInfoCancel);
        Button buttonOk = (Button) findViewById(R.id.ChangeInfoOk);
        Intent intent = getIntent();
        NAME = intent.getStringExtra("name");
        String StartClassName = intent.getStringExtra("StartClass");
        if (StartClassName.equals("MenuInfoActivity")) {
            TopText.setText("菜谱信息修改-只在要修改的地方输入");
            TextFirst.setText("菜名：" + NAME);
            editTextThird.setHint("简要介绍：");
            editTextFourth.setHint("配料：");
            isMenuInfoActivity = true;
        }
        else if (StartClassName.equals("CollocationInfoActivity")) {
            TopText.setText("营养搭配信息添加-只在要修改的地方输入");
            TextFirst.setText("食物名称：" + NAME);
            editTextThird.setHint("简要介绍：");
            editTextFourth.setHint("不宜同食：");
            isMenuInfoActivity = false;
        }
        else {
            Toast.makeText(ChangeInfoActivity.this,"Not Supported Error !!!",Toast.LENGTH_SHORT).show();
        }
        buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ChangeInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChangeInfoActivity.this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },1);
                } else {
                    openAlbum();
                }
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Intro = editTextThird.getText().toString();
                String ContentOrNotTogether = editTextFourth.getText().toString();
                if (isMenuInfoActivity) {
                    Dish dish = new Dish();
                    if (ImageBitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] Image = baos.toByteArray();
                        dish.setImageBitmap(Image);
                    }
                    if (!Intro.isEmpty()) {
                        dish.setIntro(Intro);
                    }
                    if (!ContentOrNotTogether.isEmpty()) {
                        dish.setContent(ContentOrNotTogether);
                    }
                    dish.updateAll("name = ?",NAME);
                }
                else {
                    Food food = new Food();
                    if (ImageBitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] Image = baos.toByteArray();
                        food.setImageBitmap(Image);
                    }
                    if (!Intro.isEmpty()) {
                        food.setIntro(Intro);
                    }
                    if (!ContentOrNotTogether.isEmpty()) {
                        food.setNotTogether(ContentOrNotTogether);
                    }
                    food.updateAll("name = ?",NAME);
                }
                Toast.makeText(ChangeInfoActivity.this,"修改成功!!!",Toast.LENGTH_SHORT).show();
            }
        });
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }
                else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri,null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            SetImageBitmap(imagePath);
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        SetImageBitmap(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void SetImageBitmap(String imagePath) {
        if (imagePath != null) {
            ImageBitmap = BitmapFactory.decodeFile(imagePath);
        }
        else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
    
    public static void actionStart(Context context,String StartClassName,String Name) {
        Intent intent = new Intent(context, ChangeInfoActivity.class);
        intent.putExtra("StartClass",StartClassName);
        intent.putExtra("name", Name);
        context.startActivity(intent);
    }
}
