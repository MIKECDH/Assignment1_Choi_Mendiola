package ca.bcit.assignment1_choi_mendiola;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        String authorString = getIntent().getStringExtra("author");
        String sourceString = getIntent().getStringExtra("source");
        String titleString = getIntent().getStringExtra("title");
        String descriptionString = getIntent().getStringExtra("description");
        String urlString = getIntent().getStringExtra("url");
        String urlToImageString = getIntent().getStringExtra("urlToImage");
        String publishedAtString = getIntent().getStringExtra("publishedAt");
        String contentString = getIntent().getStringExtra("content");

        TextView author = findViewById(R.id.articleAuthor);
        TextView url = findViewById(R.id.articleUrl);
        TextView title = findViewById(R.id.articleTitle);
        TextView description = findViewById(R.id.articleDescription);
        TextView content = findViewById(R.id.articleContent);
        TextView publishedAt = findViewById(R.id.articlePublishedAt);
        TextView source = findViewById(R.id.articleSource);
        ImageView urlToImage = findViewById(R.id.urlToImage);



        // set all of the values
        author.setText(authorString);
        url.setText(urlString);
        title.setText(titleString);
        description.setText(descriptionString);
        publishedAt.setText(publishedAtString);
        content.setText(contentString);
        source.setText(sourceString);

        new ImageDownloaderTask(urlToImage).execute(urlToImageString);
    }

}
