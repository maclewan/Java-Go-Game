package gameRulesTests;
/**
 * Test na zabicie wielu pionkow
 *
 * */
import Controllers.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.Test;

public class KillMoreControllerTest {
    private Ellipse[][] checkers = new Ellipse[19][19];

    @Test
    public void test() {
        GuiController test= new GuiController();
        Ellipse pb = new Ellipse();
        Ellipse pc = new Ellipse();
        pc.setFill(Color.BLACK);
        pb.setFill(Color.WHITE);
        checkers[2][2]=pb;
        checkers[3][2]=pb;
        checkers[4][2]=pb;
        checkers[5][2]=pb;
        checkers[6][2]=pb;
        checkers[7][2]=pb;
        checkers[7][3]=pb;
        checkers[7][4]=pb;
        checkers[7][5]=pb;
        checkers[6][5]=pb;
        checkers[5][5]=pb;
        checkers[5][4]=pb;
        checkers[4][4]=pb;
        checkers[3][4]=pb;
        checkers[2][4]=pb;
        checkers[2][3]=pb;
        test.isBlack=true;
        /*nie korzystam z addChecker poniewaz jest tam czesc odpowiedzialna za dodanie do planszy*/
        checkers[6][4]=pc;
        checkers[6][3]=pc;
        checkers[5][3]=pc;
        checkers[3][3]=pc;
        checkers[4][3]=pc;
        assert(test.isSuicide2(3,3));


    }
}
