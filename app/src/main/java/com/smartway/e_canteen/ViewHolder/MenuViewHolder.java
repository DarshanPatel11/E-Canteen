package com.smartway.e_canteen.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartway.e_canteen.Common.ServerCommon;
import com.smartway.e_canteen.Interface.ItemClickListener;
import com.smartway.e_canteen.R;

/**
 * Created by djsma on 04-02-2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;
    public MenuViewHolder(View itemView) {
        super(itemView);
        txtMenuName = itemView.findViewById(R.id.menu_name);
        imageView = itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.OnClick(view, getAdapterPosition(), false);
    }


}
