package gameRulesTests;


import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import Controllers.*;
import javafx.scene.shape.Ellipse;
import org.junit.Test;

/*Test bada lwy gorny rog planszy*/
public class KillOneCheckerTest extends GuiController{

    @Test
    public void test() {
        GuiController test= new GuiController();

        /*temp[0][17].setStroke(Color.WHITE);
        temp[1][18].setStroke(Color.WHITE);*/
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
        //test.isSuicide(1,18);
        assert(test.isSuicide(2,3));
        //assert(test.checkers[1][18].getFill().equals(Color.BLACK));

    }

}
