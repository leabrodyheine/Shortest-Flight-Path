import matplotlib.pyplot as plt
import ast

with open(
    "/Users/leabrody-heine2/Desktop/5011/5011-P3/CS5011-P3/src/BenchMarkOutput.txt", "r"
) as f:
    lines = f.readlines()
    bfs_list = ast.literal_eval(lines[0][11:])
    bfs_costs = ast.literal_eval(lines[1][11:])
    dfs_list = ast.literal_eval(lines[2][11:])
    dfs_costs = ast.literal_eval(lines[3][11:])
    astar_list = ast.literal_eval(lines[4][13:])
    astar_costs = ast.literal_eval(lines[5][13:])
    bestf_list = ast.literal_eval(lines[6][13:])
    bestf_costs = ast.literal_eval(lines[7][13:])
    ids_list = ast.literal_eval(lines[8][11:])
    ids_costs = ast.literal_eval(lines[9][11:])
    smastar_list = ast.literal_eval(lines[10][15:])
    smastar_costs = ast.literal_eval(lines[11][15:])
    print(smastar_costs)


data = {
    "DFS": dfs_list[10:],
    "BFS": bfs_list[10:],
    "A*": astar_list[10:],
    "BestF": bestf_list[10:],
    'SMA*': smastar_list[10:],
    "IDS": ids_list[10:],
}

costs = {
    "DFS": dfs_costs,
    "BFS": bfs_costs,
    "A*": astar_costs,
    "BestF": bestf_costs,
    'SMA*': smastar_costs,
    "IDS": ids_costs,
}

# Colors for each algorithm
colors = ['red', 'xkcd:sky blue', 'mediumseagreen', 'xkcd:lavender', 'orange']

# Plotting runtimes with custom colors
fig1, ax1 = plt.subplots()
bp1 = ax1.boxplot(data.values(), patch_artist=True, showfliers=False)
for patch, color in zip(bp1['boxes'], colors):
    patch.set_facecolor(color)
ax1.set_xticklabels(data.keys())
ax1.set_title('Algorithm Runtimes')
ax1.set_ylabel('Runtime (nanoseconds)')
plt.show()

fig2, ax2 = plt.subplots()
bp2 = ax2.boxplot(costs.values(), patch_artist=True, showfliers=False)
for patch, color in zip(bp2['boxes'], colors):
    patch.set_facecolor(color)
ax2.set_xticklabels(costs.keys())
ax2.set_title('Algorithm Costs')
ax2.set_ylabel('Cost')
plt.show()