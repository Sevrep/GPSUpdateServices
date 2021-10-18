package com.sevrep.gpsupdateservices.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.gpsupdateservices.R;
import com.sevrep.gpsupdateservices.models.PostmenPositionModel;

import java.util.List;

/**
 * Created by : Ivan Peter Semilla on 18/10/2021
 */
public class PostmanPositionAdapter extends RecyclerView.Adapter<PostmanPositionAdapter.PostmanPositionViewHolder> {

    Context mCtx;
    List<PostmenPositionModel> postmenPositionModelList;

    public PostmanPositionAdapter(Context mCtx, List<PostmenPositionModel> postmenPositionModelList) {
        this.mCtx = mCtx;
        this.postmenPositionModelList = postmenPositionModelList;
    }

    @NonNull
    @Override
    public PostmanPositionAdapter.PostmanPositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.custom_row_item, parent, false);
        return new PostmanPositionViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PostmanPositionAdapter.PostmanPositionViewHolder holder, int position) {
        PostmenPositionModel postmenPositionModel = postmenPositionModelList.get(position);

        holder.txtId.setText(String.format("Id: %d", postmenPositionModel.getId()));
        holder.txtPostmanId.setText(String.format("Postman id: %d", postmenPositionModel.getPostmen_id()));
        holder.txtPostmanLat.setText(String.format("Latitude: %s", postmenPositionModel.getPostmen_lat()));
        holder.txtPostmanLong.setText(String.format("Longitude: %s", postmenPositionModel.getPostmen_long()));
        holder.txtCreatedAt.setText(String.format("Created at: %s", postmenPositionModel.getCreated_at()));
        holder.txtUpdatedAt.setText(String.format("Updated at: %s", postmenPositionModel.getUpdated_at()));
    }

    @Override
    public int getItemCount() {
        return postmenPositionModelList.size();
    }

    public static class PostmanPositionViewHolder extends RecyclerView.ViewHolder {

        TextView txtId;
        TextView txtPostmanId;
        TextView txtPostmanLat;
        TextView txtPostmanLong;
        TextView txtCreatedAt;
        TextView txtUpdatedAt;

        public PostmanPositionViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtId);
            txtPostmanId = itemView.findViewById(R.id.txtPostmanId);
            txtPostmanLat = itemView.findViewById(R.id.txtPostmanLat);
            txtPostmanLong = itemView.findViewById(R.id.txtPostmanLong);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtUpdatedAt = itemView.findViewById(R.id.txtUpdatedAt);
        }
    }
}
