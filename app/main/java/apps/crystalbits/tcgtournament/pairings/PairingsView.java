package apps.crystalbits.tcgtournament.pairings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apps.crystalbits.tcgtournament.Injection;
import apps.crystalbits.tcgtournament.MainActivity;
import apps.crystalbits.tcgtournament.R;
import apps.crystalbits.tcgtournament.ViewModelFactory;
import apps.crystalbits.tcgtournament.pojo.PairingTuple;

public class PairingsView extends AppCompatActivity {
    private PairingsAdapter mPairingsAdapter;
    private PairingsViewModel mViewModel;
    private ViewModelFactory mViewModelFactory;
    private Button startRoundButton;
    private TextView mRoundNumber;
    private TextView mRoundTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pairings);
        loadCustomToolbar();

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(PairingsViewModel.class);

        mViewModel.getRoundNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer roundNumber) {
                mRoundNumber.setText(String.valueOf(roundNumber));
            }
        });

        mViewModel.getRemainingTime().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainingTime) {
                mRoundTime.setText(DateUtils.formatElapsedTime(remainingTime));
            }
        });

        mViewModel.getPairings().observe(this, new Observer<List<PairingTuple>>() {
            @Override
            public void onChanged(List<PairingTuple> pairings) {
                showPairings(pairings);
            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.pairings_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mPairingsAdapter = new PairingsAdapter(this);
        mRecyclerView.setAdapter(mPairingsAdapter);

        startRoundButton = findViewById(R.id.button_start_round);
        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.startCountDown();
                startRoundButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewModel.hasRoundStarted())
            startRoundButton.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /* Round information will be stored to database on every pause. This way, it won't be lost
        in case the user removes the application from the background. */
        mViewModel.savePairings(mPairingsAdapter.getPairingsToUpdate());
        mViewModel.saveRoundTime();
        if (isFinishing()) {
            mViewModel.stopCountDown();
            mViewModel.finishActions();
        }
    }

    public void showPairings(List<PairingTuple> pairings) {
        mPairingsAdapter.replaceData(pairings);
    }

    @Override
    public void onBackPressed() {
        displayAlertDialog(R.string.pause_tournament_title, R.string.pause_tournament_msg, false);
    }

    private void loadCustomToolbar() {
        Toolbar toolbar = findViewById(R.id.pairings_toolbar);
        toolbar.inflateMenu(R.menu.pairings_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.finish_round) {
                    if (mViewModel.matchesUnfinished())
                        PairingsView.this.displayAlertDialog(R.string.unfinished_matches_title, R.string.unfinished_matches_msg, true);
                    else {
                        // TODO Create Pairings
                    }
                } else if (id == R.id.pause_tournament) {
                    displayAlertDialog(R.string.pause_tournament_title, R.string.pause_tournament_msg, false);
                }
                return PairingsView.super.onOptionsItemSelected(item);
            }
        });

        mRoundNumber = findViewById(R.id.toolbar_round_number);
        mRoundTime = findViewById(R.id.toolbar_round_time);
    }

    private void displayAlertDialog(int title, int message, final boolean startNewRound){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.continues, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (startNewRound) {
                            //mViewModel.storePairings(); // AND Round Time
                            // TODO mViewModel prepareRound and create standings, + refresh this view, etc
                        }
                        else {
                            // TODO store current data
                            Intent intent = new Intent(PairingsView.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).create().show();
    }
}