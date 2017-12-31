package com.perusudroid.roomlocal.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.perusudroid.roomlocal.R;
import com.perusudroid.roomlocal.adapter.listener.DishCallback;
import com.perusudroid.roomlocal.databinding.InflaterDishBinding;
import com.perusudroid.roomlocal.model.dto.response.Data;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesViewHolder> implements View.OnClickListener {

    private List<Data> oldData;
    private DishCallback dishCallback;

    public DishesAdapter(List<Data> newData, DishCallback dishCallback) {
        this.oldData = newData;
        this.dishCallback = dishCallback;
    }


    /*
        New way of refreshing adapter based on data
     */
    public void setDishList(final List<Data> newData) {

        if (oldData == null) {
            oldData = newData;

            notifyItemRangeInserted(0, oldData.size());
        } else {

            DiffUtil.DiffResult myResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return oldData.size();
                }

                @Override
                public int getNewListSize() {
                    return newData.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return oldData.get(oldItemPosition).getDish_id() ==
                            newData.get(newItemPosition).getDish_id();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return false;
                }
            });
            oldData = newData;
            myResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public DishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflating layout with DataBindingUtil
        InflaterDishBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.inflater_dish,
                        parent, false);
        //onClick is for the ripple effect
        binding.getRoot().setOnClickListener(this);
        binding.setCallback(dishCallback);
        return new DishesViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(DishesViewHolder holder, int position) {
        /*
        As we use DataBinding, not required to set text for each view.
         */
        Data myData = oldData.get(position);
        holder.inflaterDishBinding.setDishInflaterData(myData);
        holder.inflaterDishBinding.executePendingBindings();
        holder.itemView.setTag(myData);
    }

    @Override
    public int getItemCount() {
        return oldData == null ? 0 : oldData.size();
    }

    @Override
    public void onClick(View view) {

        Data myData = (Data) view.getTag();
        dishCallback.onClick(myData.getDish_id().toString(), view);
    }

    public class DishesViewHolder extends RecyclerView.ViewHolder {

        private InflaterDishBinding inflaterDishBinding;

        public DishesViewHolder(InflaterDishBinding binding) {
            super(binding.getRoot());
            this.inflaterDishBinding = binding;

            /*
            As we use DataBinding, not required to inflate view and so reduced lot of code!
            */

        }

    }
}
