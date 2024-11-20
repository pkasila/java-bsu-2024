package by.bsu.dependency.context.loop;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.Inject;

@Bean(scope = BeanScope.PROTOTYPE)
public class SecondLoopedBean {

    @Inject
    private FirstLoopedBean firstLoopedBean;
}