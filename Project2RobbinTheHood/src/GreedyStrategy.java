import java.util.*;

/**
 * A greedy algorithm to select vertices for robbing.
 */
public class GreedyStrategy implements RobbingStrategy {

    @Override
    public List<String> chooseOrderToAttack(LabeledValueGraph graph) {

        List<String> vertices = graph.getAllVertexLabels();
        Map<Integer, List<String>> valueGold = new TreeMap<>(Collections.reverseOrder());
        Map<String, Integer> attackedMap = new LinkedHashMap<>();
        Set<String> alertedSet = new LinkedHashSet<>();

        // Reverse order sort
        for (String vertex : vertices) {
            int gold = graph.getValueAt(vertex);
            valueGold.putIfAbsent(gold, new ArrayList<>());
            valueGold.get(gold).add(vertex);
        }

        // Include # fort first in the map
        for (String vertex : vertices) {
            if (vertex.contains("#")) {
                attackedMap.put(vertex, graph.getValueAt(vertex));
                List<String> neighbors = graph.adj(vertex);
            }
        }

        for (Map.Entry<Integer, List<String>> entry : valueGold.entrySet()) {
            for (String vertex : entry.getValue()) {
                if (attackedMap.containsKey(vertex)) continue;

                int vertexValue = graph.getValueAt(vertex);

                if (!vertex.contains("!") && !vertex.contains("*") && !alertedSet.contains(vertex)) {
                    attackedMap.put(vertex, vertexValue);
                    List<String> neighbors = graph.adj(vertex);
                    for (String neighbor : neighbors) {
                        if (!attackedMap.containsKey(neighbor)) {
                            alertedSet.add(neighbor);
                        }
                    }
                }
            }
        }

        for (String newVertex:graph.getAllVertexLabels() ) {
            if (!attackedMap.containsKey(newVertex)){
                attackedMap.put(newVertex, graph.getValueAt(newVertex));
            }
        }


        return new ArrayList<>(attackedMap.keySet());
    }
}