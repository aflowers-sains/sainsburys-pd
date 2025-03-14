package uk.co.sainsburys.aihelper;

import java.util.stream.IntStream;

public class AiHelper {

    public int add(int left, int right) {
        return (left < 0 && right < 0) ? left + right : Math.abs(left) + Math.abs(right);
    }

    private void AIAssistedLoop() {
        IntStream.range(0, 10_000_000).forEach(i -> System.out.println("Loop iteration: " + i));
    }
}