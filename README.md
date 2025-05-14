# Selenium Test Automation Framework

A robust and scalable test automation framework built with Selenium WebDriver, TestNG, and Cucumber for Behavior-Driven Development (BDD) testing. This framework provides a solid foundation for automating web application testing with best practices and modern tools.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Working with the Codebase](#working-with-the-codebase)
- [Running Tests](#running-tests)
- [Reports](#reports)
- [Code Quality](#code-quality)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Features

- Selenium WebDriver 4.18.1 for browser automation
- TestNG 7.9.0 for test execution and parallel testing
- Cucumber 7.15.0 for BDD implementation
- Extent Reports 5.1.1 for detailed HTML reporting
- WebDriverManager 5.6.3 for automated driver management
- Logback for comprehensive logging
- Jackson for JSON data handling
- Maven for dependency management
- Cross-browser testing support
- Parallel test execution
- Screenshot capture on test failure
- Data-driven testing capabilities

## Prerequisites

- Java 21 or higher
- Maven 3.8.0 or higher
- Chrome/Firefox browser installed
- Git for version control
- IDE (IntelliJ IDEA or Eclipse recommended)

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
        └── config/        # Configuration files
```

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd selenium-automation
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Configure test environment:
   - Update `src/test/resources/config.properties` and `src/test/resources/config/qa.properties` with your test environment details
   - Test data is in `src/test/resources/testdata/`

4. Verify setup:
   ```bash
   mvn clean test -Dtest=SmokeTest
   ```

## Working with the Codebase

### Development Workflow

1. **Creating New Tests**
   - Create feature files in `src/test/resources/features/`
   - Follow BDD format with Given-When-Then scenarios
   - Implement step definitions in `src/test/java/steps/`
   - Add page objects in `src/main/java/pages/`

2. **Page Object Pattern**
   ```java
   public class LoginPage {
       @FindBy(id = "username")
       private WebElement usernameField;
       
       public void login(String username, String password) {
           usernameField.sendKeys(username);
           // ... implementation
       }
   }
   ```

3. **Adding Test Data**
   - Store test data in `src/test/resources/testdata/`
   - Use JSON format for structured data
   - Create separate files for different test scenarios

4. **Configuration Management**
   - Environment-specific configs in `src/test/resources/config/`
   - Use `config.properties` for general settings

### Best Practices

1. **Test Organization**
   - Group related tests using Cucumber tags
   - Use meaningful feature and scenario names
   - Keep scenarios independent and atomic
   - Follow the Arrange-Act-Assert pattern

2. **Code Quality**
   - Write clean, maintainable code
   - Use meaningful variable and method names
   - Add comments for complex logic
   - Follow Java coding conventions

3. **Error Handling**
   - Implement proper exception handling
   - Add meaningful error messages
   - Use custom exceptions when needed
   - Log errors appropriately

4. **Reporting**
   - Add detailed test steps in reports
   - Include relevant test data in reports
   - Capture screenshots for failures
   - Add environment details to reports

### Common Tasks

1. **Adding New Page Objects**
   ```java
   @Page
   public class NewPage extends BasePage {
       public NewPage(WebDriver driver) {
           super(driver);
           PageFactory.initElements(driver, this);
       }
   }
   ```

2. **Creating New Step Definitions**
   ```java
   @When("user performs action")
   public void userPerformsAction() {
       // Implementation
   }
   ```

3. **Adding Test Data**
   ```json
   {
     "testCase": "login_test",
     "username": "testuser",
     "password": "testpass"
   }
   ```

4. **Running Specific Tests**
   - By tag: `mvn test -Dcucumber.filter.tags="@smoke"`
   - By feature: `mvn test -Dcucumber.features=src/test/resources/features/login.feature`
   - By scenario: `mvn test -Dcucumber.filter.tags="@scenario1"`

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test suite
```bash
mvn test -DsuiteXmlFile=src/test/resources/run.xml
```

### Run specific feature
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

### Run tests in parallel
```bash
mvn test -Dparallel=true -DthreadCount=4
```

## Reports

### Test Reports Location
- Extent Cucumber Report: `test-reports/extent-cucumber-report.html`
- Manual Screenshot Report: `test-reports/{timestamp}/manual-reports/manual-report.html`
- Screenshots: `test-reports/{timestamp}/screenshots/`

### Viewing Reports
You can view the HTML reports in two ways:

1. **File Explorer method**:
   - Navigate to the `test-reports` folder in your project directory
   - For the main Cucumber report: open `extent-cucumber-report.html`
   - For the manual report with screenshots: open `test-reports/{timestamp}/manual-reports/manual-report.html`

2. **Using Command Line**:
   ```bash
   # Open the main Cucumber report
   start test-reports\extent-cucumber-report.html
   
   # Open the latest manual report with screenshots (Windows)
   start test-reports\{timestamp}\manual-reports\manual-report.html
   
   # Open reports on Mac/Linux
   open test-reports/extent-cucumber-report.html
   open test-reports/{timestamp}/manual-reports/manual-report.html
   ```

### Report Features
- Extent Cucumber Report:
  - Dashboard view with test execution summary
  - Feature-wise breakdown of scenarios
  - Step definitions with status
  - Test execution timeline
  - Environment details

- Manual Screenshot Report:
  - Detailed test steps with timestamps
  - Screenshots captured during test execution
  - Browser and system information
  - Error logs and stack traces for failures

## Code Quality

- Checkstyle for code style checking
- Maven formatter plugin for code formatting
- SonarQube integration for code analysis
- Code coverage reports with JaCoCo

## Troubleshooting

### Common Issues

1. **WebDriver Issues**
   - Ensure correct browser version is installed
   - Check WebDriverManager logs for driver download issues
   - Verify browser compatibility

2. **Test Failures**
   - Check test data in `testdata` directory
   - Verify environment configuration
   - Review test logs in `logs` directory

3. **Build Issues**
   - Clean Maven cache: `mvn clean`
   - Update dependencies: `mvn dependency:purge-local-repository`
   - Check Java version compatibility

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style Guidelines
- Follow Java coding conventions
- Write meaningful commit messages
- Add appropriate comments and documentation
- Include unit tests for new features
