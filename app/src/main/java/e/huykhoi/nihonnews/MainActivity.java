package e.huykhoi.nihonnews;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;
import android.widget.TabWidget;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int CATE_NUM = 8;
    TabHost tabHost;
    Toolbar myToolbar;
    ListView[] arrListView = new ListView[CATE_NUM];
    ArrayList<News>[] news = new ArrayList[CATE_NUM];
    ArrayList<News> searchNews;

    NewsAdapter[] adapter = new NewsAdapter[CATE_NUM];
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    int currentTabIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        myToolbar.setTitle("日本の新聞");
        setSupportActionBar(myToolbar);
        tabHost.setup();
        addTab();
        int posTab;

        refreshFromFeed();

        sharedPreferences = getSharedPreferences("newsTab", MODE_PRIVATE);
        posTab = sharedPreferences.getInt("newsTab", 0);
        centerTabItem(posTab);

        arrListView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "主要ニュース");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[0].size(); j++) {
                    arrLink.add(news[0].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 0);
                editor.commit();
            }
        });

        arrListView[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "社会");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[1].size(); j++) {
                    arrLink.add(news[1].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 1);
                editor.commit();
            }
        });

        arrListView[2].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "文化・エンタメ");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[2].size(); j++) {
                    arrLink.add(news[2].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 2);
                editor.commit();
            }
        });

        arrListView[3].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "科学・医療");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[3].size(); j++) {
                    arrLink.add(news[3].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 3);
                editor.commit();
            }
        });

        arrListView[4].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "科学・医療");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[4].size(); j++) {
                    arrLink.add(news[4].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 4);
                editor.commit();
            }
        });

        arrListView[5].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "経済");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[5].size(); j++) {
                    arrLink.add(news[5].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 5);
                editor.commit();
            }
        });

        arrListView[6].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "国際");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[6].size(); j++) {
                    arrLink.add(news[6].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 6);
                editor.commit();
            }
        });
        arrListView[7].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                Bundle extras = new Bundle();

                extras.putString("newsTitle", "スポーツ");
                ArrayList<String> arrLink = new ArrayList<>();
                for (int j = 0; j < news[7].size(); j++) {
                    arrLink.add(news[7].get(j).getLink());
                }
                extras.putStringArrayList("arrLinkNews", arrLink);
                extras.putInt("newsNumber", i);
                intent.putExtras(extras);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("newsTab", 7);
                editor.commit();
            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String s) {
                currentTabIndex = tabHost.getCurrentTab();
            }
        });
      //  printKeyHash(MainActivity.this);
    }

    private void findViewById() {
        tabHost = (TabHost) findViewById(R.id.tabhost);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        arrListView[0] = (ListView) findViewById(R.id.ListView1);
        arrListView[1] = (ListView) findViewById(R.id.ListView2);
        arrListView[2] = (ListView) findViewById(R.id.ListView3);
        arrListView[3] = (ListView) findViewById(R.id.ListView4);
        arrListView[4] = (ListView) findViewById(R.id.ListView5);
        arrListView[5] = (ListView) findViewById(R.id.ListView6);
        arrListView[6] = (ListView) findViewById(R.id.ListView7);
        arrListView[7] = (ListView) findViewById(R.id.ListView8);
    }

    private void addTab() {

        TabHost.TabSpec spec1 = tabHost.newTabSpec("tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("主要ニュース", null);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("社会", null);
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("tab3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("文化・エンタメ", null);
        tabHost.addTab(spec3);

        TabHost.TabSpec spec4 = tabHost.newTabSpec("tab4");
        spec4.setContent(R.id.tab4);
        spec4.setIndicator("科学・医療", null);
        tabHost.addTab(spec4);

        TabHost.TabSpec spec5 = tabHost.newTabSpec("tab5");
        spec5.setContent(R.id.tab5);
        spec5.setIndicator("政治", null);
        tabHost.addTab(spec5);

        TabHost.TabSpec spec6 = tabHost.newTabSpec("tab6");
        spec6.setContent(R.id.tab6);
        spec6.setIndicator("経済", null);
        tabHost.addTab(spec6);

        TabHost.TabSpec spec7 = tabHost.newTabSpec("tab7");
        spec7.setContent(R.id.tab7);
        spec7.setIndicator("国際", null);
        tabHost.addTab(spec7);

        TabHost.TabSpec spec8 = tabHost.newTabSpec("tab8");
        spec8.setContent(R.id.tab8);
        spec8.setIndicator("スポーツ", null);
        tabHost.addTab(spec8);
    }

    public void centerTabItem(int position) {
        tabHost.setCurrentTab(position);
        //-----set Scroll to Selected Tab
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItem)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                refreshFromSearchFeed(newText);
                if(newText.isEmpty()){
                    refreshFromFeed();
                }
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                refreshFromSearchFeed(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateItem:
                dialog = ProgressDialog.show(MainActivity.this, "Loading", "Loading news of the day");
                refreshFromFeed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void refreshFromSearchFeed(String query){
        searchNews = new ArrayList<>();
        for(News searchNew : news[currentTabIndex]){
            if(searchNew.getTitle().contains(query)) {
                searchNews.add(searchNew);
            }
       }
        adapter[currentTabIndex] = new NewsAdapter(this,R.layout.line_news_views,searchNews);
        arrListView[currentTabIndex].setAdapter(adapter[currentTabIndex]);
    }

    private void refreshFromFeed() {
        for (int j = 0; j < CATE_NUM; j++) {
            news[j] = new ArrayList<>();
            adapter[j] = new NewsAdapter(this,R.layout.line_news_views,news[j]);
            arrListView[j].setAdapter(adapter[j]);
            new readRSS(j).execute("https://www3.nhk.or.jp/rss/news/cat" + j + ".xml");
        }
    }

    class readRSS extends AsyncTask<String,Void,String>{
        int taskNumber;
        readRSS(int i){
            this.taskNumber=i;
        }
        @Override
        protected String doInBackground(String... strings) {
                    StringBuilder stringBuilder = new StringBuilder();
                   try {
                       URL url = new URL(strings[0]);
                       InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream());
                       BufferedReader br = new BufferedReader(isr);
                       String line = "";
                       while ((line = br.readLine()) != null) {
                           stringBuilder.append(line);
                       }
                       br.close();
                   }catch(MalformedURLException e){
                       e.printStackTrace();
                   }catch(IOException e){
                       e.printStackTrace();
                   }
                    return stringBuilder.toString();
                }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMparser parser = new XMLDOMparser();
            Document doc = parser.getDocument(s);
            NodeList nlist = doc.getElementsByTagName("item");

            for(int j=0;j<nlist.getLength();j++){
                Element el = (Element) nlist.item(j);
                News newsEl = new News();
                newsEl.setTitle(parser.getValue(el,"title"));
                newsEl.setTimePublic(parser.getValue(el,"pubDate"));
                newsEl.setLink(parser.getValue(el,"link"));
                news[taskNumber].add(newsEl);
            }
            adapter[taskNumber].notifyDataSetChanged();//lam moi listView
            if(dialog!=null) {
                if (dialog.isShowing() && (taskNumber == CATE_NUM - 1)) {
                    dialog.dismiss();
                }
            }
        }
    }
}
