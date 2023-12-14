package edu.hw10.Task2Test;

import edu.hw10.Task2.Cache;

public interface Sum {
    @Cache(persist = false)
    int sum(int num);
}
