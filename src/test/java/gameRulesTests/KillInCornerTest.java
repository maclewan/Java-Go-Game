package gameRulesTests;


import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import Controllers.*;
import javafx.scene.shape.Ellipse;
import org.junit.Test;

/*Test bada lwy gorny rog planszy*/
public class KillInCornerTest extends GuiController{
    private Ellipse[][] checkers = new Ellipse[19][19];

    @Test
    public void test() {
        GuiController test= new GuiController();

        /*temp[0][17].setStroke(Color.WHITE);
        temp[1][18].setStroke(Color.WHITE);*/
        Ellipse pb = new Ellipse();
        Ellipse pc = new Ellipse();
        pc.setFill(Color.BLACK);
        pb.setFill(Color.WHITE);
        checkers[0][1]=pb;
        checkers[1][0]=pb;
        test.isBlack=true;
        /*nie korzystam z addChecker poniewaz jest tam czesc odpowiedzialna za dodanie do planszy*/
        checkers[0][0]=pc;
        assert(test.isSuicide2(0,0));

    }

}
