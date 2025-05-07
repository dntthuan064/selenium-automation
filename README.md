# Selenium Test Automation Framework

A robust test automation framework built with Selenium WebDriver, TestNG, and Cucumber for BDD testing.

## Features

- Selenium WebDriver 4.18.1
- TestNG 7.9.0 for test execution
- Cucumber 7.15.0 for BDD
- Extent Reports 5.1.1 for detailed reporting
- WebDriverManager 5.6.3 for automated driver management
- Logback for logging
- Jackson for JSON handling
- Maven for dependency management

## Prerequisites

- Java 21 or higher
- Maven 3.8.0 or higher
- Chrome/Firefox browser installed

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── pages/         # Page Object classes
│   │   └── utils/         # Utility classes
│   └── resources/
│       ├── config/        # Configuration files
│       └── drivers/       # WebDriver executables
└── test/
    ├── java/
    │   ├── steps/         # Cucumber step definitions
    │   └── runners/       # Test runners
    └── resources/
        ├── features/      # Cucumber feature files
        └── testdata/      # Test data files
```

## Setup

1. Clone the repository
2. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test suite
```bash
mvn test -DsuiteXmlFile=src/test/resources/run.xml
```

## Reports

- Extent Reports are generated in `test-output/ExtentReport.html`
- Cucumber reports are available in `target/cucumber-reports`

## Code Quality

- Checkstyle for code style checking
- Maven formatter plugin for code formatting

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request 