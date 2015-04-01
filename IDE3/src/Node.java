

import java.util.ArrayList;

/*
 * Class with node features
 */
public class Node {

    private String identifier;
    private ArrayList<String> children;

    // Constructor
    public Node(String identifier) {
        this.identifier = identifier;
        children = new ArrayList<String>();
    }

    // Properties
    public String getIdentifier() {
        return identifier;
    }

    // Node children
    public ArrayList<String> getChildren() {
        return children;
    }

    // Public interface
    public void addChild(String identifier) {
        children.add(identifier);
    }
}