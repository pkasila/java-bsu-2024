package by.bsu.dependency.context.loop;

import by.bsu.dependency.annotation.Inject;

public class FirstLoopedBean {

    @Inject
    private SecondLoopedBean secondLoopedBean;
}