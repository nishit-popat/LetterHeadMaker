package com.example.i.letterheadmaker;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.print.PrintManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class Create extends AppCompatActivity {
    CardView card;
    LinearLayout l;
    FloatingActionButton fab;
    String C1;
    private Bitmap bitmap;
    //Button templets;

    EditText body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
         card=(CardView)findViewById(R.id.card);
        card.setBackgroundResource(R.drawable.twolines);
       // templets=(Button)findViewById(R.id.temp);
        body=(EditText)findViewById(R.id.Etbody);
        fab = (FloatingActionButton) findViewById(R.id.dow);
        ActivityCompat.requestPermissions(Create.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

       Intent in =getIntent();
       C1=in.getStringExtra("c1");
       body.setText(C1);


    }

    public void Createpdf(View view) {
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        String name="/myPDF "+currentDateTimeString+".pdf";

        PdfDocument myDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(card.getWidth(),card.getHeight(),1).create();
        PdfDocument.Page myPage = myDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        Bitmap screen;
        card.buildDrawingCache();
        card.setDrawingCacheEnabled(true);
        screen = Bitmap.createBitmap(card.getDrawingCache());
        card.setDrawingCacheEnabled(false);
      /*  View v1 = view.getRootView();
        v1.setDrawingCacheEnabled(true);
        screen = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);*/

        myPage.getCanvas().drawBitmap(screen,0,0,myPaint);
        myDocument.finishPage(myPage);

        String filePath = Environment.getExternalStorageDirectory().getPath() + name;
        File myFile = new File(filePath);
        try {
            myDocument.writeTo(new FileOutputStream(myFile));
            Toast.makeText(Create.this,"Created in Internal Storage",Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(Create.this,"Error",Toast.LENGTH_LONG).show();
        }
        myDocument.close();


    }


}
