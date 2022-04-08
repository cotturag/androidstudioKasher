package com.example.kasher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ActionsFundsListAdapter extends ListAdapter<FundsForList,ActionsFundsListAdapter.ViewHolder> {
    private MyOnItemClickListener listener;
    ActionsFundsListAdapter()
    {
        super(DIFF);
        //this.listener=listener;

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
                    oldItem.getHookedTo()==newItem.getHookedTo()&&oldItem.getOwnerinusers().equals(newItem.getOwnerinusers()));
        }
    };
    @NonNull
    @Override
    public ActionsFundsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actionfundsview, parent, false);

        return new ActionsFundsListAdapter.ViewHolder(item,this.listener);
    }
    @Override
    public void onBindViewHolder(@NonNull ActionsFundsListAdapter.ViewHolder holder, int position) {
        FundsForList fund = getFundAt(position);
        holder.name.setText(fund.getName());
        holder.type.setText(String.valueOf(fund.getNameInCodes()));
    }
    private FundsForList getFundAt(int position){
        return getItem(position);
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyOnItemClickListener listener;

        public TextView money;
        public TextView type;
        public TextView name;
        public TextView owner;
        public CardView fund;
        public TextView moneylabel;
        public Button pick;
        public ViewHolder(@NonNull View itemView,MyOnItemClickListener listener){
            super(itemView);

            money=itemView.findViewById(R.id.money);
            moneylabel=itemView.findViewById(R.id.moneylabel);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            owner=itemView.findViewById(R.id.owner);
            pick= itemView.findViewById(R.id.pick);
            fund=itemView.findViewById(R.id.fund);
            itemView.setOnClickListener(this);
            this.listener=listener;

        }

        @Override
        public void onClick(View v){
            this.listener.onItemClick(getAdapterPosition());


        }

    }

    public interface MyOnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(ActionsFundsListAdapter.MyOnItemClickListener listener) {
        this.listener = listener;
    }
}
