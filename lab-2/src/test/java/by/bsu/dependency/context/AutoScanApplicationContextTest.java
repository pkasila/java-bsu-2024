package by.bsu.dependency.context;

import by.bsu.dependency.exceptions.NoSuchBeanDefinitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AutoScanApplicationContextTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void init() {
        applicationContext = new AutoScanApplicationContext("by.bsu.dependency.example");
    }

    @Test
    void testScannedPackage() {
        applicationContext.start();

        assertTrue(applicationContext.containsBean("firstBean"));
        assertTrue(applicationContext.containsBean("otherBean"));
        assertTrue(applicationContext.containsBean("counter"));
        assertFalse(applicationContext.containsBean("main"));

        assertThrows(
                NoSuchBeanDefinitionException.class,
                ()->applicationContext.getBean("main")
        );
    }

}