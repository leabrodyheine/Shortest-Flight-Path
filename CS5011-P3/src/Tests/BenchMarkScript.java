package Tests;

import General.Node;
import Algorithms.PartA_BFS;
import Algorithms.PartA_DFS;
import Algorithms.PartB_AStar;
import Algorithms.PartB_BestF;
import Algorithms.PartB_SMAStar;
import Algorithms.PartC_IDS;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class BenchMarkScript {

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("BenchMarkOutput.txt");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        List<Long> bfsTimes = new ArrayList<>();
        List<Double> bfsCosts = new ArrayList<>();

        List<Long> dfsTimes = new ArrayList<>();
        List<Double> dfsCosts = new ArrayList<>();

        List<Long> astarTimes = new ArrayList<>();
        List<Double> astarCosts = new ArrayList<>();

        List<Long> bestfTimes = new ArrayList<>();
        List<Double> bestfCosts = new ArrayList<>();

        List<Long> smastarTimes = new ArrayList<>();
        List<Double> smastarCosts = new ArrayList<>();

        List<Long> idsTimes = new ArrayList<>();
        List<Double> idsCosts = new ArrayList<>();

        Random rand = new Random();
        int planetSize = 15;
        int memorySize = planetSize * 3;

        for (int i = 0; i < 100; i++) {
            int goal_d = rand.nextInt(planetSize) + 1;
            int start_d = rand.nextInt(planetSize) + 1;
            int goal_a = rand.nextInt(8) * 45;
            int start_a = rand.nextInt(8) * 45;

            Node goal = new Node(goal_d, goal_a, null, 0, null);
            Node start = new Node(start_d, start_a, null, 0, goal);

            long startTime = System.nanoTime();
            PartA_BFS.bfs(start, goal, planetSize);
            long endTime = System.nanoTime();
            bfsTimes.add(endTime - startTime);
            bfsCosts.add(parseCost(bos.toString()));
            bos.reset();

            startTime = System.nanoTime();
            PartA_DFS.dfs(start, goal, planetSize);
            endTime = System.nanoTime();
            dfsTimes.add(endTime - startTime);
            dfsCosts.add(parseCost(bos.toString()));
            bos.reset();

            startTime = System.nanoTime();
            PartB_AStar.AStar(start, goal, planetSize);
            endTime = System.nanoTime();
            astarTimes.add(endTime - startTime);
            astarCosts.add(parseCost(bos.toString()));
            bos.reset();

            startTime = System.nanoTime();
            PartB_BestF.BestF(start, goal, planetSize);
            endTime = System.nanoTime();
            bestfTimes.add(endTime - startTime);
            bestfCosts.add(parseCost(bos.toString()));
            bos.reset();

            // startTime = System.nanoTime();
            // PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
            // endTime = System.nanoTime();
            // idsTimes.add(endTime - startTime);
            // idsCosts.add(parseCost(bos.toString()));
            // bos.reset();

            startTime = System.nanoTime();
            PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
            endTime = System.nanoTime();
            smastarTimes.add(endTime - startTime);
            smastarCosts.add(parseCost(bos.toString()));
            bos.reset();
        }
        System.setOut(originalOut);
        writer.write("BFS Times: " + bfsTimes + "\nBFS Costs: " + bfsCosts + "\n");
        writer.write("DFS Times: " + dfsTimes + "\nDFS Costs: " + dfsCosts + "\n");
        writer.write("AStar Times: " + astarTimes + "\nAStar Costs: " + astarCosts + "\n");
        writer.write("BestF Times: " + bestfTimes + "\nBestF Costs: " + bestfCosts + "\n");
        // writer.write("IDS Times: " + idsTimes + "\nIDS Costs: " + idsCosts + "\n");
        writer.write("SMAStar Times: " + smastarTimes + "\nIDS Costs: " + smastarCosts + "\n");
        writer.close();
    }

    private static double parseCost(String output) {
        Pattern pattern = Pattern.compile("\\((\\d+:\\d+)\\)\\n(\\d+\\.\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(output);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(2)); // Get the cost from the regex group
        }
        return 0.0; // Return 0 or some error value if not found
    }
}
