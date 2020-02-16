package ca.bcit.assignment1_choi_mendiola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArticleListActivity extends AppCompatActivity {
    private String TAG = ArticleListActivity.class.getSimpleName();
    private ListView listView;
    private final String API_KEY = "465c153296d849b28cbec5866a76519b";
    private String DATE = calculateDate();
    private String QUERY = QueryActivity.getQueriedWord();
    private String SERVICE_URL = String.format("https://newsapi.org/v2/everything?q=%s&from=%s&sortBy=relevancy&apiKey=%s",
            QUERY, DATE, API_KEY);

    private List<Article> newsList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        newsList2 = new ArrayList<>();
        listView = findViewById(R.id.newsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ArticleListActivity.this,
                        ArticleDetailsActivity.class);
                intent.putExtra("source", newsList2.get(i).getSource().getName());
                intent.putExtra("author", newsList2.get(i).getAuthor());
                intent.putExtra("title", newsList2.get(i).getTitle());
                intent.putExtra("description", newsList2.get(i).getDescription());
                intent.putExtra("url", newsList2.get(i).getUrl());
                intent.putExtra("urlToImage", newsList2.get(i).getUrlToImage());
                intent.putExtra("publishedAt", newsList2.get(i).getPublishedAt());
                intent.putExtra("content", newsList2.get(i).getContent());
                startActivity(intent);
            }
        });
        new GetContacts().execute();
    }

    /**
     * This method retrieves the current calendar date and then calculates
     * so that the date is set 1 week in the past. The date is then formatted
     * as a string that can be put into the SERVICE_URL.
     * @return a string
     */
    private static String calculateDate() {
        Calendar date = Calendar.getInstance();
        return String.format("%s-%s-%s", date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1,
                date.get(Calendar.WEEK_OF_MONTH - 1));
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request to url and getting response
            System.out.println(SERVICE_URL);
            jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                Log.d(TAG, "Json: " + jsonStr);
                // this step is needed to wrap the JSON array inside
                Gson gson = new Gson();
                News news = gson.fromJson(jsonStr, News.class);
                newsList2 = news.getArticles();
            } else {
                Log.e(TAG, " u Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            NewsAdapter adapter = new NewsAdapter(ArticleListActivity.this, newsList2);

            // Attach the adapter to a ListView
            listView.setAdapter(adapter);
        }
    }


}

