package apps.crystalbits.tcgtournament.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import apps.crystalbits.tcgtournament.entity.Tournament;

@Dao
public interface TournamentDao {
    @Query("SELECT * FROM Tournament WHERE tournament_id = :tournamentId")
    LiveData<Tournament> getTournamentById(int tournamentId);

    @Insert
    void insert(Tournament tournament);

    @Update
    void update(Tournament tournament);

    @Delete
    void delete(Tournament tournament);

    @Query("DELETE FROM Tournament")
    void deleteAll();
}
