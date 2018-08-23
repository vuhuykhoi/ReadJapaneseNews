package e.huykhoi.nihonnews;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    int SWIPE_THRESHOLD = 200;
    int SWIPE_VELOCITY_THRESHOLD = 200;
    GestureDetector gestureDetector;

    WebView webView;
    Toolbar myToolbar;
    SharedPreferences sharedPreferences;

    int pos;
    ArrayList<String> arrLink;

    //Variable for share on facebook:
    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        sharedPreferences = getSharedPreferences("dialogSetting", MODE_PRIVATE);

        myToolbar = findViewById(R.id.my_toolbar);
        webView = findViewById(R.id.WebViewNews);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String title = extras.getString("newsTitle");

        myToolbar.setTitle(title);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arrLink = extras.getStringArrayList("arrLinkNews");
        pos = extras.getInt("newsNumber");
        webView.loadUrl(arrLink.get(pos));
        webView.setWebViewClient(new WebViewClient());

        changeTextSizeWebView(sharedPreferences.getInt("textSize", 100));
        gestureDetector = new GestureDetector(this, new myGesture());
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });

        //Init FB
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
    }

    protected int getWebTextSize() {
        final WebSettings webSettings = webView.getSettings();
        return webSettings.getTextZoom();
    }

    protected void changeTextSizeWebView(int i) {
        final WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(i);
    }

    private void DialogChangeTextSize() {
        final int seekBarDefaultValue = getWebTextSize();
        //la du lieu cho seekBar

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_text_size);
        Button btnApply = (Button) dialog.findViewById(R.id.buttonApply);
        Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        Button btnDefault = (Button) dialog.findViewById(R.id.buttonDefault);
        final SeekBar txtSizeBar = (SeekBar) dialog.findViewById(R.id.seekBar2);
        txtSizeBar.setProgress(seekBarDefaultValue);
        txtSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //i:gia tri tra ve cua SeekBar
                changeTextSizeWebView(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextSizeWebView(seekBarDefaultValue);
                dialog.dismiss();
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextSizeWebView(txtSizeBar.getProgress());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("textSize", txtSizeBar.getProgress());
                editor.commit();
                dialog.dismiss();
            }
        });
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSizeBar.setProgress(100);
            }
        });
        dialog.show();
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(NewsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.itemTextSize:
                DialogChangeTextSize();
                return true;
            case R.id.itemShare:
                shareLinkToFacebook(arrLink.get(pos));

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void shareLinkToFacebook(String link) {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setQuote("This is news link")
                .setContentUrl(Uri.parse(link))
                .build();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(linkContent);
        }
    }

    class myGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //keo tu trai sang phai
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (pos > 0) {
                    pos = pos - 1;
                    webView.loadUrl(arrLink.get(pos));
                } else if (pos == 0) {
                    pos = arrLink.size() - 1;
                    webView.loadUrl(arrLink.get(pos));
                }
            }
            //keo tu phai sang trai
            if (e1.getX() - e2.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (pos == arrLink.size() - 1) {
                    pos = 0;
                    webView.loadUrl(arrLink.get(pos));
                } else if (pos < arrLink.size() - 1) {
                    pos = pos + 1;
                    webView.loadUrl(arrLink.get(pos));
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
