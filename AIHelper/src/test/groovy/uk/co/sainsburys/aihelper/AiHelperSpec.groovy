package uk.co.sainsburys.aihelper

import spock.lang.Specification


class AiHelperSpec extends Specification {

  def "AI Helper has defined a way to add numbers together"() {
    given:
    AiHelper helper = new AiHelper()

    when:
    def result = helper.add(left, right)

    then:
    expected == result

    where:
    left | right | expected
    1    | 2     | 3
    -1   | 2     | 3
  }

  def "AI Helper has defined a way to add numbers together where 2 negatives produce a negative"() {
    given:
    AiHelper helper = new AiHelper()

    when:
    def result = helper.add(left, right)

    then:
    result == expected

    where:
    left | right | expected
    -1   | -2    | -3
    -1   | -5    | -6
  }

  def "We have an endpoint that says hello world"() {
    given:
    AiHelper helper = new AiHelper()

    when:
    def result = helper.helloWorld()

    then:
    result == "Hello World!"
  }

}