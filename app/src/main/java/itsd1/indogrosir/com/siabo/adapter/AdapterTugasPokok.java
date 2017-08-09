package itsd1.indogrosir.com.siabo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.activity.ToDoDetails;
import itsd1.indogrosir.com.siabo.models.SiDao;
import itsd1.indogrosir.com.siabo.models.ToDo;
import itsd1.indogrosir.com.siabo.models.TugasPokok;

/**
 * Created by Paulina on 5/11/2017.
 */
public class AdapterTugasPokok extends RecyclerView.Adapter<AdapterTugasPokok.ViewHolder> {

    ArrayList<TugasPokok.TPDetail> tplist;
    private String judul, tanggal, today;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
    private Date dateToday, dateTP;
    private CardView layoutTodo;
    private SiDao siDao;

    public AdapterTugasPokok(ArrayList<TugasPokok.TPDetail> tplist, SiDao siDao)
    {
        this.siDao= siDao;
        this.tplist = tplist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Log.d("tanggal", tplist.get(position).getExp_date().toString());
        holder.txtJudul.setText(tplist.get(position).getJudul());
        holder.txtDeskripsi.setText(tplist.get(position).getDeskripsi());
        holder.txtTodo.setText(String.valueOf(siDao.getId_todo()));
        holder.txtUser.setText(String.valueOf(siDao.getId_user()));
        holder.txtToken.setText(siDao.getToken());
        holder.txtIDPlan.setText(String.valueOf(siDao.getId_plan()));
        holder.txtIDBukti.setText(String.valueOf(tplist.get(position).getId_bukti()));
        Log.wtf("idbukti :", String.valueOf(tplist.get(position).getId_bukti()));
        if(tplist.get(position).getId_bukti() != 0)
        {
            layoutTodo.setCardBackgroundColor(Color.GREEN);
        }
//        if(tplist.get(position).getExp_date() == null)
//        {
//            holder.txtJudul.setText(tplist.get(position).getJudul());
//            holder.txtDeskripsi.setText(tplist.get(position).getDeskripsi());
//            holder.txtTodo.setText(String.valueOf(siDao.getId_todo()));
//            holder.txtUser.setText(String.valueOf(siDao.getId_user()));
//            holder.txtToken.setText(siDao.getToken());
//            holder.txtIDPlan.setText(String.valueOf(siDao.getId_plan()));
//            holder.txtIDBukti.setText(String.valueOf(tplist.get(position).getId_bukti()));
//            Log.wtf("idbukti :", String.valueOf(tplist.get(position).getId_bukti()));
//            if(tplist.get(position).getId_bukti() != 0)
//            {
//                layoutTodo.setCardBackgroundColor(Color.GREEN);
//            }
//        }
//        else
//        {
//            //ngeget tanggal dlm bentuk string
//            today = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());
//            tanggal = new SimpleDateFormat("dd-MMM-yyyy").format(tplist.get(position).getExp_date());
//            //format tanggal yg udh di get jadi tipe datanya date
//            try {
//                dateToday = (Date)formatter.parse(today);
//                dateTP = (Date)formatter.parse(tanggal);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            //pengecekan tgl expired tugas
//            if(dateToday.after(dateTP))
//            {
//            }
//            else
//            {
//                holder.txtJudul.setText(tplist.get(position).getJudul());
//                holder.txtDeskripsi.setText(tplist.get(position).getDeskripsi());
//                holder.txtTodo.setText(String.valueOf(siDao.getId_todo()));
//                holder.txtUser.setText(String.valueOf(siDao.getId_user()));
//                holder.txtToken.setText(siDao.getToken());
//                holder.txtIDPlan.setText(String.valueOf(siDao.getId_plan()));
//                holder.txtIDBukti.setText(String.valueOf(tplist.get(position).getId_bukti()));
//                Log.wtf("idbukti :", String.valueOf(tplist.get(position).getId_bukti()));
//                if(tplist.get(position).getId_bukti() != 0)
//                {
//                    layoutTodo.setCardBackgroundColor(Color.GREEN);
//                }
//            }
//        }
    }

    @Override
    public int getItemCount()
    {
        return tplist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView txtJudul, txtDeskripsi, txtTodo, txtUser, txtToken, txtIDPlan, txtIDBukti;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txtJudul = (TextView) itemView.findViewById(R.id.txtJudul);
            judul = txtJudul.toString();
            txtDeskripsi = (TextView) itemView.findViewById(R.id.txtDeskripsi);
            txtTodo = (TextView) itemView.findViewById(R.id.txt_id_todo);
            txtUser = (TextView) itemView.findViewById(R.id.txt_id_user);
            txtToken = (TextView) itemView.findViewById(R.id.txt_token);
            txtIDPlan = (TextView) itemView.findViewById(R.id.txt_idplan);
            txtIDBukti = (TextView) itemView.findViewById(R.id.txt_idbukti);
            layoutTodo = (CardView) itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Bundle b = new Bundle();
            b.putString("judul", txtJudul.getText().toString());
            b.putString("desc", txtDeskripsi.getText().toString());
            b.putInt("id_todo", Integer.parseInt(txtTodo.getText().toString()));
            b.putInt("id_user", Integer.parseInt(txtUser.getText().toString()));
            b.putInt("id_plan", Integer.parseInt(txtIDPlan.getText().toString()));
            b.putInt("tugas", 1);
            b.putString("token", txtToken.getText().toString());
            Intent i = new Intent(v.getContext(), ToDoDetails.class);
            i.putExtras(b);
            v.getContext().startActivity(i);
        }
    }
}
