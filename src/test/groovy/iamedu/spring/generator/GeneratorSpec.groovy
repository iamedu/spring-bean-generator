package iamedu.spring.generator

import org.springframework.test.context.ContextConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.*

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = CoreConfigTest)
class GeneratorSpec extends Specification {

  @Autowired
  BeanConfigGenerator generator

  def 'hola'() {
    when:
      true
    then:
      false
  }

}

