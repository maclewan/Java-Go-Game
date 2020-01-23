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
    @Column(name = "ID_game", unique = true, nullable = false)
    private Integer gameID;

    @Column(name = "ID_move", unique = true, nullable = false, length = 100)
    private Integer moveID;

    @Column(name = "x", unique = false, nullable = false, length = 100)
    private Integer x;

    @Column(name = "y", unique = false, nullable = false, length = 100)
    private Integer y;

    @Column(name = "isBlack", unique = false, nullable = false, length = 100)
    private boolean isBlack;

    public Integer getGameId() {
        return gameID;
    }
    public void setGameId(Integer employeeId) {
        this.gameID = employeeId;
    }

    public Integer getMoveId() {
        return moveID;
    }
    public void setMoveId(Integer employeeId) {
        this.moveID = moveID;
    }

    public Integer getY() {
        return y;
    }
    public void setY(int y) { this.y = y; }

    public Integer getX() {
        return x;
    }
    public void setX(int y) { this.x = x; }

    public Boolean getisBlack() {
        return isBlack;
    }
    public void setisBlack(boolean isBlack) { this.isBlack = isBlack; }


}
