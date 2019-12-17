package gameRulesTests;

import Server.GameLogic.Board;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.Test;

/**
 *
 * Test na zabicie 1 pionka w rogu(lewym gornym)
 *
 * */
public class KillInCornerController{

    @Test
    public void test() {
        Board test= new Board(null);
        //GuiController test= new GuiController();

        /*temp[0][17].setStroke(Color.WHITE);
        temp[1][18].setStroke(Color.WHITE);*/
        Ellipse pb = new Ellipse();
        Ellipse pc = new Ellipse();
        pc.setFill(Color.BLACK);
        pb.setFill(Color.WHITE);
      //  test.checkers[0][1]=pb;
      //  test.checkers[1][0]=pb;
      //  test.isBlack=true;
        /*nie korzystam z addChecker poniewaz jest tam czesc odpowiedzialna za dodanie do planszy*/
      //  test.checkers[0][0]=pc;
       // assert(test.isSuicide2(0,0));

    }

}
