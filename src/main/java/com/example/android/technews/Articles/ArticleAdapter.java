package com.example.android.technews.Articles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.technews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {


    public ArticleAdapter(Context context, List<Article> articles) {

        super(context, 0, articles);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;

        if (listView == null) {

            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        Article article = (Article) getItem(position);

        TextView section = (TextView) listView.findViewById(R.id.section);
        section.setText(article.getSectionName());

        TextView author = (TextView) listView.findViewById(R.id.author);

        if (article.getAuthor() == "") {

            author.setText("");

        } else {

            author.setText(" - " + article.getAuthor());
        }

        TextView title = (TextView) listView.findViewById(R.id.title);
        title.setText(article.getWebTitle());

        TextView date = (TextView) listView.findViewById(R.id.date);
        String dateTime = article.getPublicationDate();
        String articleDate = dateTime.substring(0, dateTime.indexOf("T"));

        String days = formatDate(articleDate);

        date.setText(days);

        return listView;
    }

    private String formatDate (String date) {

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

        Date dat = null;

        try {

            dat = dt.parse(date);

        } catch (ParseException e) {

            Log.e("Article Adapter:", "Problem formatting date");
        }

        Calendar calendar = Calendar.getInstance();

        long days = calendar.getTimeInMillis() - dat.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd");

        int daysFrom = Integer.parseInt(formatter.format(days));

        String numDays;

        switch (daysFrom) {

            case 1:
                numDays = "Today";
                break;
            case 2:
                numDays = "Yesterday";
                break;
            default:
                numDays = (daysFrom - 1) + " days ago";
                break;
        }

        return numDays;
    }

    public void updateArticles(ArrayList<Article> articles) {

        this.clear();

        if (articles != null) {

            for (Article article: articles) {

                insert(article, getCount());
            }
        }

        notifyDataSetChanged();
    }
}
