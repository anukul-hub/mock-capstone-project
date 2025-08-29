Feature: Amazon Search Box

  @testcase1
  Scenario: User searches for Logitech MX Anywhere 3s
    Given user is on Amazon home page
    When user enters "logitech mx anywhere 3s" in search box
    And user clicks on search button
    Then search results for "logitech mx anywhere 3s" are displayed
