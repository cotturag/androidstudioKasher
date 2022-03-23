package com.example.kasher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ActionsFundsListAdapter extends ListAdapter<FundsForList,ActionsFundsListAdapter.ViewHolder> {
    ActionsFundsListAdapter(){
        super(DIFF);
    }
    private static final DiffUtil.ItemCallback<FundsForList> DIFF = new DiffUtil.ItemCallback<FundsForList>() {
        @Override
        public boolean areItemsTheSame(@NonNull FundsForList oldItem, @NonNull FundsForList newItem) {
            return (oldItem.getId()==newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FundsForList oldItem, @NonNull FundsForList newItem) {
            return (oldItem.getMoney().equals(newItem.getMoney())&&oldItem.getOwner().equals(newItem.getOwner())&&oldItem.getType().equals(newItem.getType())&&
                    oldItem.getActivity()==newItem.getActivity()&&oldItem.getInactivity().equals(newItem.getInactivity())&&
                    oldItem.getName().equals(newItem.getName())&&oldItem.getOtherOwner().equals(newItem.getOtherOwner())&&
                    oldItem.getHookedTo()==newItem.getHookedTo());
        }
    };
    @NonNull
    @Override
    public ActionsFundsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fundsview, parent, false);
        return new ActionsFundsListAdapter.ViewHolder(item);
    }
    @Override
    public void onBindViewHolder(@NonNull ActionsFundsListAdapter.ViewHolder holder, int position) {
        FundsForList fund = getFundAt(position);
        holder.name.setText(fund.getName());
        holder.type.setText(String.valueOf(fund.getNameInPrivileges()));
    }
    private FundsForList getFundAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView money;
        public TextView type;
        public TextView name;
        public TextView owner;
        public CardView fund;
        public TextView moneylabel;
        public Button pick;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            money=itemView.findViewById(R.id.money);
            moneylabel=itemView.findViewById(R.id.moneylabel);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            owner=itemView.findViewById(R.id.owner);
            pick= itemView.findViewById(R.id.pick);
            fund=itemView.findViewById(R.id.fund);
        }
    }
}
