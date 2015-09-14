package iamedu.spring.generator;

import lombok.Data;

@Data
public class BeanGeneratorDefinition {
  private final Class clazz;
  private final String configRoot;

}
