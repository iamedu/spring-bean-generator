package iamedu.spring.generator

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@EnableAutoConfiguration
@Configuration
@ComponentScan
class CoreConfigTest {

  static class Test {
    String thing1
    Integer thing2
  }

  @Bean
  BeanGeneratorDefinition test() {
    new BeanGeneratorDefinition(
      Test.class,
      'config'
    )
  }

}

