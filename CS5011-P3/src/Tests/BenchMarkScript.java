package Tests;

import General.Node;
import Algorithms.PartA_BFS;
import Algorithms.PartA_DFS;
import Algorithms.PartB_AStar;
import Algorithms.PartB_BestF;
import Algorithms.PartB_SMAStar;
import Algorithms.PartC_IDS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenchMarkScript {

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("BenchMarkOutput.txt");
        List<Long> bfs_obs = new ArrayList<Long>();
        List<Long> dfs_obs = new ArrayList<Long>();
        List<Long> astar_obs = new ArrayList<Long>();
        List<Long> bestf_obs = new ArrayList<Long>();
        List<Long> smastar_obs = new ArrayList<Long>();
        List<Long> ids_obs = new ArrayList<Long>();


        Random rand = new Random();
        int planetSize = 20;
        int memorySize = planetSize;

        for (int i = 0; i < 500; i++) {
            int goal_d = rand.nextInt(planetSize) + 1;
            int start_d = rand.nextInt(planetSize) + 1;
            int goal_a = rand.nextInt(8) * 45;
            int start_a = rand.nextInt(8) * 45;

            Node goal = new Node(goal_d, goal_a, null, 0, null);
            Node start = new Node(start_d, start_a, null, 0, goal);

            long startTime = System.nanoTime();
            PartA_DFS.dfs(start, goal, planetSize);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            dfs_obs.add(duration);

            startTime = System.nanoTime();
            PartA_BFS.bfs(start, goal, planetSize);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            bfs_obs.add(duration);

            startTime = System.nanoTime();
            PartB_BestF.BestF(start, goal, planetSize);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            bestf_obs.add(duration);

            startTime = System.nanoTime();
            PartB_AStar.AStar(start, goal, planetSize);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            astar_obs.add(duration);

            startTime = System.nanoTime();
            PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            smastar_obs.add(duration);

            startTime = System.nanoTime();
            PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            ids_obs.add(duration);
        }
        writer.write("dfs: " + dfs_obs + "\n");
        writer.write("bfs: " + bfs_obs + "\n");
        writer.write("astar: " + astar_obs + "\n");
        writer.write("bestf: " + bestf_obs + "\n");
        writer.write("smastar: " + smastar_obs + "\n");
        writer.write("ids: " + ids_obs + "\n");
        writer.close();
    }
}
