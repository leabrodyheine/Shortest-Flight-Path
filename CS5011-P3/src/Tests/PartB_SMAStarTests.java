package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartB_SMAStar;
import General.Node;

public class PartB_SMAStarTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(1:0)1.414]\n"
                + "[(1:45)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testBasicPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 4;
        String frontier_result = "[(1:0)1.414]\n"
                + "[(1:45)1.551,(1:315)2.633]\n"
                + "[(1:90)1.571,(1:315)2.633,(1:0)2.985]\n"
                + "(1:0)(1:45)(1:90)\n"
                + "1.571\n"
                + "3\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(2, 90, null, 0, null);
        Node start = new Node(7, 0, null, 0, goal);
        int planetSize = 8;
        int memorySize = 9;
        String frontier_result = "[(7:0)7.280]\n"
                + "[(6:0)7.325,(7:45)11.260,(7:315)14.030]\n"
                + "[(5:0)7.385,(7:0)9.280,(6:45)10.511,(7:45)11.260,(6:315)13.260,(7:315)14.030]\n"
                + "[(4:0)7.472,(7:0)9.280,(6:0)9.325,(5:45)9.782,(6:45)10.511,(7:45)11.260,(5:315)12.495,(6:315)13.260,(7:315)14.030]\n"
                + "[(3:0)7.606,(4:45)9.089,(7:0)9.280,(6:0)9.325,(5:0)9.385,(5:45)9.782,(6:45)10.511,(7:45)11.260,(4:315)11.737]\n"
                + "[(2:0)7.828,(3:45)8.481,(4:45)9.089,(7:0)9.280,(6:0)9.325,(5:0)9.385,(4:0)9.472,(5:45)9.782,(6:45)10.511]\n"
                + "[(2:45)8.102,(1:0)8.236,(3:45)8.481,(4:45)9.089,(7:0)9.280,(6:0)9.325,(5:0)9.385,(4:0)9.472,(3:0)9.606]\n"
                + "[(2:90)8.142,(1:0)8.236,(1:45)9.044,(4:45)9.089,(7:0)9.280,(6:0)9.325,(5:0)9.385,(4:0)9.472,(3:45)9.696]\n"
                + "(7:0)(6:0)(5:0)(4:0)(3:0)(2:0)(2:45)(2:90)\n"
                + "8.142\n"
                + "8\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 0, null, 0, null);
        Node start = new Node(3, 180, null, 0, goal);
        int planetSize = 4;
        int memorySize = 8;
        String frontier_result = "[(3:180)4.000]\n"
                + "[(2:180)4.000,(3:135)6.130,(3:225)6.130]\n"
                + "[(1:180)4.000,(2:135)5.369,(2:225)5.369,(3:180)6.000,(3:135)6.130,(3:225)6.130]\n"
                + "[(1:135)4.633,(1:225)4.633,(2:135)5.369,(2:225)5.369,(2:180)6.000,(3:180)6.000,(3:135)6.130,(3:225)6.130]\n"
                + "[(1:225)4.633,(1:90)4.985,(2:225)5.369,(1:180)5.571,(2:180)6.000,(3:180)6.000,(3:225)6.130,(2:135)6.583]\n"
                + "[(1:90)4.985,(1:270)4.985,(1:180)5.571,(1:180)5.571,(2:180)6.000,(3:180)6.000,(2:135)6.583,(2:225)6.583]\n"
                + "[(1:270)4.985,(1:45)5.122,(1:180)5.571,(1:180)5.571,(2:180)6.000,(3:180)6.000,(2:135)6.583,(2:225)6.583]\n"
                + "[(1:45)5.122,(1:315)5.122,(1:180)5.571,(1:180)5.571,(2:180)6.000,(3:180)6.000,(2:135)6.583,(2:225)6.583]\n"
                + "[(1:315)5.122,(1:0)5.142,(1:180)5.571,(1:180)5.571,(2:180)6.000,(3:180)6.000,(2:135)6.583,(2:225)6.583]\n"
                + "[(1:0)5.142,(1:0)5.142,(1:180)5.571,(1:180)5.571,(2:180)6.000,(3:180)6.000,(2:135)6.583,(2:225)6.583]\n"
                + "(3:180)(2:180)(1:180)(1:135)(1:90)(1:45)(1:0)\n"
                + "5.142\n"
                + "10\n";
        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(0, 0, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(1:0)1.000]\n"
                + "[(1:45)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(3:0)3.162]\n"
                + "fail\n"
                + "1\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(4, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(1:0)4.123]\n"
                + "[(1:45)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds3() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(4, 90, null, 0, null);
        Node start = new Node(4, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(4:0)5.657]\n"
                + "fail\n"
                + "1\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testMemoryConstraint() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(2, 180, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 3;
        int memorySize = 2;
        String frontier_result = "[(1:0)3.000]\n"
                + "[(2:0)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }
}