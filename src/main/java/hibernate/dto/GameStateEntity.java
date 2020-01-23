package hibernate.dto;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "games", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_game")
        })

public class GameStateEntity implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_game", unique = false, nullable = true)
    private Integer gameID;

    @Column(name = "ID_move", unique = false, nullable = true, length = 100)
    private Integer moveID;

    @Column(name = "x", unique = false, nullable = true, length = 100)
    private Integer x;

    @Column(name = "y", unique = false, nullable = true, length = 100)
    private Integer y;

    @Column(name = "isBlack", unique = false, nullable = true, length = 100)
    private boolean isBlack;

    public Integer getGameId() {
        return gameID;
    }
    public void setGameId(Integer gameId) {
        this.gameID = gameId;
    }

    public Integer getMoveId() {
        return moveID;
    }
    public void setMoveId(Integer moveId) { this.moveID = moveId; }

    public Integer getY() {
        return y;
    }
    public void setY(int y) { this.y = y; }

    public Integer getX() {
        return x;
    }
    public void setX(int x) { this.x = x; }

    public Boolean getisBlack() {
        return isBlack;
    }
    public void setisBlack(boolean isBlack) { this.isBlack = isBlack; }


}
