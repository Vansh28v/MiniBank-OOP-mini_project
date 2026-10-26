📌 About the Project
MiniBank is a Java project prepared for the Object-Oriented
Programming (Java) --- Part B practical work. It brings together small,
focused demonstrations of banking operations and Java language features
in one repository.

The project uses a package-based source layout (model, service, and
util) alongside standalone demonstration programs. Depending on the
class being run, the code demonstrates account operations, interfaces,
polymorphism, validation, exceptions, serialization, repository-style
abstractions, transaction processing, and concurrency concepts.
Scope note: This repository contains educational demonstrations.
It is not a production banking application and should not be used to
store real customer or financial data.

✨ Highlights
- 💳 Account operations --- deposit, withdrawal, balance access
  and interest calculation.
- 🧱 Object-oriented design --- classes, encapsulation,
  interfaces, inheritance-related examples and polymorphism
  demonstrations.
- 🧩 Functional-style examples --- anonymous classes and lambda
  expressions.
- 🛡️ Validation and errors --- custom annotations, validation
  utilities and custom exception classes.
- 💾 Persistence concepts --- Java serialization and state
  persistence demonstrations.
- 🧵 Concurrency concepts --- thread-safety, transaction
  processing, producer--consumer and deadlock-prevention examples.
- 🧾 Supporting utilities --- report generation, transaction
  logging and reusable helper methods.
🗂️ Project Structure
MiniBank/
├── .gitignore
├── AccountWorker.java
├── MiniBank.java
├── MiniBankInterfaceDemo.java
├── src/
│   ├── model/
│   │   ├── Account.java
│   │   ├── InterestBearing.java
│   │   ├── Premium.java
│   │   ├── Transactable.java
│   │   ├── WithdrawRule.java
│   │   └── annotation/
│   │       ├── Id.java
│   │       ├── MaxLength.java
│   │       └── Positive.java
│   ├── service/
│   │   ├── BankingApp.java
│   │   ├── practical2/CustomerAccountDemo.java
│   │   ├── practical3/CustomerAccountMethodsDemo.java
│   │   ├── practical4/MiniBankValidationDemo.java
│   │   ├── practical5/MiniBankPolymorphismDemo.java
│   │   ├── MiniBank.java
│   │   ├── AccountStore.java
│   │   ├── TransactionProcessor.java
│   │   ├── ReportGenerator.java
│   │   ├── StatePersister.java
│   │   └── ...supporting demos and exceptions
│   └── util/
│       ├── AnnotationValidator.java
│       └── BankUtils.java
├── README.md
└── PROTOCOL.md
Package responsibilities
  Package                             Responsibility
  model                             Represents account data and
                                      contracts such as transaction and
                                      interest operations.

  model.annotation                  Defines custom annotations used to
                                      mark fields for validation.

  service                           Contains banking logic,
                                      persistence, transaction
                                      processing, exception and
                                      concurrency demonstrations.

  util                              Provides reusable validation and
                                      general-purpose helper methods.

  service.practical2--practical5      Interactive practicals for customer
                                      details, cloning, validation and
                                      polymorphism.
⚙️ Requirements
- JDK: Install a Java Development Kit (JDK) that provides both
  java and javac.
- Terminal: PowerShell, Command Prompt, macOS/Linux terminal, or
  the integrated VS Code terminal.
- Git: Optional, for cloning and updating the repository.
Check your Java setup:
java -version
javac -version
Both commands should display an installed Java version.
🚀 Compile and Run
Run these commands from the repository root (the folder containing
src).
1. Compile the package-based source files
Windows PowerShell:
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName)
This compiles the Java source files under src and places generated
.class files in out.
2. Run the integrated MiniBank application
java -cp out service.BankingApp
Choose option 12 to open the practical demonstrations. Practicals 2, 3,
4, 5, 7, 9, 10 and 11 now ask for user input. The demos use the same
Scanner as the main menu so input continues correctly when returning to
the submenu. Practical 11 serializes the accounts created in the current
application session and generates a report from the transaction log.

The separate root-level MiniBank.java and MiniBankInterfaceDemo.java
remain available as standalone examples; avoid compiling all root-level
Java files together because some older demos define helper classes with
the same names.

🔄 Typical Account Flow
1. An Account object is created with an opening balance and interest
   rate.
2. A deposit request is checked to ensure its amount is positive.
3. A withdrawal request is accepted only if the amount is positive and
   does not exceed the available balance.
4. The balance can be queried, and yearly interest can be calculated
   using the account's configured rate.
5. Supporting service classes demonstrate additional concerns such as
   persistence, reports, transaction processing, validation and
   concurrency.
The exact behavior depends on which demonstration class is run. See
[`PROTOCOL.md`](PROTOCOL.md) for the architecture and execution details.
🧠 Java Concepts Demonstrated
  Concept                             Example area
  Classes and encapsulation           model.Account

  Interfaces                          Transactable, InterestBearing,
                                      WithdrawRule

  Polymorphism and lambdas            service.practical5 and
                                      package-based demonstration classes

  Custom annotations                  model.annotation and
                                      util.AnnotationValidator

  Exception handling                  Custom exception classes and
                                      exception demos

  Serialization and persistence       SerializationDemo,
                                      StatePersister, AccountStore

  Collections / repository            Repository and RepositoryDemo
  abstraction                         

  Multithreading                      AccountWorker, ThreadSafetyDemo

  Producer--consumer pattern          ProducerConsumerDemo
  Logging and reporting               Transactionlog, ReportGenerator
🧪 Suggested Testing Checklist
- [ ] Compile the source code successfully.
- [ ] Run service.MiniBank and inspect the printed account
  operations.
- [ ] Try valid and invalid deposit / withdrawal amounts in the
  relevant demo.
- [ ] Run the interface and polymorphism demonstrations.
- [ ] Inspect the validation and exception examples.
- [ ] Run persistence or serialization demonstrations only in a test
  directory, because they may create or modify local data files.
- [ ] Review concurrency demonstrations separately and repeat them if
  output order varies between runs.
📁 Generated Files
Some demonstrations may create runtime files such as serialized account
data, transaction logs, or reports. These are local outputs, not source
code. The repository's .gitignore excludes common compiled output and
generated runtime files.

Important: Do not commit personal data, credentials, real banking
details, or sensitive runtime data.
🛠️ Troubleshooting
javac is not recognized
Install a JDK and configure your PATH, then reopen the terminal.

Could not find or load main class
Run the command from the project root and verify the class name and
package. For a packaged class, use its fully qualified name, such as
service.MiniBank.

package model does not exist
Compile the files under src together using the javac -d out ...
command above.

A demo creates files or changes output between runs
Check that demo's source for file I/O, serialization, logging or
threading behavior. Use disposable test data when experimenting.
🎓 Academic Context
- Project: MiniBank --- Java OOP Practicals, Part B
- Coverage: Practicals 1--13
- Language: Java
- Repository: Vansh28v /
  Java-MiniBank-Project-OOP-Practicals-1-13
::: {align="center"}
**Built for learning. Structured for clarity. Powered by Java.** ☕

If this repository helps you understand a concept, explore the source
code and run the examples yourself.
:::