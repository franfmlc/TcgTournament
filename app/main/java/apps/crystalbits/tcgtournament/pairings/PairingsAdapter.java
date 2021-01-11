package apps.crystalbits.tcgtournament.pairings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import apps.crystalbits.tcgtournament.R;
import apps.crystalbits.tcgtournament.pojo.PairingTuple;

public class PairingsAdapter extends RecyclerView.Adapter<PairingsAdapter.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private static List<PairingTuple> mPairingList;
    private static List<PairingTuple> mPairingsToUpdate;

    public PairingsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void replaceData(List<PairingTuple> pairingsList) {
        mPairingList = pairingsList;
        mPairingsToUpdate = new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<PairingTuple> getPairingsToUpdate() {
        return mPairingsToUpdate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pairingRowView = mLayoutInflater.inflate(R.layout.pairing_row_item, parent, false);
        return new ViewHolder(pairingRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PairingTuple pairingTuple = mPairingList.get(position);
        holder.p1Name.setText(pairingTuple.firstPlayerName);
        holder.p2Name.setText(pairingTuple.secondPlayerName);
        holder.p1Score.setText(String.valueOf(pairingTuple.pairing.firstPlayerResult));
        holder.p2Score.setText(String.valueOf(pairingTuple.pairing.secondPlayerResult));
    }

    @Override
    public int getItemCount() {
        return mPairingList == null ? 0 : mPairingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView p1Name;
        private final TextView p2Name;
        private final Button p1Score;
        private final Button p2Score;
        private PairingTuple tuple;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p1Name = itemView.findViewById(R.id.p1_name);
            p2Name = itemView.findViewById(R.id.p2_name);
            p1Score = itemView.findViewById(R.id.p1_score);
            p2Score = itemView.findViewById(R.id.p2_score);

            p1Score.setOnClickListener(new scoreButtonListener());
            p2Score.setOnClickListener(new scoreButtonListener());
        }

        private class scoreButtonListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                Button scoreButton = (Button) view;
                int scoreButtonId = scoreButton.getId();
                int pairingPosition = getAdapterPosition();
                int currentValue = Integer.parseInt(scoreButton.getText().toString());
                int nextValue;
                int tupleIndex;

                tuple = mPairingList.get(pairingPosition);

                if (currentValue == 2)
                    nextValue = 0;
                else
                    nextValue = ++currentValue;

                if (scoreButtonId == R.id.p1_score)
                    tuple.pairing.firstPlayerResult = nextValue;
                else
                    tuple.pairing.secondPlayerResult = nextValue;

                scoreButton.setText(String.valueOf(nextValue));

                tupleIndex = mPairingsToUpdate.indexOf(tuple);
                if (tupleIndex >= 0)
                    mPairingsToUpdate.set(tupleIndex, tuple);
                else
                    mPairingsToUpdate.add(tuple);
            }
        }
    }
}
