#@World(learn.CukeWorld)
Feature: run Grails unit test annotation in steps

#  @World(learn.CukeWorld)
#  Scenario: can unit test controller
#    Given given
#    When when
#    Then then


  @World(learn.CukeDomainWorld)
  Scenario: can unit test domain object
    Given givenDomain
    When whenDomain
    Then thenDomain
