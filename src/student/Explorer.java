package student;

import game.*;

import java.util.*;
import java.util.stream.Collectors;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        dfs(state); //depth first search
    }



    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */
    public void escape(EscapeState state) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> marked = new HashSet<>();
        Map<Node, Node> predecessors = new HashMap<>();
        Map<Node, Integer> distance = new HashMap<>();
        Node start = state.getCurrentNode();
        Node exit = state.getExit();

        for (Node n : state.getVertices()) {
            if (n == start) {
                predecessors.put(n, start);
                distance.put(n, 0);
                marked.add(n);
            } else {
                predecessors.put(n, null);
                distance.put(n, Integer.MAX_VALUE);
            }
            queue.add(n);


        }

        while (!queue.isEmpty()) {
            Node node = queue.remove();
            for (Node n : node.getNeighbours()) {
                if (!marked.contains(n)) {
                    distance.put(n, distance.get(node) + node.getEdge(n).length());
                    predecessors.put(n, node);
                    marked.add(n);
                    queue.add(n);
                }
            }
        }

        Stack<Node> path = getPath(exit, start, marked, predecessors);

        goToExit(path, state);

    }

    public Stack<Node> getPath(Node exit, Node start, Set<Node> marked, Map<Node, Node> predecessors) {
        if (!marked.contains(exit)) return null;

        Stack<Node> shortestPath = new Stack<Node>();
        Node node;
        for (node = exit; !node.equals(start); node = predecessors.get(node)){
            shortestPath.push(node);
        }
        return shortestPath;
    }

    private void goToExit(Stack<Node> path, EscapeState state) {
        while (!path.empty()) {
            state.moveTo(path.pop());
        }
    }

    /**
     * Depth first search algorithm to find Orb.
     *
     * @param state
     */
    private void dfs(ExplorationState state) {
        Set<NodeStatus> visited = new HashSet<>();
        Stack<NodeStatus> nodes_to_be_visited = new Stack<>();

        NodeStatus firstNode = state.getNeighbours().stream().findFirst().get();
        visited.add(firstNode);
        nodes_to_be_visited.push(firstNode);

        while (!nodes_to_be_visited.isEmpty()){
            NodeStatus next = nodes_to_be_visited.peek();
            state.moveTo(next.getId());
            visited.add(next); //mark node as visited

            //pick the Orb
            if(state.getDistanceToTarget() == 0) return;


            List<NodeStatus> nextTiles = state.getNeighbours().stream()
                    .filter(node -> !visited.contains(node))
                    .sorted() //sort by distance
                    .collect(Collectors.toList());


            if (nextTiles.isEmpty()) {
                visited.add(nodes_to_be_visited.pop());
            } else {
                nodes_to_be_visited.push(nextTiles.get(0)); //select first node with minimal distance
            }
        }
    }

}
