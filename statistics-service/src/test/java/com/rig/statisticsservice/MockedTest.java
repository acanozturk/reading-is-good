package com.rig.statisticsservice;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class MockedTest {
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
}
