package com.example.android.technews.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.technews.Articles.Article;
import com.example.android.technews.Articles.ArticleAdapter;
import com.example.android.technews.Articles.ArticlesLoader;
import com.example.android.technews.R;
import com.example.android.technews.Utils.QueryUtil;
import com.example.android.technews.Utils.QueryValues;

import java.util.ArrayList;

public class MainFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private ArticleAdapter adapter;

    private ListView listView;

    private TextView noResultsFound;

    private TextView noInternetConnection;

    private ProgressBar progressBar;


    private static String section = "world";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.feed, container, false);

        noResultsFound = (TextView) rootView.findViewById(R.id.no_results_found);
        noResultsFound.setVisibility(View.INVISIBLE);

        noInternetConnection = (TextView) rootView.findViewById(R.id.no_internet_connection);
        noInternetConnection.setVisibility(View.INVISIBLE);
        noInternetConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLoaderManager().restartLoader(0, null, MainFragment.this);
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        listView = (ListView) rootView.findViewById(R.id.list_view);



        adapter = new ArticleAdapter(getActivity(), new ArrayList<Article>());



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Article articleClicked = (Article) parent.getItemAtPosition(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleClicked.getArticleUrl()));

                startActivity(intent);
            }
        });



        listView.setAdapter(adapter);

        if (QueryUtil.hasInternetConnection(getActivity())) {

            getLoaderManager().initLoader(0, null, this).forceLoad();

        } else {

            progressBar.setVisibility(View.GONE);
            noResultsFound.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {

        Uri uri = Uri.parse(QueryValues.BASE_URL).buildUpon()
                .appendQueryParameter(QueryValues.SECTION_PARAM, getArguments().getString("section"))
                .appendQueryParameter(QueryValues.FORMAT_PARAM, QueryValues.FORMAT)
                .appendQueryParameter(QueryValues.TAGS_PARAM, QueryValues.TAG)
                .appendQueryParameter(QueryValues.API_KEY_PARAM, QueryValues.API_KEY).build();

        progressBar.setVisibility(View.VISIBLE);
        noInternetConnection.setVisibility(View.GONE);
        noResultsFound.setVisibility(View.GONE);

        return new ArticlesLoader(getContext(), uri.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Article>> loader, ArrayList<Article> data) {

        adapter.updateArticles(data);
        listView.setEmptyView(noResultsFound);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Article>> loader) {

        progressBar.setVisibility(View.VISIBLE);
        noInternetConnection.setVisibility(View.GONE);
        noResultsFound.setVisibility(View.GONE);
        adapter.updateArticles(new ArrayList<Article>());
    }
}
