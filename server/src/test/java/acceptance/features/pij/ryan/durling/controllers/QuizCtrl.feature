Feature: Controller for quizzes

  Scenario Outline: should be able to add a quiz
    Given a user has a quiz controller
    And a user has a <quiz>
    When a user adds a quiz
    Then the the user can retrieve the <quiz>

  Examples:
    | quiz |
    | 4    |
    | 67   |

  Scenario Outline: should be able to get a set of available quizzes
    Given a user has a quiz controller
    And a user has a <quiz>
    When a user adds a quiz
    Then the the user can retrieve list of the available <quiz>

  Examples:
  | quiz |
  | 4    |
  | 67   |

  Scenario Outline: should  be able to close a quiz
    Given a user has a quiz controller
    And a user has a <quiz>
    When a user adds a quiz
    Then the the user can retrieve list of the available <quiz>
    When a user close a <quiz>
    Then the quiz should be closed

  Examples:
    | quiz |
    | 4    |
    | 67   |

  Scenario Outline: should  be able to open a quiz
    Given a user has a quiz controller
    And a user has a <quiz>
    When a user adds a quiz
    Then the the user can retrieve list of the available <quiz>
    When a user close a <quiz>
    Then the quiz should be closed
    When a user opens a <quiz>
    Then the quiz should be opened

  Examples:
    | quiz |
    | 4    |
    | 67   |