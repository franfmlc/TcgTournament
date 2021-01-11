package apps.crystalbits.tcgtournament.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import apps.crystalbits.tcgtournament.entity.Pairing;
import apps.crystalbits.tcgtournament.pojo.PairingTuple;

@Dao
public interface PairingDao {
    @Transaction
    @Query("SELECT * FROM Pairing WHERE tournament_id = :tournamentId")
    LiveData<List<PairingTuple>> getPairingsByTournamentId(int tournamentId);

    @Insert
    void insert(Pairing pairing);

    @Insert
    void insert(List<Pairing> pairings);

    @Update
    void update(List<Pairing> pairings);

    @Delete
    void delete(Pairing Pairing);

    @Query("DELETE FROM Pairing")
    void deleteAll();
}
