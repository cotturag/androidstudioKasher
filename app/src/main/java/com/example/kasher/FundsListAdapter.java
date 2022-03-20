package com.example.kasher;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FundsListAdapter extends ListAdapter<FundsForList,FundsListAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private OnButtonClickListener buttonClickListener;

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


            return (oldItem.getMoney().equals(newItem.getMoney())&&oldItem.getOwner().equals(newItem.getOwner())&&oldItem.getType().equals(newItem.getType())&&
                    oldItem.getActivity()==newItem.getActivity()&&oldItem.getInactivity().equals(newItem.getInactivity())&&
                    oldItem.getName().equals(newItem.getName())&&oldItem.getOtherOwner().equals(newItem.getOtherOwner())&&
                    oldItem.getHookedTo()==newItem.getHookedTo());


            //return (oldItem.getOwner().equals(newItem.getOwner()));
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
        holder.money.setText(fund.getMoney());

        if (fund.getType().equals("C")||fund.getType().equals("3")) holder.owner.setText(fund.getOwnerinusers());

        String loggedUser=FundsViewM.getLoggedUser();
        if (fund.getType().equals("1")||fund.getType().equals("A")) {
            holder.pick.setVisibility(View.INVISIBLE);
            if (fund.getType().equals("1")){
                holder.fund.setCardBackgroundColor(0xff000faa);
                holder.name.setTextColor(0xffffffff);
                holder.type.setTextColor(0xffffffff);
                holder.money.setTextColor(0xffffffff);
                holder.owner.setTextColor(0xffffffff);


                //TODO ezeket összekéne fogni
            }
            if (fund.getType().equals("A")){
                holder.fund.setCardBackgroundColor(0xff000faa);
                holder.name.setTextColor(0xffffffff);
                holder.type.setTextColor(0xffffffff);
                holder.money.setTextColor(0xffffffff);
                holder.owner.setTextColor(0xffffffff);

            }

        }
        else {
            holder.pick.setVisibility(View.VISIBLE);
            if (fund.getOtherOwner().equals(loggedUser)){
                holder.pick.setText("leadás a sajátokból");
                if (fund.getType().equals("2")) {
                    holder.name.setTextColor(0xffffffff);
                    holder.type.setTextColor(0xffffffff);
                    holder.money.setTextColor(0xffffffff);
                    holder.owner.setTextColor(0xffffffff);
                    holder.fund.setCardBackgroundColor(0xff008000);
                }
                if (fund.getType().equals("B")) {
                    holder.name.setTextColor(0xff000000);
                    holder.type.setTextColor(0xff000000);
                    holder.money.setTextColor(0xff000000);
                    holder.owner.setTextColor(0xff000000);
                    holder.fund.setCardBackgroundColor(0xff008000);

                }

                if (fund.getType().equals("3")) {
                    holder.fund.setCardBackgroundColor(0xffFFFFCC);
                    holder.name.setTextColor(0xff000000);
                    holder.type.setTextColor(0xff000000);
                    holder.money.setTextColor(0xff000000);
                    holder.owner.setTextColor(0xff000000);
                    holder.fund.setCardBackgroundColor(0xffFFCC66);
                }

                if (fund.getType().equals("C")) {
                    holder.fund.setCardBackgroundColor(0xffFFFFCC);
                    holder.name.setTextColor(0xff000000);
                    holder.type.setTextColor(0xff000000);
                    holder.money.setTextColor(0xff000000);
                    holder.owner.setTextColor(0xff000000);
                    holder.fund.setCardBackgroundColor(0xffFFCC66);
                }
            }
            else {
                if (fund.getOtherOwner().equals("")||(!fund.getOtherOwner().equals("")&&fund.getHookedTo()==0)){
                    holder.pick.setText("felvétel a sajátok közé");
                    holder.fund.setCardBackgroundColor(0xffff0000);
                    holder.name.setTextColor(0xffffffff);
                    holder.type.setTextColor(0xffffffff);
                    holder.money.setTextColor(0xffffffff);
                    holder.owner.setTextColor(0xffffffff);






                }
            }

        }











    }
    private FundsForList getFundAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView hidden;
        public TextView money;
        public TextView type;
        public TextView name;
        public TextView owner;
        public TextView otherOwner;
        public CardView fund;
        public LinearLayout fundstexts;

        public Button pick;
        public Button createVirtual;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            money=itemView.findViewById(R.id.money);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            owner=itemView.findViewById(R.id.owner);


            pick= itemView.findViewById(R.id.pick);

            fund=itemView.findViewById(R.id.fund);
            fundstexts=itemView.findViewById(R.id.fundstexts);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if (listener!=null && pos!=RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(pos));
                    }
                }
            });

            pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    buttonClickListener.onButtonClick(getItem(pos));
                }
            });


        }
    }
    public interface OnItemClickListener {
        void onItemClick(FundsForList fund);
    }
    public interface OnButtonClickListener {
        void onButtonClick(FundsForList fund);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setButtonClickListener(OnButtonClickListener buttonClickListener) {this.buttonClickListener=buttonClickListener;}
}



