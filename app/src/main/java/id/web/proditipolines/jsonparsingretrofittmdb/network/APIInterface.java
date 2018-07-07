package id.web.proditipolines.jsonparsingretrofittmdb.network;

import id.web.proditipolines.jsonparsingretrofittmdb.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("search/movie")
    Call<MovieResponse>
    getTopRatedMovies(@Query("api_key") String apiKey,
                      @Query("query") String Query);

    @GET("movie/{id}")
    Call<MovieResponse>
    getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
