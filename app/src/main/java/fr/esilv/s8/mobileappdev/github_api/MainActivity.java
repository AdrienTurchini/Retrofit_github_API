package fr.esilv.s8.mobileappdev.github_api;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

  private ListView listView;
  String API_BASE_URL = "https://api.github.com/";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // ListView where we are displaying the content
    listView = (ListView) findViewById(R.id.pagination_list);

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // Builder with baseUrl the only thing we have to change to get data
    // Converter Factory to convert JSON to Java object using GSON
    Retrofit.Builder builder = new Retrofit.Builder()
      .baseUrl(API_BASE_URL)
      .addConverterFactory(
              GsonConverterFactory.create()
      );

    Retrofit retrofit =
            builder
                    .client(
                            httpClient.build()
                    )
                    .build();

    //instance of github client
    GitHubClient client = retrofit.create(GitHubClient.class);

    // fetch a list of github repo
    Call<List<GitHubRepo>> call = client.reposForUser("AdrienTurchini");

    // Execute the call asynchronously. Get a positive or negative callback.
    call.enqueue(new Callback<List<GitHubRepo>>() {
      @Override
      public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

          List<GitHubRepo> repos = response.body();
          listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
      }

      @Override
      public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
        Toast.makeText(MainActivity.this, "error : (", Toast.LENGTH_SHORT).show();
      }
    });
  }
}

