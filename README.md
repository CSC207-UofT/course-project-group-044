# HR SCHEDULING SYSTEM - group 044
**PHASE 2 Changes in design_document.md

**Universal Design in accessibility.md

## Specification

The HR scheduling system manages scheduling for an organization: shifts and meetings for employees. It maintains a roster of employees at the organization (which may be modified as employees join or leave the organization), along with scheduling constraints. The Manager of the Organization can assign employees work as Event according to specific rules:

+ Minimum and maximum number of hours stipulate ceiling and floor that employees may work a week.
+ Minimum and maximum number of employees represent max and min number of employees working at a given time/event. This is meaningful since an insufficient number of employees can’t get the work done, while an excessive number of employees would cause redundancy.
+ Leave tracking -- avoid scheduling an employee on holiday
+ Scheduling contiguously where possible.

These scheduling requirements translate to requirements for the general employee management: the number of working hours and leave of absence time relates to the salary of the employee (part time vs full time), the workplace requirements is a property of the organization.

All persistent state (employee data, schedules, and so on) must be serialized to an on-disk database as it is updated, and loaded from the database on start-up, to guarantee data integrity even if the system fails.


## Scenario Walk-Through
  
When a programmer Jack wants to enter a new company, the HR manager will firstly accept his application by EmployeeManager. This operation will certainly call HireEmployee to work. After entering Jack’s name, salary, worktime, and ID, the system will automatically initialize his work_time to 0. Then, the manager will assign events to Jack by  the SchedularManager. This com.hr.controller will call several use case classes, which allow the manager not only to create a new event by time, location, duration, and event’s name for Jack to do, but also assign other events that have already existed to Jack. When the weekly meeting is held, another special event called “Meeting” is created by using additional names of attenders and the meeting holder and then was assigned to Jack. When Jack’s first month in this company comes to an end, the HR manager will calculate his salary by using SalaryManager. Since Jack cannot do his work very well, the HR manager is not going to assign him much work. As a result, Jack’s work_time is very low. What’s worse, the SalaryEvaluation called by SalaryManager calculates one employee’s salary only according to his work_time, Jack only gets a little. According to this, the HR manager is sad and decides to fire Jack. By raising FireEmployee called by EmployeeManager, Jack’s schedulable is set to false, and he was announced to hand over his work in a few days. All the events sent to him after a few days are reassigned to others. After that, he was finally fired.


## Progress Report
The HR scheduling system adds events to a calendar among certain rules based on the target employee’s max or min of work hours and number of employees, enabling employees to join and exit the event, also evaluating their salaries. 

By clean architecture, there are four layers in HR systems, **external interface -> com.hr.controller -> use case classes -> com.hr.entity**, we implement systems by first implementing our command line **(external interface)** to access EmployeeManager, SchedulerManager and SalaryManager **(com.hr.controller)**,then com.hr.controller will call schedule class **(use case classes)** to further access to our **com.hr.entity**, where our class follows SOLID principle.

We have finished a brief description of Classes **(com.hr.entity classes, user classes, com.hr.controller)** with all constructor and methods covered. All relationships between each class and their function are clearly indicated in CRC cards and each CRC card only gets called from the upper layer and provides a path to access the lower layer.

From our Scenario Walk-Through, Human resources managers can use three different types of management systems to fire or hire employees, placing different events to employees, and calculating each employees salary based on their workload. Through the story of Jack, Jack gets hired through HireEmploy called by EmployeeManager. His work term is scheduled and assessed by SchedulerManager and Salary Manager and eventually he is fired by FireEmployee called by EmployeeManager. Through this story, we demonstrate the whole process of how an employee enters, works and leaves in one company.

open question:
one issue our group is currently struggling with is whether we need to create different classes to separate createEvent and deleteEvent from class Scheduler in use case classes to satisfy our single responsibility principle(SRP).  By single responsibility principle, every class can only have one responsibility. However, the disagreement of our group is whether “createEvent” and “deleteEvent” counted for a responsibility. For example, it is obvious that creating an event and deleting os belongs to different functions. However, if we count the ability of shifting employees(create and delete events both have the meaning of shifting employees) as a responsibility, it follows SRP. So it is a little vague about the concept of SRP principle in our cases and we have not figured it out.

In our design, after several group discussions, we now have a clear perspective about how our HR system follows both clean architecture and SOLID principles. In our specification, we let the outer layer only implement the inner layer and fully explain what our HR system does, what classes they have and which method they are implementing. Moreover, we have written all CRC cards based on the description of the class in specification. Also we show how the HR manager uses this system to convenient their daily work. And eventually we implement our design into the Skeleton Program to let the description become true.

## Group Work Distribution:
**Yuxiang Lin**: Finish Scenario Walk-Through and help fix some parts in CRC cards. Next will focus on designing parts of com.hr.entity Classes.<br>
**Kunhan Duan**: make a summary in progress report. coming up with an open question Also adding and editing some parts  on CRC cards, Scenario Walk-Through. Finishing part of controllers and command line function. planning to work on the Skeleton Program to implement our HR system.<br>
**Jiawei Chen**: Scheduling in-person meetings. Define classes and clarify which layer those classes belong to based on CLEAN Architecture.<br>
**Haitao Zeng**: Working on CRC model, discussing specification part with teammates, and com.hr.entity part of skeleton program. I may work on com.hr.entity class or use case class, in the future.<br>
**Alyssa Rosenzweig**: Skeleton program, most of the specification.<br>
**Xinglin Chen**: most part of CRC model, refining the specification, editting README. Later, may deal with Use Case EmployeeManager, and com.hr.controller EmployeeModifier etc.




