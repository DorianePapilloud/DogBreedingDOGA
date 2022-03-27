package com.example.dogbreedingdoga.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.util.RecyclerViewItemClickListener;
import com.example.dogbreedingdoga.R;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dogs_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(Dog.class))
            holder.mTextView.setText(((Dog) item).getNameDog());
        if (item.getClass().equals(Breeder.class))
            holder.mTextView.setText(((Breeder) item).getNameBreeder() + " " + ((Breeder) item).getSurnameBreeder());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof Dog) {
                        return ((Dog) mData.get(oldItemPosition)).getIdDog() == (((Dog) data.get(newItemPosition)).getIdDog() );
                    }
                    if(mData instanceof Breeder) {
                        return ((Breeder) mData.get(oldItemPosition)).getEmail().equals(
                                ((Breeder) data.get(newItemPosition)).getEmail());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof Dog) {
                        Dog newDog = (Dog) data.get(newItemPosition);
                        Dog oldDog = (Dog) mData.get(newItemPosition);

                        return Objects.equals(newDog.toString(), oldDog.toString())
//                                newDog.toString().equals(oldDog.toString())
                                && newDog.getBreederMail().equals(oldDog.getBreederMail());
                    }
                    if(data instanceof Breeder) {
                        Breeder newBreeder = (Breeder) data.get(newItemPosition);
                        Breeder oldBreeder = (Breeder) data.get(newItemPosition);

                        return newBreeder.getEmail().equals(oldBreeder.getEmail());
                    }

                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
