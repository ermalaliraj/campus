@BaseLineScenarios
Feature: BaseLine Scenarios

  @E2eAnnexTesting
  Scenario: Checks the integrity of the Proposal during basic operations Add/Delete Article/Level
    Given today is Sunday
    When I ask
    Then  close the browser