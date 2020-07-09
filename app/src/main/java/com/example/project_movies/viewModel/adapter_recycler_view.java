package com.example.project_movies.viewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_movies.R;
import com.example.project_movies.model.Models.Movie_1;

import org.w3c.dom.Text;

public class adapter_recycler_view extends PagedListAdapter<Movie_1, adapter_recycler_view.movieHolder> {

    public adapter_recycler_view() {
        super(DIFF_CALLBACK);
    }


    private OnItemClickListener onItemClick;


    public static final DiffUtil.ItemCallback<Movie_1> DIFF_CALLBACK=new DiffUtil.ItemCallback<Movie_1>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie_1 oldItem, @NonNull Movie_1 newItem) {

            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie_1 oldItem, @NonNull Movie_1 newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public movieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new movieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieHolder holder, int position) {

        Movie_1 movie1= getItem(position);
        holder.title.setText(movie1.getTitle());
        holder.text_releaseDate.setText(movie1.getRelease_date());
        holder.tex_vote_average.setText(String.valueOf(movie1.getVote_average()));
        Glide.with(holder.imageView.getContext())
                .load(movie1.getPoster_path())
                .into(holder.imageView);

    }

    public  class movieHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;
        private TextView tex_vote_average;
        private TextView text_releaseDate;
        public movieHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_poster);
            title=itemView.findViewById(R.id.text_title);
            tex_vote_average=itemView.findViewById(R.id.tex_vote_average);
            text_releaseDate=itemView.findViewById(R.id.text_releaseDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int  position= getAdapterPosition();
                    if (onItemClick!=null && position!= RecyclerView.NO_POSITION){

                        onItemClick.OnItemClick(getItem(position).getId(),
                                getItem(position).getVote_average(),
                                getItem(position).getTitle()
                                ,getItem(position).getRelease_date()
                                ,getItem(position).getPoster_path());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClick=listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int id,double voteAverage,String title,String releaseDate,String posterUrl);
    }
}
