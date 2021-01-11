package apps.crystalbits.tcgtournament.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import apps.crystalbits.tcgtournament.entity.Player;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM Player")
    List<Player> getAll();

    // TODO
    /* Query to get player opponents during a tournament. Opponent table needs to be created.
    @Query("SELECT opp_Id FROM Opponent WHERE player = :playerId AND tournament = :tournamentId")
    List<long> getOpponents(id playerId, id tournamentId);
    */

    @Insert
    void insert(Player player);

    @Insert
    void insert(List<Player> players);

    @Update
    void update(Player player);

    @Delete
    void delete(Player player);


    @Query("DELETE FROM Player")
    void deleteAll();
}
