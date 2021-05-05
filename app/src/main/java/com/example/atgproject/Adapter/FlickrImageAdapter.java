package com.example.atgproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atgproject.Activities.ImageViewerActivity;
import com.example.atgproject.ModelClass.Photo;
import com.example.atgproject.R;

import java.util.List;

public class FlickrImageAdapter extends RecyclerView.Adapter<FlickrImageAdapter.mViewHolderClass> {

    Context context;
    List<Photo> photoList;

    public FlickrImageAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public mViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_view_layout, parent, false);
        return new mViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolderClass holder, int position) {
        Glide.with(context).load(photoList.get(position).getUrl_s()).into(holder.imageView);
        holder.textView.setText(photoList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class mViewHolderClass extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public mViewHolderClass(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView3);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ImageViewerActivity.class);
                intent.putExtra("PHOTO", photoList.get(getAdapterPosition()));

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) context, imageView, "IMAGE");
                context.startActivity(intent, compat.toBundle());
            });
        }
    }
}
