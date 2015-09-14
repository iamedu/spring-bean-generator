package iamedu.spring.generator

import iamedu.spring.generator.CoreConfigTest.Test
import org.springframework.test.context.ContextConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import spock.lang.*

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = CoreConfigTest)
class GeneratorSpec extends Specification {

  @Autowired
  ApplicationContext applicationContext

  def 'We should be able to find all generated types'() {
    when:
      Map<String, Test> tests = applicationContext.getBeansOfType(Test)
    then:
      tests.size() == 2
  }

}

