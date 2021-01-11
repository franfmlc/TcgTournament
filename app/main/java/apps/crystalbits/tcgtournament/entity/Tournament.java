package apps.crystalbits.tcgtournament.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tournament {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tournament_id")
    public int tournamentId;

    @ColumnInfo(name = "number_players")
    public int numberPlayers;

    @ColumnInfo(name = "number_rounds")
    public int numberRounds;

    @ColumnInfo(name = "round_time")
    public int roundTime;

    @ColumnInfo(name = "date")
    public String date;
}
