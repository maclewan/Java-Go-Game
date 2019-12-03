package gameRulesTests;
/**
 *
 * Test na zabicie 1 pionka
 *
 * */
import Controllers.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.Test;

/*Test bada samobojstwo 1 samotnego pionka*/
public class KillOneCheckerTest {

    @Test
    public void test() {
        GuiController test= new GuiController();
        Ellipse pb = new Ellipse();
        Ellipse pc = new Ellipse();
        pc.setFill(Color.BLACK);
        pb.setFill(Color.WHITE);
        test.checkers[2][2]=pb;
        test.checkers[3][3]=pb;
        test.checkers[1][3]=pb;
        test.checkers[2][4]=pb;
        test.isBlack=true;
        /*nie korzystam z addChecker poniewaz jest tam czesc odpowiedzialna za dodanie do planszy*/
        test.checkers[2][3]=pc;
        assert(test.isSuicide2(2,3));


    }

}
