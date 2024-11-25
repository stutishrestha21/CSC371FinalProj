import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {


    public static void main(String args[]) throws FileNotFoundException {

        String inputFile = "samples/tree8.graph";
//        String inputFile = "samples/sherwood_forest.graph";
//        String inputFile = "samples/random1000.graph";
        LabeledValueGraph graph = new LabeledValueGraph(inputFile);


        List<RobbingStrategy> strategies = new ArrayList<>();
        strategies.add(new RandomStrategy());
        strategies.add(new BruteForceStrategy());
//        strategies.add(new GreedyStrategy());
//        strategies.add(new DPStrategy());

        for (RobbingStrategy strategy : strategies) {
            testStrategy(strategy, graph);
        }

    }

    public static void testStrategy(RobbingStrategy strategy, LabeledValueGraph originalGraph) {
        //make a copy to pass to the strategy, in case the strategy modifies the graph in some way
        LabeledValueGraph copy = new LabeledValueGraph(originalGraph);
        List<String> fortAttackOrdering = strategy.chooseOrderToAttack(copy);

        double gold = AttackValueVerifier.computeGoldForAttackOrdering(originalGraph, fortAttackOrdering, false);
        System.out.println(gold + " stolen by " + strategy.getClass().getSimpleName());
        System.out.println("   using order: " + fortAttackOrdering);
    }

}
