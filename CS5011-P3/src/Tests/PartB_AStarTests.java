package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartA_BFS;
import Algorithms.PartB_AStar;
import General.Node;

public class PartB_AStarTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 180, null, 0, null);
        Node start = new Node(1, 90, null, 0, goal);
        int planetSize = 4;
        String frontier_result = "[(1:90)1.414]\n"
                + "[(1:135)1.551,(1:45)2.633,(2:90)3.236]\n"
                + "[(1:180)2.356,(1:45)2.633,(2:90)3.236,(2:135)4.044]\n"
                + "(1:90)(1:135)(1:180)\n"
                + "1.571\n"
                + "3\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(2, 90, null, 0, null);
        Node start = new Node(7, 0, null, 0, goal);
        int planetSize = 8;
        String frontier_result = "[(7:0)]\n"
                + "[(6:0),(7:45),(7:315)]\n"
                + "[(7:45),(7:315),(5:0),(6:45),(6:315)]\n"
                + "[(7:315),(5:0),(6:45),(6:315),(7:90)]\n"
                + "[(5:0),(6:45),(6:315),(7:90),(7:270)]\n"
                + "[(6:45),(6:315),(7:90),(7:270),(4:0),(5:45),(5:315)]\n"
                + "[(6:315),(7:90),(7:270),(4:0),(5:45),(5:315),(6:90)]\n"
                + "[(7:90),(7:270),(4:0),(5:45),(5:315),(6:90),(6:270)]\n"
                + "[(7:270),(4:0),(5:45),(5:315),(6:90),(6:270),(7:135)]\n"
                + "[(4:0),(5:45),(5:315),(6:90),(6:270),(7:135),(7:225)]\n"
                + "[(5:45),(5:315),(6:90),(6:270),(7:135),(7:225),(3:0),(4:45),(4:315)]\n"
                + "[(5:315),(6:90),(6:270),(7:135),(7:225),(3:0),(4:45),(4:315),(5:90)]\n"
                + "[(6:90),(6:270),(7:135),(7:225),(3:0),(4:45),(4:315),(5:90),(5:270)]\n"
                + "[(6:270),(7:135),(7:225),(3:0),(4:45),(4:315),(5:90),(5:270),(6:135)]\n"
                + "[(7:135),(7:225),(3:0),(4:45),(4:315),(5:90),(5:270),(6:135),(6:225)]\n"
                + "[(7:225),(3:0),(4:45),(4:315),(5:90),(5:270),(6:135),(6:225),(7:180)]\n"
                + "[(3:0),(4:45),(4:315),(5:90),(5:270),(6:135),(6:225),(7:180)]\n"
                + "[(4:45),(4:315),(5:90),(5:270),(6:135),(6:225),(7:180),(2:0),(3:45),(3:315)]\n"
                + "[(4:315),(5:90),(5:270),(6:135),(6:225),(7:180),(2:0),(3:45),(3:315),(4:90)]\n"
                + "[(5:90),(5:270),(6:135),(6:225),(7:180),(2:0),(3:45),(3:315),(4:90),(4:270)]\n"
                + "[(5:270),(6:135),(6:225),(7:180),(2:0),(3:45),(3:315),(4:90),(4:270),(5:135)]\n"
                + "[(6:135),(6:225),(7:180),(2:0),(3:45),(3:315),(4:90),(4:270),(5:135),(5:225)]\n"
                + "[(6:225),(7:180),(2:0),(3:45),(3:315),(4:90),(4:270),(5:135),(5:225),(6:180)]\n"
                + "[(7:180),(2:0),(3:45),(3:315),(4:90),(4:270),(5:135),(5:225),(6:180)]\n"
                + "[(2:0),(3:45),(3:315),(4:90),(4:270),(5:135),(5:225),(6:180)]\n"
                + "[(3:45),(3:315),(4:90),(4:270),(5:135),(5:225),(6:180),(1:0),(2:45),(2:315)]\n"
                + "[(3:315),(4:90),(4:270),(5:135),(5:225),(6:180),(1:0),(2:45),(2:315),(3:90)]\n"
                + "[(4:90),(4:270),(5:135),(5:225),(6:180),(1:0),(2:45),(2:315),(3:90),(3:270)]\n"
                + "[(4:270),(5:135),(5:225),(6:180),(1:0),(2:45),(2:315),(3:90),(3:270),(4:135)]\n"
                + "[(5:135),(5:225),(6:180),(1:0),(2:45),(2:315),(3:90),(3:270),(4:135),(4:225)]\n"
                + "[(5:225),(6:180),(1:0),(2:45),(2:315),(3:90),(3:270),(4:135),(4:225),(5:180)]\n"
                + "[(6:180),(1:0),(2:45),(2:315),(3:90),(3:270),(4:135),(4:225),(5:180)]\n"
                + "[(1:0),(2:45),(2:315),(3:90),(3:270),(4:135),(4:225),(5:180)]\n"
                + "[(2:45),(2:315),(3:90),(3:270),(4:135),(4:225),(5:180),(1:45),(1:315)]\n"
                + "[(2:315),(3:90),(3:270),(4:135),(4:225),(5:180),(1:45),(1:315),(2:90)]\n"
                + "[(3:90),(3:270),(4:135),(4:225),(5:180),(1:45),(1:315),(2:90),(2:270)]\n"
                + "[(3:270),(4:135),(4:225),(5:180),(1:45),(1:315),(2:90),(2:270),(3:135)]\n"
                + "[(4:135),(4:225),(5:180),(1:45),(1:315),(2:90),(2:270),(3:135),(3:225)]\n"
                + "[(4:225),(5:180),(1:45),(1:315),(2:90),(2:270),(3:135),(3:225),(4:180)]\n"
                + "[(5:180),(1:45),(1:315),(2:90),(2:270),(3:135),(3:225),(4:180)]\n"
                + "[(1:45),(1:315),(2:90),(2:270),(3:135),(3:225),(4:180)]\n"
                + "[(1:315),(2:90),(2:270),(3:135),(3:225),(4:180),(1:90)]\n"
                + "[(2:90),(2:270),(3:135),(3:225),(4:180),(1:90),(1:270)]\n"
                + "(7:0)(6:0)(5:0)(4:0)(3:0)(2:0)(2:45)(2:90)\n"
                + "8.142\n"
                + "43\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 0, null, 0, null);
        Node start = new Node(3, 180, null, 0, goal);
        int planetSize = 4;
        String frontier_result = "[(3:180)]\n"
                + "[(2:180),(3:135),(3:225)]\n"
                + "[(3:135),(3:225),(1:180),(2:135),(2:225)]\n"
                + "[(3:225),(1:180),(2:135),(2:225),(3:90)]\n"
                + "[(1:180),(2:135),(2:225),(3:90),(3:270)]\n"
                + "[(2:135),(2:225),(3:90),(3:270),(1:135),(1:225)]\n"
                + "[(2:225),(3:90),(3:270),(1:135),(1:225),(2:90)]\n"
                + "[(3:90),(3:270),(1:135),(1:225),(2:90),(2:270)]\n"
                + "[(3:270),(1:135),(1:225),(2:90),(2:270),(3:45)]\n"
                + "[(1:135),(1:225),(2:90),(2:270),(3:45),(3:315)]\n"
                + "[(1:225),(2:90),(2:270),(3:45),(3:315),(1:90)]\n"
                + "[(2:90),(2:270),(3:45),(3:315),(1:90),(1:270)]\n"
                + "[(2:270),(3:45),(3:315),(1:90),(1:270),(2:45)]\n"
                + "[(3:45),(3:315),(1:90),(1:270),(2:45),(2:315)]\n"
                + "[(3:315),(1:90),(1:270),(2:45),(2:315),(3:0)]\n"
                + "[(1:90),(1:270),(2:45),(2:315),(3:0)]\n"
                + "[(1:270),(2:45),(2:315),(3:0),(1:45)]\n"
                + "[(2:45),(2:315),(3:0),(1:45),(1:315)]\n"
                + "[(2:315),(3:0),(1:45),(1:315),(2:0)]\n"
                + "[(3:0),(1:45),(1:315),(2:0)]\n"
                + "[(1:45),(1:315),(2:0)]\n"
                + "[(1:315),(2:0),(1:0)]\n"
                + "[(2:0),(1:0)]\n"
                + "[(1:0)]\n"
                + "(3:180)(2:180)(1:180)(1:135)(1:90)(1:45)(1:0)\n"
                + "5.142\n"
                + "24\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(0, 0, null, 0, null);
        Node start = new Node(1, 90, null, 0, goal);
        int planetSize = 2;
        String frontier_result = "[(1:90)1.000]\n"
                + "[(1:45)1.785,(1:135)1.785]\n"
                + "[(1:135)1.785,(1:0)3.356]\n"
                + "[(1:0)3.356,(1:180)3.356]\n"
                + "[(1:180)3.356,(1:315)5.712]\n"
                + "[(1:225)5.712,(1:315)5.712]\n"
                + "[(1:315)5.712,(1:270)8.854]\n"
                + "[(1:270)8.854]\n"
                + "fail\n"
                + "8\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(4, 90, null, 0, null);
        Node start = new Node(1, 90, null, 0, goal);
        int planetSize = 2;
        String frontier_result = "[(1:90)3.000]\n"
                + "[(1:45)4.153,(1:135)4.153]\n"
                + "[(1:135)4.153,(1:0)6.479]\n"
                + "[(1:0)6.479,(1:180)6.479]\n"
                + "[(1:180)6.479,(1:315)9.472]\n"
                + "[(1:225)9.472,(1:315)9.472]\n"
                + "[(1:315)9.472,(1:270)12.854]\n"
                + "[(1:270)12.854]\n"
                + "fail\n"
                + "8\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testStartOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(4, 90, null, 0, goal);
        int planetSize = 2;
        String frontier_result = "[(4:90)3.000]\n"
                + "fail\n"
                + "1\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
