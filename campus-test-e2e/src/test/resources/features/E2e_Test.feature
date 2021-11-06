@BaseLineScenarios
Feature: BaseLine Scenarios

  @E2eAnnexTesting
  Scenario: Checks the integrity of the Proposal during basic operations Add/Delete Article/Level
    Given navigate to "Commission" edit application
    Then navigate to Repository Browser page
    When click on create proposal button
#    Then "Create new legislative document - Template selection (1/2)" window is displayed
#    When select template "SJ-023 - Proposal for a Regulation of the European Parliament and of the Council"
    Then next button is enabled
#    When click on next button
#    Then "Create new legislative document - Document metadata (2/2)" is displayed
    And  close the browser
