package apps.crystalbits.tcgtournament.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(foreignKeys = {
        @ForeignKey(entity = Tournament.class, parentColumns = {"tournament_id"}, childColumns = {"tournament_id"}, onDelete = CASCADE),
        @ForeignKey(entity = Player.class, parentColumns = {"player_id"}, childColumns = {"first_player_id"}),
        @ForeignKey(entity = Player.class, parentColumns = {"player_id"}, childColumns = {"second_player_id"})
})

public class Pairing {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pairing_id")
    public int pairingId;

    @ColumnInfo(name = "tournament_id", index = true)
    public int tournamentId;

    @ColumnInfo(name = "first_player_id", index = true)
    public int firstPlayerId;

    @ColumnInfo(name = "second_player_id", index = true)
    public int secondPlayerId;

    @ColumnInfo(name = "first_player_result")
    public int firstPlayerResult;

    @ColumnInfo(name = "second_player_result")
    public int secondPlayerResult;
}
