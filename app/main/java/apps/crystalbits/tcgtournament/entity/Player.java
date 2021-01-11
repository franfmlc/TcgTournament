package apps.crystalbits.tcgtournament.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_id")
    public int playerId;

    @ColumnInfo(name = "player_name")
    public String playerName;

    @ColumnInfo(name = "player_identification")
    public String playerIdentification;
}