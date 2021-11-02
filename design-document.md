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
this approach; it suffices to annotate our entity classes. Similarly, since
much of the application logic follows a standard CRUD model, making use of the
Spring Framework's Repository interface allows reducing boilerplate while
maintaining Clean Architecture. In addition, the combination will ease the
devleopment of a web frontend.

# Clean Architecture

<!-- A brief description of how your project adheres to Clean Architecture (if you notice a violation and aren't sure how to fix it, talk about that too!) -->

# SOLID design principles

<!-- A brief description of how your project is consistent with the SOLID design principles (if you notice a violation and aren't sure how to fix it, talk about that too!) -->

# Packaging strategies

<!---    A brief description of which packaging strategies you considered, which you decided to use, and why. -->

# Design patterns

<!---    A summary of any design patterns your group has implemented (or plans to implement). -->

# Progress report

<!---        open questions your group is struggling with -->

<!---        what has worked well so far with your design -->

<!---        a summary of what each group member has been working on and plans to work on next -->

