# Specification

The HR scheduling system manages scheduling for an organization: shifts and
meetings for employees. It maintains a roster of employees at the organization
(which may be modified as employees join or leave the organization), along with
scheduling constraints. The Manager of the Organization can assign employees
work as Event according to specific rules:

+ Minimum and maximum number of hours stipulate ceiling and floor that employees may work a week.
+ Minimum and maximum number of employees represent max and min number of employees working at a given time/event. This is meaningful since an insufficient number of employees can’t get the work done, while an excessive number of employees would cause redundancy.
+ Leave tracking -- avoid scheduling an employee on holiday
+ Scheduling contiguously where possible.

These scheduling requirements translate to requirements for the general
employee management: the number of working hours and leave of absence time
relates to the salary of the employee (part time vs full time), the workplace
requirements is a property of the organization.

All persistent state (employee data, schedules, and so on) must be serialized
to an on-disk database as it is updated, and loaded from the database on
start-up, to guarantee data integrity even if the system fails.

---

<!-- Briefly highlight any additional functionality that you have implemented between phase 0 and the end of phase 1. -->

Since phase 0, we're implementing scheduling, data persistence, and an interactice frontend.

# Major design decisions

<!-- A description of any major design decisions your group has made (along with brief explanations of why you made them). -->

For persistence, we have decided to use the Java Persistence API in tandem with
SpringBoot. Our original specification required persistence to a database; the
JPA and Spring Framework provide an effective way to support this requirement
with Clean Architecture. Since the entities involved map nautrally to a
relational database, we don't need to specify a database schema explicitly in
this approach; it suffices to annotate our com.hr.entity classes. Similarly, since
much of the application logic follows a standard CRUD model, making use of the
Spring Framework's com.hr.repository interface allows reducing boilerplate while
maintaining Clean Architecture. In addition, the combination will ease the
devleopment of a web frontend.

For scheduler, we use an SAT solver. We model a shift schedule as propositional
variables ("employee X works a shift on day D at hour H") and model scheduling
constraints as propositional formulas. For ease of specification, we use
pseudo-boolean constraints ("at least 2 employees work on day D at hour H",
expressed as "the sum of all the shift variables on day D at hour H is at least
2"). These constraints are passed to an off-the-shelf SAT solver in Java
(`sat4j`), which finds a valid schedule conforming to our specification. SAT
solvers represent a balance between generality, performance and ease of use. It
would not be possible to build a better specialized algorithm in the project's
time constraints, given the complexity of the scheduling problem and its
NP-hardness. Transforming the problem to a well studied one (boolean
satisfiability) allows us to focus on our business logic and not the details of
NP-hard heuristics. This provides a clean layering.

# Clean Architecture

As a whole, our design adheres to clean architecture by putting every effort to ensure that entities, use cases, interfaces, and controllers interact with one another in ways that are explicit in terms of relationships and dependencies, as shown in CRC cards.

### To make our code clean:
 - Data access interface of  `CalendarRepository `,  `EmployeeRepository `,  `EventRepository `             

In this way, when use case classes create or load data, they won't interact with entity and concrete data loader classes. This helps we following the rule of Clean Architecture that the outer layers won't directly make use of inner layers.

 - Independent Entity layer 

The Entity layer, which is at the core of the architecture, consists of  `Calendar `,  `Employee `,  `Event `,  `Meeting `, and  `Shift `. Employees stores personal information such as their ID, name, salary, maxHoursPerWeek, schedulable hours, and hoursPerShift. Calendar stores a list of Events. Event stores the name, start time, and duration of an event that is categorised as Meeting or Shift. Each of these five classes is unaware of the other layers and is not reliant on other components found in the outer layers.

 - Use case Layer: 

It contains two parts,  `EmployeeModifierImpl ` and  `SchedulerImpl `, providing basic services by which present what we can do with the entity in pure business logic and plain code.
1.  `EmployeeModifierImpl `: A Employee Manager has the permission to modify employees in this Organization to hire and fire Employee from the Organization, and evaluate payroll .

2.  `SchedulerImpl `: The automatic scheduler manages the Calendars in the Organization,  creating Shift objects to satisfy shift scheduling constraints. We handle this as a discrete problem, with the basic step of scheduling a single Shift, or refusing to schedule any Shifts if scheduling is impossible.




# SOLID design principles

### - Single Responsibility Principle

We adhere to the Single Responsibility principle by keeping our each classes unique, with only one major responsibility. Within each layer of clean architecture, we divided class functionality more specifically. This is done in accordance with their specific major responsibility. For instance, in use case layer, `EmployeeModifierImpl` is in charge of changes in Employee, whereas `SchedulerImpl` is in charge of managing events on Calendar. Moreover, we created a controller that is only associated with one use case; we have `FireController`, `HireController`, which is matched by `EmployeeModifierImpl`, and `SchedulerImpl`, which is matched by `ScheduleController`. Each component takes on a simple responsibility with little reliance on others. These approaches guarantee that the principle of single responsibility is followed.

### - Closed/Open Principle

Objects or entities should be open for extension but closed for modification. This means that a class should be extendable without modifying the class itself.
First, All classes make use of private variables with getters and setters, which ensures internal implementations can be changed without changing the interface. The Command interface and structure of the modifier class allow easy adding of additional Use Cases for more functionality, by adding the new Use Case to the command dictionary. Take `EmployeeModifierImpl` as an example, we create an interface called `EmployeeModifier`, providing with various ways of managing employee like hire and fire. In the future, if we wanna add some new instruction towards employee,  the interface helps us  alternate the functionality without changing the existing code.

### - Liskov Substitution Principle

This rule states that every subclass or derived class should be substitutable for their base or parent class. The implementation of the `Event` superclass, as well as the `Shift` and `Meeting` subclasses, demonstrates our adherence to the Liskov substitution principle; they all share functionality such as the isSameWeek() and getHours() methods. This means that the SchedulerImpl can rely on these methods existing and functioning properly regardless of which subclass of Event is being used.

### - Interface Segregation Principle

We are able to assure that no class is forced to rely on methods that it does not use. We keep the interface's contents simple and responsibility-specific in order to prevent any class that implements it from receiving unnecessary methods. For example, in order to access a database, we implement three unique repository DAO interfaces: `CalendarRepository`, `EmployeeRepository`, and `EventRepository`. As a result, all interfaces are unique and do not interact with one another, which also adheres to the single responsibility rule.

### - Dependency Inversion Principle

Entities must depend on abstractions, not on concretions. It states that the high-level module must not depend on the low-level module, but they should depend on abstractions. We also made our `CrudRepository` an abstract interface with three unique subclasses as a dependency in whatever class needed access to database info. We offer repository interface for modifier to access database without directly depend on the entity, but abstraction methods, so that no clients of `EmployeeModifierImpl` depended on our specific database implementation.



# Design patterns
### - Template Pattern

The template method design pattern was used in the Event abstract class and Shift and Meeting subclasses. The `isSameWeek()` template method is used to determine whether an event occurs during the same week as a given date/time for a Shift or Meeting object. This design pattern was used because the method for determining event time was identical for both types of Events. As a result, the duplicated code was moved to an abstract class, while the remaining implementations were moved into subclasses.

### - Factory Pattern

This design pattern is used in the repository DAO interface. We provide varied repository classes for loading various entities (e.g. `EmployeeRepository` to access an Employee object into database, `EventRepository` to write an event object into database).

As a consequence, if we do not implement the Factory Design Pattern, we must decide which repository class to instantiate every time we want to write an entity into the database. This leads to high-coupling issues.

In this case, Factory Design Pattern is essential to solve the problem. This pattern takes the entity to be stored in the database and determines which repository class should be instantiated. In this manner, we grouped all of the repository classes into a single folder. Other classes are not required to decide which repository class to instantiate. Furthermore, if we want to add more entities in the future, it is easily extensible.

# Packaging strategies

The packaing strategy that we use is packaging by layer. Because our team decide to design the program in CLEAN architecture, we create packages: "entity" for entity layer, "service" for use case layer, "controller" for controller layer. Also, because we are using h2 database, we use "repository" as a package to organize all of our repository classes which is in use case layer in a role of gateways. The Outer layer are majorly HTMLs, so we put them into resources which is a good package organizing all the HTMLs.
<!---    A brief description of which packaging strategies you considered, which you decided to use, and why. -->

# Progress report

<!---        open questions your group is struggling with -->

<!---        what has worked well so far with your design -->

<!---        a summary of what each group member has been working on and plans to work on next -->
[required] brief summary of what each group member has been working on since phase 1
Each group member should include a link to a significant pull request (or two if you can't pick just one) that they made throughout the term. Include a sentence or two explaining why you think this demonstrates a significant contribution to the team.

Alyssa Rosenzweig: scheduler, code review, design document, gradle setup.

Jiawei Chen: 
 Entities, Spring Framework, Mockito Test, Gradle setup, Writting welcome controller and help to modify and improve other controller, CRUDRepository setup, Authored employee modifier service and event service while test them


