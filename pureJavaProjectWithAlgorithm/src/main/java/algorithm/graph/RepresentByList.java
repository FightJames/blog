package algorithm.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class RepresentByList {
    ArrayList<Node> allNode;
    // Each element present a node and store a list which present its neighbor node.
    LinkedList<Node>[] adjacencyList;
    List<Queue<Node>> layer;

    public void prepareGraph() {
        allNode = new ArrayList<Node>();
        for (int i = 0; i < 8; i++) {
            Node node = new Node(i + 1);
            allNode.add(node);
        }

        // init node neighbor
        adjacencyList = new LinkedList[9];

        LinkedList<Node> element = new LinkedList();
        addNodeToList(element, 2, 3);
        adjacencyList[1] = element;

        element = new LinkedList();
        addNodeToList(element, 1, 3, 4, 5);
        adjacencyList[2] = element;

        element = new LinkedList();
        addNodeToList(element, 1, 2, 5, 7, 8);
        adjacencyList[3] = element;

        element = new LinkedList();
        addNodeToList(element, 2, 5);
        adjacencyList[4] = element;

        element = new LinkedList();
        addNodeToList(element, 2, 3, 4, 6);
        adjacencyList[5] = element;

        element = new LinkedList();
        addNodeToList(element, 5);
        adjacencyList[6] = element;

        element = new LinkedList();
        addNodeToList(element, 3, 8);
        adjacencyList[7] = element;

        element = new LinkedList();
        addNodeToList(element, 3, 7);
        adjacencyList[8] = element;

    }

    public void addNodeToList(LinkedList<Node> list, int... par) {
        for (int i = 0; i < par.length; i++) {
            list.add(allNode.get(par[i] - 1));
        }
    }

    public Queue<Node> addNodeToLayerQueue(int... par) {
        Queue<Node> queue = new LinkedList();
        for (int i = 0; i < par.length; i++) {
            queue.add(allNode.get(par[i] - 1));
        }
        return queue;
    }

    public void BFS() {
        boolean[] discovered = new boolean[allNode.size()];
        layer = new ArrayList();
        layer.add(addNodeToLayerQueue(1));
        int i = 0;
        while (layer.get(i) != null && !layer.get(i).isEmpty()) {
            Queue<Node> currentQueue = layer.get(i);
            Queue<Node> queue = new LinkedList();
            layer.add(queue);
            System.out.println("Next layer : " + (i + 1));
            currentQueue.forEach(new Consumer<Node>() {
                @Override
                public void accept(Node node) {
                    System.out.println("current Node : " + node.val);
                    if (discovered[node.val - 1] == false) {
                        discovered[node.val - 1] = true;
                        List list = adjacencyList[node.val];
                        Iterator<Node> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            Node nodeb = iterator.next();
                            if (discovered[nodeb.val - 1] == false && !queue.contains(nodeb)
                                    && !currentQueue.contains(nodeb)) {
                                queue.add(nodeb);
                            }
                            System.out.println("neighbor : " + nodeb.val);
                            System.out.println("discovered : " + discovered[nodeb.val - 1]);
                        }
                        System.out.println("neighbor=====");
                    }
                }
            });
            i++;
        }
        for (int j = 0; j < layer.size(); j++) {
            Queue<Node> queue = layer.get(j);
            queue.forEach(new Consumer<Node>() {
                @Override
                public void accept(Node t) {
                    System.out.print(t.val + " ");
                }
            });
            System.out.println();
            System.out.println("==" + j + "==");
        }
    }

    public static void main(String[] argu) {
        RepresentByList rr = new RepresentByList();
        rr.prepareGraph();
        rr.BFS();
    }
}

