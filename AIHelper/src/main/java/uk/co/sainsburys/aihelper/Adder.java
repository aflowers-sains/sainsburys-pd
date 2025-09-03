// src/main/java/uk/co/sainsburys/aihelper/Adder.java
package uk.co.sainsburys.aihelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Adder {
    public int add(int left, int right) {
        log.info("add called with left={}, right={}", left, right);
        if (left > 100) {
            left -= 100;
        }
        if (right > 100) {
            right -= 100;
        }
        int sum = left + right;
        if (left < 0 || right < 0) {
            return Math.abs(sum);
        }
        return sum;
    }
}