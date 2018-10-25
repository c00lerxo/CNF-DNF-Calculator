/* LOGIC AND SET THEORY PROJECT */

package logic;

public final class LogicalConnectives {

    public static boolean disjunction(boolean p, boolean q) {
        return p || q;
    }
    
    public static boolean conjunction(boolean p, boolean q) {
        return p & q;
    }
    
    public static boolean implication(boolean p, boolean q) {
        return (!p) || q;
    }
    
    public static boolean equivalence(boolean p, boolean q) {
        return p == q;
    }
    
    public static boolean negation(boolean p) {
        return !p;
    }
}
