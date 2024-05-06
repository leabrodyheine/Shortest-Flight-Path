package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartB_SMAStar;
import Algorithms.PartC_IDS;
import General.Node;

public class PartC_IDSTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(3, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 4;
        String frontier_result = "[(3:0)]\n"
                + "[(3:315), (3:45), (3:0), (2:0)]\n"
                + "[(1:0), (3:90), (2:315), (3:45), (2:45), (3:0), (2:0)]\n"
                + "(3:0)(3:45)(3:90)\n"
                + "4.712\n"
                + "7\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 2;
        String frontier_result = "[(3:0)]\n"
                + "[(3:0)]\n"
                + "[(3:0)]\n"
                + "fail\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(2, 90, null, 0, null);
        Node start = new Node(7, 0, null, 0, goal);
        int planetSize = 8;
        String frontier_result = "[(7:0)]\n"
                + "[(7:315), (7:45), (7:0), (6:0)]\n"
                + "[(7:90), (7:315), (6:315), (7:45), (6:45), (7:270), (7:0), (6:0), (5:0)]\n"
                + "[(6:90), (7:315), (6:315), (5:315), (7:45), (6:45), (5:45), (6:270), (7:0), (6:0), (5:0), (4:0)]\n"
                + "[(7:135), (6:135), (7:45), (6:45), (7:270), (5:45), (6:270), (4:45), (5:270), (7:180), (7:90), (6:90), (7:315), (5:90), (6:315), (5:315), (4:315), (7:0), (6:0), (7:225), (5:0), (6:225), (4:0), (3:0)]\n"
                + "[(6:135), (7:45), (5:45), (6:270), (3:45), (4:270), (6:90), (7:315), (4:90), (5:315), (3:315), (7:0), (5:0), (6:225), (3:0), (7:135), (5:135), (6:45), (7:270), (4:45), (5:270), (6:180), (7:90), (5:90), (6:315), (4:315), (6:0), (7:225), (4:0), (5:225), (2:0)]\n"
                + "[(6:135), (4:135), (7:45), (5:45), (6:270), (3:45), (4:270), (5:180), (6:90), (7:315), (4:90), (5:315), (3:315), (7:0), (5:0), (6:225), (3:0), (4:225), (1:0), (5:135), (6:45), (7:270), (4:45), (5:270), (2:45), (3:270), (7:90), (5:90), (6:315), (3:90), (4:315), (2:315), (6:0), (4:0), (5:225), (2:0)]\n"
                + "[(1:0), (2:90), (7:0), (6:0), (1:315), (5:0), (2:45), (4:0), (1:45), (3:0), (2:0)]\n"
                + "(7:0)(6:0)(5:0)(4:0)(3:0)(2:0)(2:45)(2:90)\n"
                + "8.142\n"
                + "11\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 0, null, 0, null);
        Node start = new Node(3, 180, null, 0, goal);
        int planetSize = 4;
        String frontier_result = "[(3:180)4.000]\n"
                + "[(2:180)3.000,(3:135)3.774,(3:225)3.774]\n"
                + "[(1:180)2.000,(2:135)2.798,(2:225)2.798,(3:135)3.774,(3:225)3.774]\n"
                + "[(1:135)1.848,(1:225)1.848,(2:135)2.798,(2:225)2.798,(3:135)3.774,(3:225)3.774]\n"
                + "[(1:90)1.414,(1:225)1.848,(2:135)2.798,(2:225)2.798,(3:135)3.774,(3:225)3.774]\n"
                + "[(1:45)0.765,(1:225)1.848,(2:90)2.236,(2:135)2.798,(2:225)2.798,(3:135)3.774,(3:225)3.774]\n"
                + "[(1:0)0.000,(2:45)1.474,(1:225)1.848,(2:90)2.236,(2:135)2.798,(2:225)2.798,(3:135)3.774,(3:225)3.774]\n"
                + "(3:180)(2:180)(1:180)(1:135)(1:90)(1:45)(1:0)\n"
                + "5.142\n";
        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
