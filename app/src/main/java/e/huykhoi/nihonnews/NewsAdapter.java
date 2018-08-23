package e.huykhoi.nihonnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huykhoi on 13/12/2017.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<News> newsList;

    public NewsAdapter(Context context, int layout, ArrayList<News> newsList) {
        this.context = context;
        this.layout = layout;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtTitle =(TextView) view.findViewById(R.id.textViewTitle);
        TextView txtTime = (TextView) view.findViewById(R.id.textViewTime);

        News news = newsList.get(i);
        txtTitle.setText(news.getTitle());
        txtTime.setText(news.getTimePublic());
        return view;
    }
}
