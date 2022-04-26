package com.mc2022.template;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mc2022.template.Models.Model_wardrive;

import java.util.List;

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiDatabaseHolder>{
    Context context;
    List<Model_wardrive> wifilist;
    private ScanResult i;

    public WifiAdapter(Context context,List<Model_wardrive> wifilist){
        this.context=context;
        this.wifilist=wifilist;
    }
    @NonNull
    @Override
    public WifiDatabaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata, parent,false);
        WifiDatabaseHolder holder=new WifiDatabaseHolder(view);
        return holder;    }

    @Override
    public void onBindViewHolder(@NonNull WifiDatabaseHolder holder, int position) {

        Model_wardrive i =wifilist.get(position);
        System.out.println(i);
//        holder.room_no.setText("\n BSSID:"+i.BSSID+"\n SSID:"+i.SSID+"\n level:"+i.level+"\n frequency:"+i.frequency+"\n timestamp:"+i.timestamp);

        holder.serial_no.setText(Integer.toString(i.getNum()));
        holder.name.setText(i.getName());
        holder.room_no.setText(i.getRoom_number());
        holder.val_ssi_1.setText(Integer.toString(i.getSsid_1()));
        holder.val_ssi_2.setText(Integer.toString(i.getSsid_2()));
        holder.val_ssi_3.setText(Integer.toString(i.getSsid_3()));
        holder.val_ssi_4.setText(Integer.toString(i.getSsid_4()));

    }

    @Override
    public int getItemCount() {
        if(wifilist==null){
            return 0;
        }
        System.out.println(wifilist.size());
        return wifilist.size();
    }


    class WifiDatabaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView serial_no, name,room_no,val_ssi_1,val_ssi_2,val_ssi_3,val_ssi_4;
        public WifiDatabaseHolder(View itemView)
        {
            super(itemView);
            serial_no = itemView.findViewById(R.id.serial_number);
            name = itemView.findViewById(R.id.room_name);
            room_no = itemView.findViewById(R.id.room_number);
            val_ssi_1 = itemView.findViewById(R.id.wifi_1);
            val_ssi_2 = itemView.findViewById(R.id.wifi_2);
            val_ssi_3 = itemView.findViewById(R.id.wifi_3);
            val_ssi_4 = itemView.findViewById(R.id.wifi_4);
        }

        @Override
        public void onClick(View view) {
//            fragment.runn(Integer.valueOf(getAdapterPosition()));
//            Log.i("clicked: ", "News fragment opened" );
        }


    }
}
