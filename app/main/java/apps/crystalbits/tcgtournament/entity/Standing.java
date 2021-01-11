package apps.crystalbits.tcgtournament.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = Tournament.class, parentColumns = "id", childColumns = "tournament", onDelete = CASCADE),
        @ForeignKey(entity = Player.class, parentColumns = "id", childColumns = "player")
})

public class Standing {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "tournament")
    public int tournament;

    @ColumnInfo(name = "player")
    public int player;

    @ColumnInfo(name = "match_points")
    public long matchPoints;

    @ColumnInfo(name = "opp_match_win_percentage")
    public long oppMatchWinPercentage;

    @ColumnInfo(name = "game_win_percentage")
    public long gameWinPercentage;

    @ColumnInfo(name = "opp_game_win_percentage")
    public long oppGameWinPercentage;
}
