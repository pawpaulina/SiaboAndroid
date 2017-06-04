package itsd1.indogrosir.com.siabo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.activity.BuktiActivity;
import itsd1.indogrosir.com.siabo.activity.KalenderDetails;
import itsd1.indogrosir.com.siabo.activity.MainActivity;
import itsd1.indogrosir.com.siabo.activity.ToDoDetails;
import itsd1.indogrosir.com.siabo.models.Bukti;
import itsd1.indogrosir.com.siabo.models.ToDo;
import itsd1.indogrosir.com.siabo.rest.ApiClient;
import itsd1.indogrosir.com.siabo.rest.RestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 2/8/2017.
 */
public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.ViewHolder> {

    ArrayList<ToDo.ToDoDetail> todolist;
    private String judul,token, id_bukti;
    private Context context;
    private int id_todo, id_user, id_plan;
    private CardView layoutTodo;

    public AdapterTodo(ArrayList<ToDo.ToDoDetail> todolist)
    {
        this.todolist = todolist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtJudul.setText(todolist.get(position).getJudul_tugas());
        holder.txtDeskripsi.setText(todolist.get(position).getDeskripsi_tugas());
        holder.txtTodo.setText(String.valueOf(id_todo));
        holder.txtUser.setText(String.valueOf(id_user));
        holder.txtToken.setText(token.toString());
        holder.txtIDPlan.setText(String.valueOf(id_plan));
        holder.txtIDBukti.setText(id_bukti);
//        if(id_bukti!= "null")
//        {
//            layoutTodo.setCardBackgroundColor(Color.GREEN);
//        }
    }

    @Override
    public int getItemCount()
    {
        return todolist.size();
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
            b.putString("token", txtToken.getText().toString());
            b.putInt("tugas", 0);
            Intent i = new Intent(v.getContext(), ToDoDetails.class);
            i.putExtras(b);
            v.getContext().startActivity(i);
        }
    }

    public int getIDTodo(int idtodo)
    {
        return this.id_todo = idtodo;
    }

    public int getIDUser(int iduser)
    {
        return this.id_user = iduser;
    }

    public int getIDPlan(int idplan)
    {
        return this.id_plan = idplan;
    }

    public String getIDBukti(String idbukti)
    {
        return this.id_bukti = idbukti;
    }

    public String getToken(String token)
    {
        return this.token = token;
    }
}
