import java.util.List;

public interface SearchStrategy {
    List<Node> search(Node start, Node goal, int planetSize);
}
