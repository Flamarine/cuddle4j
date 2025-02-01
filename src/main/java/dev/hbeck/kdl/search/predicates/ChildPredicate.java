package dev.hbeck.kdl.search.predicates;

import dev.hbeck.kdl.objects.KDLNode;
import dev.hbeck.kdl.search.Search;

import java.util.Optional;

/**
 * Matches nodes based on the contents, or absence, of a child
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ChildPredicate implements NodeContentPredicate {
    private final Optional<Search> search;

    public ChildPredicate(Optional<Search> search) {
        this.search = search;
    }

    @Override
    public boolean test(KDLNode node) {
        return search.map(value -> node.child()
                .map(value::anyMatch)
                .orElse(false)
        ).orElseGet(() -> node.child().isEmpty() || node.child().get().nodes().isEmpty());
    }

    public static ChildPredicate empty() {
        return new ChildPredicate(Optional.empty());
    }
}
