package itsd1.indogrosir.com.siabo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itsd1.indogrosir.com.siabo.R;
import itsd1.indogrosir.com.siabo.activity.KalenderDetails;
import itsd1.indogrosir.com.siabo.activity.MainActivity;
import itsd1.indogrosir.com.siabo.models.ToDo;

/**
 * Created by Paulina on 2/8/2017.
 */
public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.ViewHolder> {

    ArrayList<ToDo.ToDoDetail> todolist;
    private String judul;
    private Context context;

    public AdapterTodo( ArrayList<ToDo.ToDoDetail> todolist)
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
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.txtJudul.setText(todolist.get(position).getJudul_tugas());
        holder.txtDeskripsi.setText(todolist.get(position).getDeskripsi_tugas());
    }

    @Override
    public int getItemCount()
    {
        return todolist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView txtJudul, txtDeskripsi;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtJudul = (TextView) itemView.findViewById(R.id.txtJudul);
            judul = txtJudul.toString();
            txtDeskripsi = (TextView) itemView.findViewById(R.id.txtDeskripsi);

            itemView.setOnClickListener(this);
            txtJudul.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Log.d("Log : ",judul);
            Toast.makeText(context, "click" + judul, Toast.LENGTH_LONG).show();
        }
    }

}
