package com.example.project_movies.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_movies.R;
import com.example.project_movies.model.Models.article;

import java.util.ArrayList;
import java.util.List;

public class adapter_articles extends RecyclerView.Adapter<adapter_articles.articleViewHolder> {

    List<article> articles;
    public static OnClick onClick;

    public adapter_articles(List<article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public articleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
        return new articleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull articleViewHolder holder, int position) {

        article currentArticle= articles.get(position);
        holder.title.setText(currentArticle.getSuggested_link_text());
        holder.summary_short.setText(currentArticle.getSummary_short());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public   class articleViewHolder extends RecyclerView.ViewHolder{

      public TextView title;
        public TextView summary_short;

        public articleViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            summary_short=itemView.findViewById(R.id.summary_short);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick!=null){
                        onClick.OnItemClickListener(articles.get(getAdapterPosition()).getUrl());
                    }
                }
            });
        }
    }

    public interface OnClick{
        void OnItemClickListener(String url);
    }

    public void setOnClick(OnClick onClick){
        this.onClick=onClick;
    }
}
