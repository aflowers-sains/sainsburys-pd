package uk.co.sainsburys.aihelper

import spock.lang.Specification


class AdderSpec extends Specification {
  def "Can add together two numbers"() {
    given:
    def adder = new Adder()

    when:
    def result = adder.add(2, 3)

    then:
    result == 5
  }

  def "Adding with any negative number always produces positive result"() {
    given:
    def adder = new Adder()

    when:
    def result = adder.add(-2, -3)

    then:
    result == 5
  }

  def "Adding with any numbers above one hundred always produces positive result less than 100"() {
    given:
    def adder = new Adder()

    when:
    def result = adder.add(1, 103)

    then:
    result == 4
  }

  def "Numbers above 100 have 100 subtracted before adding"() {
    given:
    def adder = new Adder()

    expect:
    adder.add(150, 3) == 53
    adder.add(3, 150) == 53
    adder.add(150, 150) == 100
    adder.add(101, 102) == 3
  }
}