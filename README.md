# Flexible Calculator

## Overview

The Flexible Calculator is a Command line Java-based application designed for evaluating mathematical expressions. It supports basic arithmetic operations, parentheses, and chaining of multiple operations. The system is built for extensibility and modularity, making it easy to add new operations.

## Thought Process

### Class Design

- `UserInteractionService`
    - Purpose: Manages user interaction through the terminal, providing input prompts and displaying results.
    - Thought Process: Facilitates a command-line interface for users, handling input errors and tracking metrics such as the number of calculations and errors.

- `ExpressionParser`
    - Purpose: Parses mathematical expressions into a list of tokens for further processing.
    - Thought Process: Tokenizes numbers, operators, and parentheses while managing invalid characters and ensuring correct input format.

- `ExpressionEvaluator`
    - Purpose: Integrates parsing and calculation to evaluate mathematical expressions.
    - Thought Process: Combines the functionality of `ExpressionParser` and `Calculator` to produce results from input expressions.

- `Token` and `TokenType`
    - Purpose: Represents individual units of the expression and categorizes them into types.
    - Thought Process: Encapsulates token data, providing a structured way to handle different token types such as numbers, operators, and parentheses.

- `Calculator`
    - Purpose: Performs calculations based on specified operations and operands.
    - Thought Process: Utilizes a map to link operations with their functions, allowing for easy extension by adding new operations. Supports chaining operations for sequential calculations.

### Logical Diagram

```plaintext
+----------------------------------+
|   CalculatorCommandLineRunner    |
|----------------------------------|
| - userInteractionService: UserInteractionService |
|----------------------------------|
| + run(String... args)            |
+----------------------------------+
                |
                v
+----------------------------------+
|   UserInteractionService         |
|----------------------------------|
| - expressionEvaluator: ExpressionEvaluator |
|----------------------------------|
| + start()                         |
+----------------------------------+
                |
                v
+----------------------------------+
|   ExpressionEvaluator            |
|----------------------------------|
| - expressionParser: ExpressionParser |
| - rangeValidator: RangeValidator |
|----------------------------------|
| + evaluate(String expression)    |
| - evaluateTokens(List<Token> tokens) |
| - precedence(char operator)      |
| - applyOperator(char operator, double b, double a) |
+----------------------------------+
        |
        v
+----------------------------------+
|   RangeValidator                 |
|----------------------------------|
| - minInput: double               |
| - maxInput: double               |
| - minOutput: double              |
| - maxOutput: double              |
|----------------------------------|
| + setInputRange(double min, double max) |
| + setOutputRange(double min, double max) |
| + validateInput(double value)    |
| + validateOutput(double value)   |
+----------------------------------+
        |
        v
+----------------------------------+
|   ExpressionParser               |
|----------------------------------|
| - parse(String expression): List<Token> |
+----------------------------------+
        |
        v
+----------------------------------+
|   Calculator                     |
|----------------------------------|
| - operationMap: Map<Operation, BiFunction<Double, Double, Double>> |
|----------------------------------|
| + calculate(Operation operation, double num1, double num2) |
+----------------------------------+
```
## Usage

### Entering Expressions

- Basic Arithmetic:
    - `1 + 1` will return `2.0`
    - `(1 + 3) * 4` will return `16.0`
- Chaining Operations:
    - `5 + 3 * 2` will return `11.0`
- Parentheses for Order of Operations:
    - `(2 + 3) * (4 - 1)` will return `15.0`

### Chaining Operations

- Sequential Calculations:
    - Start with an initial value and perform a series of operations. For example, `10 + 5 - 2 * 3` will first add `10` and `5`, then subtract `2` times `3`.

### Exiting the Application

- To stop the application, type `exit` and press Enter. The application will terminate.

### Error Handling

- The application handles errors such as division by zero and invalid characters gracefully, providing informative error messages for incorrect input.


## Consumer Guide

### Getting Started

#### Prerequisites

- Java 17 or higher: Ensure Java 17 or later is installed.
- Maven: Required for building the project.

#### Building the Project

1. Clone the repository:

    ```bash
    git clone <repository-url>
    ```

2. Navigate to the project directory:

    ```bash
    cd flexible-calculator
    ```

3. Build the project using Maven:

    ```bash
    mvn clean package
    ```

#### Running the Application

To run the application, use the following command:

```bash
java -jar target/flexible-calculator-0.0.1-SNAPSHOT.jar
   ```

## Restrictions

- Expression Complexity: The calculator supports basic arithmetic operations and parentheses. It may not handle very complex functions or deeply nested expressions.
- Error Handling: Basic error handling is implemented. Complex error scenarios might need additional handling.
- No External Endpoints: Metrics and health checks are managed internally, without exposing external endpoints.
## Ranges

The calculator enforces the following ranges for values:

- **Input Range**: Values must be between `-1,000,000` and `1,000,000`.
- **Output Range**: Results must be between `-1,000,000` and `1,000,000`.

These ranges are set to ensure the calculator operates within safe and reasonable limits, preventing errors or inaccuracies. If an input or output value falls outside these ranges, an error will be displayed.
## To-Do List

- Add Support for New Operations:
  - Implement additional mathematical functions (e.g., square root).
  - Update the `Operation` enum and `Calculator` class to support these operations.

- Improve Expression Parsing:
  - Enhance the parser to handle more complex expressions and functions.
  - Implement robust error handling for invalid expressions.

-  Metrics Collection:
  - Integrate with external monitoring tools (e.g., Prometheus, Grafana).
  - Add detailed metrics and performance tracking.

- Enhance User Interaction:
  - Develop a more user-friendly interface or support additional command-line options.
  - Improve documentation and help features.

- Testing and Validation:
  - Write comprehensive unit and integration tests.
  - Validate the application with a wide range of edge cases and complex inputs.

- Documentation and Examples:
  - Update the README.md with detailed examples and usage scenarios.
  - Provide additional documentation on extending and customizing the application.
