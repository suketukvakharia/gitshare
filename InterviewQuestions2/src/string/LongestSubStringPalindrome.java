package string;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Longest Palindromic Substring | Set 1
 * https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/
 * <p>
 * Given a string, find the longest substring which is palindrome. For example, if the given string is
 * “forgeeksskeegfor”, the output should be “geeksskeeg”.
 * <p>
 * There are three ways of solving this problem and we will solve it all three ways.
 */
public class LongestSubStringPalindrome {

    /**
     * Brute force implementation at O(N^3)
     * <p>
     * This implementation simply checks if each substring of the given string is a palindrome or not.
     *
     * @param str input string
     * @return
     */
    private char[] longestSubStrigPalindromeBruteForce(char[] str) {
        if (str == null || str.length == 1) {
            return str;
        }
        char[] longestPalindrome = new char[]{};

        // sub string combinations
        for (int i = 0; i < str.length; i++) {
            for (int j = str.length - 1; j >= i; j--) {

                int currentI = i, currentJ = j;
                while (currentI < str.length && currentJ >= 0 && str[currentI] == str[currentJ]) {
                    currentI++;
                    currentJ--;
                }

                if (currentI > currentJ && (j - i + 1) > longestPalindrome.length) {
                    longestPalindrome = Arrays.copyOfRange(str, i, j + 1);
                }
            }
        }

        System.err.println("returning:" + Arrays.toString(longestPalindrome));
        return longestPalindrome;
    }

    /**
     * This is a O(N^2) DP solution.
     * Detailed explanation of the solution is documented here:
     * https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/
     * <p>
     * Below is an independent solution development for this.
     *
     * @param str
     * @return
     */
    private char[] longestSubstringDP(char[] str) {
        if (str == null || str.length == 1) {
            return str;
        }

        // build a grid signifying largest palindrome from i to j
        // The value of -1 means palindrome not possible, 0 means palindrome possible, positive value means palindrome
        // of the given length.
        // once we find such value we store it.

        // first character default for now
        char[] longestPalindrome = Arrays.copyOfRange(str, 0, 1);

        int[][] grid = new int[str.length][str.length];
        // init diagonal
        for (int i = 0, j = 0; i < str.length; i++, j++) {
            grid[i][j] = 1;
        }
        // init pre-diag
        for (int i = 1, j = 0; i < str.length; i++, j++) {
            grid[i][j] = 1;
        }

        // iterate over the top grid
        for (int delta = 1; delta < str.length; delta++) {
            for (int j = 0; j + delta < str.length; j++) {
                // delta, j + delta
                // if characters equal and previous (down & left element in grid) was > 0 then this is
                // continuing palindrome match.
                if (str[j] == str[j + delta] && grid[j + 1][j + delta - 1] > 0) {
                    if (delta + 1 > longestPalindrome.length) {
                        longestPalindrome = Arrays.copyOfRange(str, j, j + delta + 1);
                    }
                    grid[j][j + delta] = 1;
                } else {
                    grid[j][j + delta] = -1;
                }
            }
        }

        // for debugging only
//        for (int[] gridElement : grid) {
//            System.err.println(Arrays.toString(gridElement));
//        }
        return longestPalindrome;
    }

    /**
     * The optimal solution here simply starts from every point in the string and tries to expand to largest
     * palindrom definition that it can find.
     * This is described in further detail here: geeksforgeeks.org/longest-palindromic-substring-set-2/
     *
     * @param str
     * @return
     */
    private char[] longestSubstringOptimal(char[] str) {
        if (str == null || str.length == 1) {
            return str;
        }

        char[] longestSubstringPalindrome = Arrays.copyOfRange(str, 0, 1);

        // check from every character if there is a larger palindrome
        for (int i = 1; i < str.length; i++) {
            // for odd numbered palindromes we look at i+j & i-j
            for (int j = 1; i - j > 0 && i + j < str.length; j++) {
                // i-j, i+j
                if (str[i - j] == str[i + j]) {
                    if (2 * j + 1 > longestSubstringPalindrome.length) {
                        longestSubstringPalindrome = Arrays.copyOfRange(str, i - j, i + j + 1);
                    }
                } else {
                    break;
                }
            }

            // for even numbered palindromes we look at i-j, i+j-1
            for (int j = 1; i - j > 0 && i + j - 1 < str.length; j++) {
                // i-j, i+j-1
                if (str[i - j] == str[i + j - 1]) {
                    if (2 * j > longestSubstringPalindrome.length) {
                        longestSubstringPalindrome = Arrays.copyOfRange(str, i - j, i + j);
                    }
                } else {
                    break;
                }

            }
        }
        return longestSubstringPalindrome;
    }

    @Test
    public void testLongestSubstringPalindrome() {
        assertArrayEquals("ee".toCharArray(), longestSubStrigPalindromeBruteForce("geeks".toCharArray()));
        assertArrayEquals("eee".toCharArray(), longestSubStrigPalindromeBruteForce("geeeks".toCharArray()));
        assertArrayEquals("isasi".toCharArray(), longestSubStrigPalindromeBruteForce("geeeks isasi".toCharArray()));
        assertArrayEquals("asiisa".toCharArray(), longestSubStrigPalindromeBruteForce("geeeks isasiisa".toCharArray()));

        assertArrayEquals("ee".toCharArray(), longestSubstringDP("geeks".toCharArray()));
        assertArrayEquals("eee".toCharArray(), longestSubstringDP("geeeks".toCharArray()));
        assertArrayEquals("isasi".toCharArray(), longestSubstringDP("geeeks isasi".toCharArray()));
        assertArrayEquals("asiisa".toCharArray(), longestSubstringDP("geeeks isasiisa".toCharArray()));

        assertArrayEquals("ee".toCharArray(), longestSubstringOptimal("geeks".toCharArray()));
        assertArrayEquals("eee".toCharArray(), longestSubstringOptimal("geeeks".toCharArray()));
        assertArrayEquals("isasi".toCharArray(), longestSubstringOptimal("geeeks isasi".toCharArray()));
        assertArrayEquals("asiisa".toCharArray(), longestSubstringOptimal("geeeks isasiisa".toCharArray()));

    }
}
