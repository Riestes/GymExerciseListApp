package com.example.exerciselist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciselist.Entity.Move;
import com.example.exerciselist.R;

import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveHolder> {

    private List<Move> moves = new ArrayList<>();
    private MoveAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public MoveAdapter.MoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.move_item, parent, false);
        return new MoveHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoveAdapter.MoveHolder holder, int position) {
        Move currentMove = moves.get(position);
        holder.textViewMoveTitle.setText(currentMove.getMove_name());
        holder.textViewSetCount.setText(String.valueOf(currentMove.getSet_count()));
        holder.textViewRepCount.setText(String.valueOf(currentMove.getRep_count()));
        holder.textViewWeightCount.setText(String.valueOf(currentMove.getWeight_count_kg()));
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public Move remove(int position) {
        if (position < getItemCount() && position >= 0) {
            Move move = moves.remove(position);
            notifyItemRemoved(position);
            return move;
        }
        return null;
    }

    public void add(Move move, int position) {
        moves.add(position, move);
        notifyItemInserted(position);
    }

    /**
     * Pass moves to the RecyclerView
     *
     * @param moves
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(MoveAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Move move);
    }

    class MoveHolder extends RecyclerView.ViewHolder {
        private TextView textViewMoveTitle;
        private TextView textViewSetCount;
        private TextView textViewRepCount;
        private TextView textViewWeightCount;

        public MoveHolder(View itemView) {
            super(itemView);

            textViewMoveTitle = itemView.findViewById(R.id.text_view_move_title);
            textViewSetCount = itemView.findViewById(R.id.text_view_set_count);
            textViewRepCount = itemView.findViewById(R.id.text_view_rep_count);
            textViewWeightCount = itemView.findViewById(R.id.text_view_weight_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(moves.get(position));
                    }
                }
            });
        }
    }
}
