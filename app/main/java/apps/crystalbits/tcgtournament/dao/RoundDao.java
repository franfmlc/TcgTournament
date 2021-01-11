package apps.crystalbits.tcgtournament.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import apps.crystalbits.tcgtournament.entity.Round;

@Dao
public interface RoundDao {
    @Transaction
    @Query("SELECT * FROM Round WHERE tournament_id = :tournamentId")
    LiveData<Round> getRoundByTournamentId(int tournamentId);

    @Insert
    void insert(Round round);

    @Update
    void update(Round round);

    @Delete
    void delete(Round round);

    @Query("DELETE FROM Round")
    void deleteAll();
}
