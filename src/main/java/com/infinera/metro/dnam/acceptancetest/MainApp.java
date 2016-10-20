package com.infinera.metro.dnam.acceptancetest;

import com.infinera.metro.dnam.acceptancetest.testimplementation.DnamNodesTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(DnamNodesTest.class.getName());
    }
}
