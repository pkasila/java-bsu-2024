package by.bsu.dependency.context;

import by.bsu.dependency.context.loop.FirstLoopedBean;
import by.bsu.dependency.context.loop.SecondLoopedBean;
import by.bsu.dependency.exceptions.CyclicDependencyException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DependencyLoopTest {
    @Test
    void testCyclicDependency() {
        var apContext = new SimpleApplicationContext(FirstLoopedBean.class, SecondLoopedBean.class);
        assertThrows(
                CyclicDependencyException.class,
                apContext::start
        );
    }
}
