
package com.example.myapplication.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.example.myapplication.Models.MovieDeets;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class CastAdapter  extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    // same logic as the other adapter
    private MovieDeets.Cast[] actorsArray;
    private Context context;

    public CastAdapter(Context context,MovieDeets.Cast[] actorsArray) {
        this.actorsArray = actorsArray;
        this.context =  context;
    }

    public MovieDeets.Cast[] getMoviesArray() {
        return actorsArray;
    }

    public void setActorsArray(MovieDeets.Cast[] actorsArray) {
        this.actorsArray = actorsArray;
    }

    @NonNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.CastViewHolder holder, int position) {
        MovieDeets.Cast cast = actorsArray[position];
        holder.charName.setText(cast.getName());
        holder.name.setText(cast.getCharacter());
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original/" + cast.getProfile_path())
                .into(holder.actorImage);
    }

    @Override
    public int getItemCount() {
        return actorsArray.length;
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        TextView charName;
        TextView name;
        ImageView actorImage;
        public CastViewHolder(@NonNull View parent) {
            super(parent);
            charName = parent.findViewById(R.id.charName);
            name = parent.findViewById(R.id.name);
            actorImage = parent.findViewById(R.id.actorImage);
        }
    }
}


