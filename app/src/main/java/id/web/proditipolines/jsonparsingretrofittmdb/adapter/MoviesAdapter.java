package id.web.proditipolines.jsonparsingretrofittmdb.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.web.proditipolines.jsonparsingretrofittmdb.R;
import id.web.proditipolines.jsonparsingretrofittmdb.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView foto;

        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            foto = (ImageView) v.findViewById(R.id.iv_foto);
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
        Picasso.get().load("https://image.tmdb.org/t/p/w92"+movies.get(position).getPosterPath()).into(holder.foto);
        holder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie detail = movies.get(position);
                Dialog dialog = new Dialog(holder.moviesLayout.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_detail);

                TextView dJudul = (TextView) dialog.findViewById(R.id.judul);
                dJudul.setText(detail.getTitle());
                TextView dSinopsis = (TextView) dialog.findViewById(R.id.sinopsis);
                dSinopsis.setText(detail.getOverview());
                ImageView dFoto = (ImageView) dialog.findViewById(R.id.foto);
                Picasso.get().load("https://image.tmdb.org/t/p/w92"+detail.getPosterPath()).into(dFoto);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}