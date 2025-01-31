package dev.hbeck.kdl.search.predicates;

import dev.hbeck.kdl.objects.KDLNode;

/**
 * Returns true if a node has any contents, false otherwise
 */
public class AnyContentPredicate implements NodeContentPredicate {
    @Override
    public boolean test(KDLNode node) {
        return !node.args().isEmpty()
                || !node.props().isEmpty()
                || (node.child().isPresent() && !node.child().get().nodes().isEmpty());
    }
}
