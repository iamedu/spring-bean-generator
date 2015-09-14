package iamedu.spring.generator;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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
            .map(this::generateBeanDefinitions)
            .forEach(it -> registerDefinitionMap(registry, it));
    }

    private void registerDefinitionMap(BeanDefinitionRegistry registry,
                                       Map<String, BeanDefinition> definitionMap) {
        definitionMap.entrySet().stream()
            .forEach(it ->
                     registry.registerBeanDefinition(
                       it.getKey(),
                       it.getValue()
                     ));
    }

    private Map<String, BeanDefinition> generateBeanDefinitions(BeanGeneratorDefinition definition) {
        Class clazz = definition.getClazz();
        String configRoot = definition.getConfigRoot();

        YamlMapFactoryBean factory = prepareYamlFactory();
        Map<String, Object> config = factory.getObject();

        Map<String, Object> ms = (Map<String,Object>) config
            .get(configRoot);

        return ms.entrySet().stream()
            .collect(Collectors.toMap(
                 (it) -> it.getKey(),
                 (it) ->
                    generateBeanDefinition(
                        it.getKey(),
                        clazz,
                        (Map<String, Object>) it.getValue())));
    }

    private BeanDefinition generateBeanDefinition(String name,
                                          Class clazz,
                                          Map<String, Object> map) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

        map.keySet().stream()
            .forEach(it -> builder.addPropertyValue(it, map.get(it)));

        return builder.getBeanDefinition();
    }

    private YamlMapFactoryBean prepareYamlFactory() {
        YamlMapFactoryBean factoryBean = new YamlMapFactoryBean();

        ClassPathResource resource = new ClassPathResource("/application.yml");

        factoryBean.setResources(resource);

        return factoryBean;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("Hola mundo");
    }
}

