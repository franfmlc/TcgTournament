package apps.crystalbits.tcgtournament;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import apps.crystalbits.tcgtournament.dao.PairingDao;
import apps.crystalbits.tcgtournament.dao.PlayerDao;
import apps.crystalbits.tcgtournament.dao.RoundDao;
import apps.crystalbits.tcgtournament.dao.StandingDao;
import apps.crystalbits.tcgtournament.dao.TournamentDao;
import apps.crystalbits.tcgtournament.entity.Pairing;
import apps.crystalbits.tcgtournament.entity.Round;
import apps.crystalbits.tcgtournament.entity.Tournament;
import apps.crystalbits.tcgtournament.pojo.PairingTuple;

public class Repository {

    private PlayerDao mPlayerDao;
    private TournamentDao mTournamentDao;
    private RoundDao mRoundDao;
    private PairingDao mPairingDao;
    private StandingDao mStandingDao;
    private LocalDatabase localDatabase;

    public Repository (Context context) {
        localDatabase = LocalDatabase.getDatabase(context);
        mPlayerDao = localDatabase.playerDao();
        mTournamentDao = localDatabase.tournamentDao();
        mRoundDao = localDatabase.roundDao();
        mPairingDao = localDatabase.pairingDao();
        mStandingDao = localDatabase.standingDao();
    }

    public LiveData<Tournament> getTournament(int tournamentId) {
        return mTournamentDao.getTournamentById(1);
    }

    public LiveData<Round> getRoundByTournamentId(int tournamentId) {
        return mRoundDao.getRoundByTournamentId(1);
    }

    public void updateTournament(final Tournament tournament) {
        LocalDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTournamentDao.update(tournament);
            }
        });
    }

    public void updateRound(final Round round) {
        LocalDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRoundDao.update(round);
            }
        });
    }

    public LiveData<List<PairingTuple>> getPairingsForTournament(int tournamentId) {
        return mPairingDao.getPairingsByTournamentId(1);
    }

    public void updatePairings(final List<Pairing> pairings) {
        LocalDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPairingDao.update(pairings);
            }
        });
    }
}
