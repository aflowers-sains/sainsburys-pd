package uk.co.sainsburys.graaldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraalDemoApplication {

  // language=javascript
  public static final String JS_CODE = """ 
    function hello(name) { 
      return 'Hello, ' + name + '!'; 
    }
    
    function fib(n) {
      if (n <= 1) {
        return n;
      } else {
        return fib(n - 1) + fib(n - 2);
      }
    }
    
    console.log(hello('world'.replace('world', 'graalvm')));
    console.log(fib(8));
  """;

  public static void main(String[] args) {
    SpringApplication.run(GraalDemoApplication.class, args);
  }

}
