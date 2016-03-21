import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vladimirsivanovs on 21/03/2016.
 */
public class ExplorerTest{

    private class Node  implements Comparable<Node> {
        int distance;

        public Node(int distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(distance, other.distance);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "distance=" + distance +
                    '}';
        }
    }

    List<Node> list;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<>();


    }

    @Test
    public void testSorting() throws Exception {
        IntStream.range(4, 8)
                .boxed()
                .map(num -> new Node(num))
                .forEach(list::add);
        IntStream.range(1, 4)
                .boxed()
                .map(num -> new Node(num))
                .forEach(list::add);

        System.out.println(list);

        List<Node> sorted = list.stream().sorted().collect(Collectors.toList());
        System.out.println(sorted);
    }
}