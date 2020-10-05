/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import org.junit.Test;

public class WordNetTestU {
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCycle3() {
        WordNet net = new WordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCycle6() {
        WordNet net = new WordNet("synsets6.txt", "hypernyms6InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTwoRoots3() {
        WordNet net = new WordNet("synsets3.txt", "hypernyms3InvalidTwoRoots.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTwoRoots6() {
        WordNet net = new WordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCyclePath6() {
        WordNet net = new WordNet("synsets6.txt", "hypernyms6InvalidCycle+Path.txt");
    }
}
