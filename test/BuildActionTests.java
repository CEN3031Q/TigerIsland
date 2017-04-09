import javafx.geometry.Point3D;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by hugh on 4/7/17.
 */

public class BuildActionTests {

    @Test
    public void testCreateServerStringFromBuildAction(){
        Point newSettlementPoint = new Point (-1, 0);
        Point3D cubePoint = Board.axialToCube(newSettlementPoint);
        String cubePointString = (int)cubePoint.getX() + " " + (int)cubePoint.getY() + " " + (int)cubePoint.getZ();
        BuildAction buildAction = new BuildAction("1", BuildActionType.FOUND_SETTLEMENT, newSettlementPoint, TerrainType.GRASS);

        String serverString = "FOUND SETTLEMENT AT " + cubePointString;
        Assert.assertEquals(serverString, buildAction.createServerStringFromBuildAction());

        buildAction = new BuildAction("1", BuildActionType.EXPAND_SETTLEMENT, newSettlementPoint, TerrainType.GRASS);
        serverString = "EXPAND SETTLEMENT AT "  + cubePointString + " GRASS";
        Assert.assertEquals(serverString, buildAction.createServerStringFromBuildAction());

        buildAction = new BuildAction("1", BuildActionType.TOTORO_SANCTUARY, newSettlementPoint, TerrainType.GRASS);
        serverString = "BUILD TOTORO SANCTUARY AT "  + cubePointString;
        Assert.assertEquals(serverString, buildAction.createServerStringFromBuildAction());

        buildAction = new BuildAction("1", BuildActionType.TIGER_PLAYGROUND, newSettlementPoint, TerrainType.GRASS);
        serverString = "BUILD TIGER PLAYGROUND AT "  + cubePointString;
        Assert.assertEquals(serverString, buildAction.createServerStringFromBuildAction());

        buildAction = new BuildAction("1", BuildActionType.UNABLE_TO_BUILD, newSettlementPoint, TerrainType.VOLCANO);
        serverString = "UNABLE TO BUILD";
        Assert.assertEquals(serverString, buildAction.createServerStringFromBuildAction());
    }

    @Test
    public void testGetBuildActionFromString(){
        String serverString = "FOUNDED SETTLEMENT AT -1 1 0";
        BuildAction buildAction = new BuildAction(serverString);
        Assert.assertEquals(BuildActionType.FOUND_SETTLEMENT, buildAction.getType());

        serverString = "EXPANDED SETTLEMENT AT -1 -1 0 GRASS";
        buildAction = new BuildAction(serverString);
        Assert.assertEquals(BuildActionType.EXPAND_SETTLEMENT, buildAction.getType());
        Assert.assertEquals(Board.cubeToAxial(new Point3D(-1, -1, 0)), buildAction.getCoordinates());
        Assert.assertEquals(TerrainType.GRASS, buildAction.getTerrainType());

        serverString = "BUILT TOTORO SANCTUARY AT -1 -1 0";
        buildAction = new BuildAction(serverString);
        Assert.assertEquals(BuildActionType.TOTORO_SANCTUARY, buildAction.getType());

        serverString = "BUILT TIGER PLAYGROUND AT -1 -1 0";
        buildAction = new BuildAction(serverString);
        Assert.assertEquals(BuildActionType.TIGER_PLAYGROUND, buildAction.getType());
    }
}
