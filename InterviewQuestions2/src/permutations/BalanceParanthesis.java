package permutations;

/**
 * Given a string (()) Determine whether its balanced etc.
 * TODO This is incomplete.
 */
public class BalanceParanthesis {

    // (()

    public boolean isBalanced(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }

        int openParnthesis = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                openParnthesis++;
            } else if (str.charAt(i) == ')') {
                openParnthesis--;
            }
            if (openParnthesis < 0) {
                return false;
            }
        }

        if (openParnthesis > 0) {
            return false;
        }

        return true;
    }
}
