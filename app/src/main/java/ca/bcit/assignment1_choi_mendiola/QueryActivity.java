package ca.bcit.assignment1_choi_mendiola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QueryActivity extends AppCompatActivity {
    private static String[] queriedWord = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        Button search_btn1 = findViewById(R.id.search_button);
        search_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText keyword = findViewById(R.id.search);

                if (keyword.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.empty_searchbar_msg),
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                queriedWord[0] = keyword.getText().toString().replace(" ", "%20");
                Intent intent = new Intent(QueryActivity.this, ArticleListActivity.class);
                startActivity(intent);
            }
        });
    }

    static String getQueriedWord() {
        return queriedWord[0];
    }
}
