package ca.bcit.assignment1_choi_mendiola;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


    public class NewsAdapter extends ArrayAdapter<Article> {
        Context _context;
        public NewsAdapter(Context context, List<Article> news) {
            super(context, 0, news);
            _context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Activity activity = (Activity) _context;
            // Get the data item for this position
            Article news = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
            }

            // Lookup view for data population
            TextView tvAuthor = convertView.findViewById(R.id.author);
            TextView tvTitle = convertView.findViewById(R.id.title);
            TextView tvDescription = convertView.findViewById(R.id.description);

            // Populate the data into the template view using the data object
            tvAuthor.setText(news.getAuthor());
            tvTitle.setText(news.getTitle());
            tvDescription.setText(news.getDescription());

            ImageView imgOnePhoto = convertView.findViewById(R.id.urlToImage);
            if (news.getUrlToImage() != null) {
                new ImageDownloaderTask(imgOnePhoto).execute(news.getUrlToImage());
            }

            // Return the completed view to render on screen
            return convertView;
        }
    }
