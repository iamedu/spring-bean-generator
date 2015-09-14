package iamedu.spring.generator;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.BeansException;

import org.springframework.stereotype.Component;

@Component
class BeanConfigGenerator implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
        throws BeansException {
        DefaultListableBeanFactory f = (DefaultListableBeanFactory)registry;
        Map<String, BeanGeneratorDefinition> generators =
            f.getBeansOfType(BeanGeneratorDefinition.class);

        generators
            .values()
            .stream()
            .map(this::generateBeanDefinition)
            .collect(Collectors.toList());


    }

    private BeanDefinition generateBeanDefinition(BeanGeneratorDefinition definition) {
        Class clazz = definition.getClazz();
        String configRoot = definition.getConfigRoot();
        System.out.println(clazz + " " + configRoot);
        return null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("Hola mundo");
    }
}

