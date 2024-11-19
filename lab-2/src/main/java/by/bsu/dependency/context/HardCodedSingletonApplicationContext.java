package by.bsu.dependency.context;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.exceptions.ApplicationContextNotStartedException;
import by.bsu.dependency.exceptions.NoSuchBeanDefinitionException;


public class HardCodedSingletonApplicationContext extends AbstractApplicationContext {

    private final Map<String, Class<?>> beanDefinitions;
    private final Map<String, Object> beans = new HashMap<>();

    /**
     * ! Класс существует только для базового примера !
     * <br/>
     * Создает контекст, содержащий классы, переданные в параметре. Полагается на отсутсвие зависимостей в бинах,
     * а также на наличие аннотации {@code @Bean} на переданных классах.
     * <br/>
     * ! Контекст данного типа не занимается внедрением зависимостей !
     * <br/>
     * ! Создает только бины со скоупом {@code SINGLETON} !
     *
     * @param beanClasses классы, из которых требуется создать бины
     */
    public HardCodedSingletonApplicationContext(Class<?>... beanClasses) {
        this.beanDefinitions = Arrays.stream(beanClasses).collect(
                Collectors.toMap(
                        beanClass -> beanClass.getAnnotation(Bean.class).name(),
                        Function.identity()
                )
        );
    }

    @Override
    public void start() {
        contextStatus = ContextStatus.STARTED;
        beanDefinitions.forEach((beanName, beanClass) -> beans.put(beanName, instantiateBean(beanClass)));
    }

    /**
     * В этой реализации отсутствуют проверки статуса контекста (запущен ли он).
     */
    @Override
    public boolean containsBean(String name) throws ApplicationContextNotStartedException {
        checkRunning();
        return beans.containsKey(name);
    }

    /**
     * В этой реализации отсутствуют проверки статуса контекста (запущен ли он) и исключения в случае отсутствия бина
     */
    @Override
    public Object getBean(String name)
            throws ApplicationContextNotStartedException, NoSuchBeanDefinitionException {
        checkRunning();
        checkIfBeanUndefined(name);
        return beans.get(name);
    }

    @Override
    public <T> T getBean(Class<T> clazz)
            throws ApplicationContextNotStartedException, NoSuchBeanDefinitionException {
        checkRunning();
        if (beanDefinitions.values().stream().noneMatch(cl -> cl != clazz)) {
            throw new NoSuchBeanDefinitionException(clazz.toGenericString());
        }
        return instantiateBean(clazz);
    }

    @Override
    public boolean isPrototype(String name)
            throws NoSuchBeanDefinitionException {
        checkIfBeanUndefined(name);
        return false;
    }

    @Override
    public boolean isSingleton(String name)
            throws NoSuchBeanDefinitionException {
        checkIfBeanUndefined(name);
        return true;
    }

    private void checkIfBeanUndefined(String name)
            throws NoSuchBeanDefinitionException {
        if (!beanDefinitions.containsKey(name)) {
            throw new NoSuchBeanDefinitionException(name);
        }
    }

    private <T> T instantiateBean(Class<T> beanClass) {
        try {
            return beanClass.getConstructor().newInstance();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
