package id.web.proditipolines.jsonparsingretrofittmdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import id.web.proditipolines.jsonparsingretrofittmdb.adapter.MoviesAdapter;
import id.web.proditipolines.jsonparsingretrofittmdb.model.Movie;
import id.web.proditipolines.jsonparsingretrofittmdb.model.MovieResponse;
import id.web.proditipolines.jsonparsingretrofittmdb.network.APIClient;
import id.web.proditipolines.jsonparsingretrofittmdb.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "a050df5725f01a6d3fe03f86baecd970";
    private EditText mEtSearch;
    private Button mBtnCari;
    private RecyclerView mMoviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Silahkan buat akun di themoviedb.org untuk mendapatkan API", Toast.LENGTH_LONG).show();
            return;
        }

        initView();
        mMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBtnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEtSearch.getText().toString().equals("")) {
                    cariMovie(mEtSearch.getText().toString());
                }
            }
        });

    }

    private void initView() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mBtnCari = (Button) findViewById(R.id.btn_cari);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
    }

    private void cariMovie(String query) {
        mBtnCari.setEnabled(false);
        mBtnCari.setText(" Sedang Mencari... ");
        APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void
            onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mBtnCari.setEnabled(true);
                mBtnCari.setText("Cari");
                List<Movie> movies = response.body().getResults();
                mMoviesRecyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                Toast.makeText(MainActivity.this, movies.size() + " Hasil Ditemukan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void
            onFailure(Call<MovieResponse> call, Throwable t) {
                mBtnCari.setEnabled(true);
                mBtnCari.setText("Cari");
                Log.e(TAG, t.toString());
            }
        });
    }


}

