package com.roh44x.xPlore.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.roh44x.xPlore.R;
import com.roh44x.xPlore.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    //declare widgets

    public TextView titleView, authorView, numLikesView, bodyView;
    public ImageView starView;


    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        numLikesView = (TextView)itemView.findViewById(R.id.numLikes);
        authorView = (TextView)itemView.findViewById(R.id.postAuthor);
        titleView = (TextView)itemView.findViewById(R.id.postTitle);
        bodyView = (TextView)itemView.findViewById(R.id.postBody);
        starView = (ImageView)itemView.findViewById(R.id.imLikes);

    }

    public void bindToBost(Post post, View.OnClickListener likeClickListener)
    {
        titleView.setText(post.title);
        bodyView.setText(post.body);
        authorView.setText(post.author);
        numLikesView.setText(String.valueOf(post.likeCount));
    }
}
