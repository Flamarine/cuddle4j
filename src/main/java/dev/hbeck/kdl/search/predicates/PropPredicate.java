package dev.hbeck.kdl.search.predicates;

import dev.hbeck.kdl.objects.KDLNode;
import dev.hbeck.kdl.objects.KDLValue;

import java.util.function.Predicate;

/**
 * Predicate matching a KDLNode property
 */
public record PropPredicate(Predicate<String> keyPredicate,
                            Predicate<KDLValue> valuePredicate) implements NodeContentPredicate {

    @Override
    public boolean test(KDLNode node) {
        for (String key : node.props().keySet()) {
            if (keyPredicate.test(key) && valuePredicate.test(node.props().get(key))) {
                return true;
            }
        }

        return false;
    }
}
