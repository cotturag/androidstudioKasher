package com.example.kasher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FundsListAdapter extends ListAdapter<FundsForList,FundsListAdapter.ViewHolder> {
    private OnItemClickListener listener;

    FundsListAdapter(){
        super(DIFF);
    }
    private static final DiffUtil.ItemCallback<FundsForList> DIFF = new DiffUtil.ItemCallback<FundsForList>() {
        @Override
        public boolean areItemsTheSame(@NonNull FundsForList oldItem, @NonNull FundsForList newItem) {
            return (oldItem.getId()==newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FundsForList oldItem, @NonNull FundsForList newItem) {
//TODO itt az összeset beállítani
            /*
            return (oldItem.getOwner().equals(newItem.getOwner())&&oldItem.getType().equals(newItem.getType())&&
                    oldItem.getActivity()==newItem.getActivity()&&oldItem.getInactivity().equals(newItem.getInactivity())&&
                    oldItem.getName().equals(newItem.getName())&&oldItem.getPickedup()==newItem.getPickedup()&&
                    oldItem.getParent()==newItem.getParent());
*/

            return (oldItem.getOwner().equals(newItem.getOwner()));
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fundsview, parent, false);
        return new ViewHolder(item);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        FundsForList fund = getFundAt(position);
        holder.name.setText(fund.getName());
        holder.type.setText(String.valueOf(fund.getNameInPrivileges()));
        holder.hidden.setText(String.valueOf(fund.getId()));
        holder.money.setText(fund.getMoney());
        holder.hidden.setVisibility(View.INVISIBLE);
        if (fund.getPickedup()==1) holder.pick.setText("leadás");
        if (fund.getPickedup()==0) holder.pick.setText("felvétel");
        if (fund.getType().equals("1")
                ||fund.getType().equals("2")
                ||fund.getType().equals("5")
                ||fund.getType().equals("A")
                ||fund.getType().equals("B")) {
            holder.pick.setVisibility(View.INVISIBLE);
        }
        else {
            holder.pick.setVisibility(View.VISIBLE);
        }
        if ((fund.getType().equals("1"))
                ||(fund.getType().equals("2"))
                ||(fund.getType().equals("5"))){
            holder.createVirtual.setVisibility(View.VISIBLE);
        }
        else {
            holder.createVirtual.setVisibility(View.INVISIBLE);
        }




    }
    private FundsForList getFundAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView type;
        public TextView hidden;
        public TextView money;
        public Button pick;
        public Button createVirtual;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            hidden=itemView.findViewById(R.id.hidden);
            pick= itemView.findViewById(R.id.pick);
            createVirtual=itemView.findViewById(R.id.createvirtual);
            money=itemView.findViewById(R.id.money);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if (listener!=null && pos!=RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(pos));
                    }
                }
            });


        }
    }
    public interface OnItemClickListener {
        void onItemClick(FundsForList fund);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}



