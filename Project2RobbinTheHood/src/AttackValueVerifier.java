import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttackValueVerifier {

    public static double computeGoldForAttackOrdering(LabeledValueGraph graph, List<String> attackOrdering) {
        return computeGoldForAttackOrdering(graph, attackOrdering, false);
    }

    public static double computeGoldForAttackOrdering(LabeledValueGraph graph, List<String> attackOrdering, boolean printDebugInfo) {

        double totalGold = 0;
        Set<String> highAlertForts = new HashSet<>();
        for (String fortName : attackOrdering) {
            double goldHere = graph.getValueAt(fortName);
            if (fortName.contains("!")) {
                highAlertForts.add(fortName);
            }
            if (highAlertForts.contains(fortName) && !fortName.contains("*")) {
                goldHere = goldHere / 2.0;
            }
            if (printDebugInfo) {
                System.out.println("Stole " + goldHere + " from " + fortName);
            }
            totalGold += goldHere;
            if (!fortName.contains("#")) {
                List<String> neighbors = graph.adj(fortName);
                highAlertForts.addAll(neighbors);
            }
        }
        return totalGold;
    }
}
