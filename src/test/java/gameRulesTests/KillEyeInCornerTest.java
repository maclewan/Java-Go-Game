package gameRulesTests;
/**
 *
 * Test na zabicie oka w lewym gornym rogu
 *
 * */
import Controllers.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.Test;


/*Test bada lwy gorny rog planszy*/
public class KillEyeInCornerTest{



    @Test
    public void test() {

        GuiController test= new GuiController();

        /*temp[0][17].setStroke(Color.WHITE);
        temp[1][18].setStroke(Color.WHITE);*/
        Ellipse pb = new Ellipse();
        Ellipse pc = new Ellipse();
        pc.setFill(Color.BLACK);
        pb.setFill(Color.WHITE);
        test.checkers[0][1]=pb;
        test.checkers[1][0]=pb;
        test.checkers[1][1]=pb;
        test.checkers[2][0]=pc;
        test.checkers[2][1]=pc;
        test.checkers[1][2]=pc;
        test.checkers[0][2]=pc;

        test.isBlack=true;
        /*nie korzystam z addChecker poniewaz jest tam czesc odpowiedzialna za dodanie do planszy*/
        test.checkers[0][0]=pc;
        assert(!(test.isSuicide2(0,0)));

    }

}
