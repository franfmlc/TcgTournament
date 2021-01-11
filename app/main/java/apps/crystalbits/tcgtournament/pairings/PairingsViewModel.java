package apps.crystalbits.tcgtournament.pairings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import apps.crystalbits.tcgtournament.Repository;
import apps.crystalbits.tcgtournament.entity.Pairing;
import apps.crystalbits.tcgtournament.entity.Round;
import apps.crystalbits.tcgtournament.pojo.PairingTuple;
import apps.crystalbits.tcgtournament.util.RoundTimeManager;

public class PairingsViewModel extends ViewModel {

    private final Repository mRepository;
    private RoundTimeManager mRoundTimeManager;
    private LiveData<Round> round;
    private MutableLiveData<Integer> roundNumber;
    private MutableLiveData<Integer> remainingTime;
    private LiveData<List<PairingTuple>> pairings;
    private Observer<Round> roundObserver;
    private int lastTournament;
    private boolean roundStarted;

    public PairingsViewModel(Repository repository, RoundTimeManager roundTimeManager) {
        mRepository = repository;
        mRoundTimeManager = roundTimeManager;
        roundNumber = new MutableLiveData<>();
        remainingTime = new MutableLiveData<>();
        lastTournament = 1; //TODO mRepository.getLastTournament
        roundStarted = false;

        getRoundInformation();
    }

    public LiveData<Integer> getRoundNumber() {
        return roundNumber;
    }

    public LiveData<Integer> getRemainingTime() {
        return remainingTime;
    }

    public LiveData<List<PairingTuple>> getPairings() {
        if (pairings == null) {
            pairings = mRepository.getPairingsForTournament(1);
        }
        return pairings;
    }

    private void getRoundInformation() {
        if (round == null) {
            roundObserver = new Observer<Round>() {
                @Override
                public void onChanged(Round dbRound) {
                    if (dbRound != null) {
                        roundNumber.setValue(dbRound.roundNumber);
                        remainingTime.setValue(dbRound.remainingTime);
                    }
                }
            };

            round = mRepository.getRoundByTournamentId(1);
            round.observeForever(roundObserver);
        }
    }

    public boolean matchesUnfinished() {
        boolean matchesUnfinished = false;

        for (PairingTuple tuple : pairings.getValue()) {
            if (tuple.pairing.firstPlayerResult != 2 && tuple.pairing.secondPlayerResult != 2) {
                matchesUnfinished = true;
                break;
            }
        }
        return matchesUnfinished;
    }

    public boolean hasRoundStarted() {
        return roundStarted;
    }

    public void startCountDown() {
        if (mRoundTimeManager != null) {
            mRoundTimeManager.startCountDown(remainingTime);
            roundStarted = true;
        }
    }

    public void stopCountDown() {
        if (mRoundTimeManager != null) {
            mRoundTimeManager.stopCountDown();
        }
    }

    public void savePairings(List<PairingTuple> pairingsToUpdate) {
        if (pairingsToUpdate.size() > 0) {
            /* Room does not support updating pojos with relations yet. That's why entities need
            to be extracted from the list first. */
            List<Pairing> updatedPairings = new ArrayList<>();
            for (PairingTuple tuple : pairingsToUpdate) {
                updatedPairings.add(tuple.pairing);
            }
            mRepository.updatePairings(updatedPairings);
        }
    }

    public void saveRoundTime() {
        if (roundStarted) {
            int remainingRoundTime = mRoundTimeManager.getRemainingTime();
            Round roundToUpdate = round.getValue();

            roundToUpdate.remainingTime = remainingRoundTime;
            mRepository.updateRound(roundToUpdate);
        }
    }

    public void finishActions() {
        round.removeObserver(roundObserver);
    }
}