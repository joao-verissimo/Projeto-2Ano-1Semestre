package domain.store;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class closesthubtoclientTest {

    DistributionNetwork distributionNetwork = new DistributionNetwork();


    String USER_PATH = "docs/grafos/Small/clientes-produtores_small.csv";
    String DISTANCE_PATH = "docs/grafos/Small/distancias_small.csv";


    @BeforeEach
    void setUp() throws IOException {
        distributionNetwork.importUserCSV(USER_PATH);
        distributionNetwork.importDistanceCSV(DISTANCE_PATH);
        distributionNetwork.setHUBs(3);
    }


    @Test
    public void testclosestHubToClient() {
        closestHubToClient.getclosesthubtoclients(distributionNetwork.getGraph(), Integer::compare, Integer::sum, 0);
        System.out.println("Test of closestHubToClient");
        Object i = closestHubToClient.getclosesthubtoclient(distributionNetwork.getGraph(), distributionNetwork.getGraph().vertices().get(0), Integer::compare, Integer::sum, 0);
        assertEquals(i.toString(),"Loc ID: CT9 User ID: E4 Is HUB: true","The closest hub to vertice 0 must be CT9");
        Object i1 = closestHubToClient.getclosesthubtoclient(distributionNetwork.getGraph(), distributionNetwork.getGraph().vertices().get(1), Integer::compare, Integer::sum, 0);
        assertEquals(i1.toString(),"Loc ID: CT11 User ID: E2 Is HUB: true","The closest hub to vertice 1 must be CT11");
        Object i2 = closestHubToClient.getclosesthubtoclient(distributionNetwork.getGraph(), distributionNetwork.getGraph().vertices().get(2), Integer::compare, Integer::sum, 0);
        assertEquals(i2.toString(),"Loc ID: CT9 User ID: E4 Is HUB: true","The closest hub to vertice 2 must be CT9");
        Object i3 = closestHubToClient.getclosesthubtoclient(distributionNetwork.getGraph(), distributionNetwork.getGraph().vertices().get(10), Integer::compare, Integer::sum, 0);
        assertEquals(i3.toString(),"Loc ID: CT11 User ID: E2 Is HUB: true","The closest hub to vertice 10 must be CT11");

    }
}
