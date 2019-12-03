package gameRulesTests;


import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import Controllers.*;
import javafx.scene.shape.Ellipse;
import org.junit.Test;

/*Test bada samobojstwo 1 samotnego pionka*/
public class KillOneCheckerTest extends GuiController{
    private Ellipse[][] checkers = getCheckers();

    @Test
    public void test() {
        GuiController test= new GuiController();
        Ellipse pb = new Ellipse();
        Ellipse pc = new Ellipse();
        pc.setFill(Color.BLACK);
        pb.setFill(Color.WHITE);
        checkers[2][2]=pb;
        checkers[3][3]=pb;
        checkers[1][3]=pb;
        checkers[2][4]=pb;
        test.isBlack=true;
        /*nie korzystam z addChecker poniewaz jest tam czesc odpowiedzialna za dodanie do planszy*/
        checkers[2][3]=pc;
        assert(test.isSuicide2(2,3));


    }

}
