package io.github.b33t1e.home_menu;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddInfoActivity extends BaseActivity {

    public static final int CHOOSE_PHOTO = 2;
    public Bitmap ImageBitmap = null;
    private EditText editTextFirst;
    private EditText editTextThird;
    private EditText editTextFourth;
    private boolean isMenuInfoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        TextView TopText = (TextView) findViewById(R.id.AddInfoTopText);
        editTextFirst = (EditText) findViewById(R.id.AddInfoFirst);
        Button buttonSecond = (Button) findViewById(R.id.AddInfoSecond);
        editTextThird = (EditText) findViewById(R.id.AddInfoThird);
        editTextFourth = (EditText) findViewById(R.id.AddInfoFourth);
        Button buttonCancle = (Button) findViewById(R.id.AddInfoCancel);
        Button buttonOk = (Button) findViewById(R.id.AddInfoOk);
        Intent intent = getIntent();
        String StartClassName = intent.getStringExtra("StartClass");
        if (StartClassName.equals("MenuInfoActivity")) {
            TopText.setText("菜谱信息添加");
            editTextFirst.setHint("菜名：");
            editTextThird.setHint("简要介绍：");
            editTextFourth.setHint("配料：");
            isMenuInfoActivity = true;
        }
        else if (StartClassName.equals("CollocationInfoActivity")) {
            TopText.setText("营养搭配信息添加");
            editTextFirst.setHint("食物名称：");
            editTextThird.setHint("简要介绍：");
            editTextFourth.setHint("不宜同食：");
            isMenuInfoActivity = false;
        }
        else {
            Toast.makeText(AddInfoActivity.this,"Not Supported Error !!!",Toast.LENGTH_SHORT).show();
        }
        buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddInfoActivity.this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },1);
                } else {
                    openAlbum();
                }
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageBitmap == null) {
                    Toast.makeText(AddInfoActivity.this,"请先选取图片!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    String Name = editTextFirst.getText().toString();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] Image = baos.toByteArray();
                    String Intro = editTextThird.getText().toString();
                    String ContentOrNotTogether = editTextFourth.getText().toString();
                    if (isMenuInfoActivity) {
                        Dish dish = new Dish();
                        dish.setName(Name);
                        dish.setImageId(-1);
                        dish.setImageBitmap(Image);
                        dish.setIntro(Intro);
                        dish.setContent(ContentOrNotTogether);
                        dish.save();
                    }
                    else {
                        Food food = new Food();
                        food.setName(Name);
                        food.setImageId(-1);
                        food.setImageBitmap(Image);
                        food.setIntro(Intro);
                        food.setNotTogether(ContentOrNotTogether);
                        food.save();
                    }
                    Toast.makeText(AddInfoActivity.this,"成功添加!!!",Toast.LENGTH_SHORT).show();
                }
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

    public static void actionStart(Context context) {
        if (context.getClass().getSimpleName().toString().equals("MenuInfoActivity")) {
            Intent intent = new Intent(context, AddInfoActivity.class);
            intent.putExtra("StartClass","MenuInfoActivity");
            context.startActivity(intent);
        }
        else if (context.getClass().getSimpleName().toString().equals("CollocationInfoActivity")) {
            Intent intent = new Intent(context, AddInfoActivity.class);
            intent.putExtra("StartClass","CollocationInfoActivity");
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, AddInfoActivity.class);
            intent.putExtra("StartClass","NotSupported");
            context.startActivity(intent);
        }
    }
}
