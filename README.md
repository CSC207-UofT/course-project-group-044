# CSC207 Group Project

HR SCHEDULING SYSTEM
- group 044 - 

Specification

The HR scheduling system manages scheduling for an organization: shifts for hourly employees, and meetings for salaried employees. It maintains a roster of employees at the organization (which may be modified as employees join or leave the organization), along with scheduling constraints. It automatically schedules employees according to specific rules:

Minimum and maximum number of hours an employee may work a week.
Minimum and maximum number of employees working at a given time(event) (too few -- can’t get the work done; too many -- redundancy)
Leave tracking (do not schedule an employee on holiday)
Scheduling contiguously where possible.

These scheduling requirements translate to requirements for the general employee management: the number of hours working is a property of the employee (part time vs full time), the workplace requirements is a property of the organization, and leave is a property of the employee.

All persistent state (employee data, schedules, and so on) must be serialized to an on-disk database as it is updated, and loaded from the database on start-up, to guarantee data integrity even if the system fails.

Entity classes
Event (abstract class): date/time, name, location, duration
Shift: concrete of Event
Meeting: holder (Employee), participants (set of Employees), subclass of Event
Employee: name, employee ID, title, Calendar, salary, workload(hour can work)
Organization: set of Employees
Calendar: collection of Events, 

Remark: need to have an interface + dependency inversion so setting/getting properties is backed by a database class on the outside.

SOLID principle.

Use case classes
Scheduler: creates Events on a Calendar according to particular rules (? maybe 
abstract class / interface for the method determining the rules, with common code for the scheduling)

ShiftScheduler: assign an employee to shifts (Event) with concern of his/her workload (and more).  Subclasses Scheduler, implementing the scheduling rules in the specification.
(note: if an employee does not reach his/her maximum workload, we should automatically call shift Scheduler for that employee.)
HireManager: allow an employee to join the organization. Assign a job through Shift Scheduler until his/her workload is full.

LeaveManager: allow an employee to request leave after a certain time. if permitted, marks the employee for leave, cancels events during that time, and prevents scheduling during that time.

SalaryEvaluation: based on workload, title, leave to evaluate employee’s salary monthly. Workload is the number of hours that employee work on; title is the position of employee( like manager 

(if time -- EmployeeEvaluation: decision of promotion, salary increase, year-end bonus)

Controllers

：
UI:
interface: employee contract (might be?)

Database class(es) implementing the interface for serialization/deserialization. SQLite backend.


