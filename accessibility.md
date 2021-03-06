# Principles of Universal Design

<!-- For each Principle of Universal Design, write 2-5 sentences or point form
notes explaining which features your program adhere to that principle. If you
do not have any such features you can either:

    (a) Describe features that you could implement in the future that would adhere to principle or

    (b) Explain why the principle does not apply to a program like yours. -->

**Equitable Use**: By developing a web app with a simple user interface and
semantic HTML, any user with web access can access our program. The program is
automatically accessible to users of screen readers and other standard
accessibility technologies (like sip and puff systems) which a user might have
for their web browser. The color scheme can be changed to accomodate people with disabilities. For now we have demo our project changing the color to help people
with red-green Color blindness recognize our services

**Flexibility in Use**: This principle is automatic with an unopinionated,
standards-compliant web app. The application is accessible to users of any
major operating system, both desktop and mobile, and any custom theming the
user might have (e.g. a system-wide dark theme applied to their browser,
increased font size, etc) would be applied without requiring special
application-specific settings.

**Simple and Intuitive Use**: This principle requires a clean design. Following
web best practices "ought" to have this principle come for free; unfortunately,
designing intuitive applications is an art rather than a science, and cannot be
distilled to a list of rules like screen reader accessibility. There is a
special aspect of this principle, however -- accessibility to people of
different language abilities. Adding internationalization and localization
support to our app would be a feature we would implement in the future to adapt
our program to non-Canadian markets and Canadians whose first language is not
English. Thanks to clean architecture, this change is localized to the front-end
and would be easily facilitated with the internationalization support in the
templating library we use.

**Perceptible Information**: This principle requires a clean style sheet for
the web program. Ironically, screen readers are the easy case here: provided we
are screen reader accessible (per Equitable Use), we're done for users of
screen readers. For sighted users, this principle requires effective use of
semantic HTML and well-designed CSS. This goes hand-in-hand with simple and
intuitive use; a clean design is inherently easier for people regardless of
sensory abilities.

**Tolerance for Error**: This principle requires "Undo" and confirmation
functionality in our program. Currently, we do not support these features;
operations like hiring and firing are permanent and immediate, violating this
principle. Fixing this by adding in an undo (making it possible to rollback all
operations) and confirmation boxes for sensitive operations (like firing) would
improve the program's tolerance for error. These should be added in the future
to improve accessibility.

**Low Physical Effort**: This principle does not directly apply to a
software-only project like ours. The main consideration is providing a simple
interface which minimizing clicks and typing needed, and allows the use of both
mice or keyboards (via keyboard shortcuts and on-screen buttons) as necessary.
Some people are able to use one input device but not the other due to various
physical disabilities, or can use one more comfortable; a standards-compliant
benefits from browser accessibility work in supporting both input methods.

**Size and Space for Approach and Use**: Like Low Physical Effort, this
principle does not apply directly. Respecting the usual browser defaults on
colour contrast and font size facilitate this principle indirectly; this follows
from the flexibility of use principle above.

# Target audience

<!--
    Write a paragraph about who you would market your program towards, if you
were to sell or license your program to customers. This could be a specific
category such as "students" or more vague, such as "people who like games". Try
to give a bit more detail along with the category.
-->

Our HR scheduling software is intended for use by the managers of small
businesses with shift-based scheduling, for example the manager of a coffee
shop. The program's core feature is automatically scheduling shifts according
to real world constraints; for this functionality to be useful requires
multiple employees with nontrivial constraints. If a business is too small or
does not have employees work fixed hourly shifts, the functionality is not
necessary. Conversely if the business is too large, the requirement complexity
may exceed what can be handled by any off-the-shelf program, ours included;
although these companies are a lucrative market, they are out-of-scope for
small scale software like ours.

# Demographics

<!--
    Write a paragraph about whether or not your program is less likely to be
used by certain demographics. For example, a program that converts txt files to
files that can be printed by a braille printer are less likely to be used by
people who do not read braille.
-->

The program is specifically for managers' use. Scheduling and employee
management are top-down operations affecting the entire organization; this
reflects the relatively flat hierarchy of a small business. A larger business
would require finer access controls than are implemented in our program,
whereas individual employees will not directly use this enterprise software.
There is a caveat: the _results_ of our program (namely, the shift schedule for
a week) must be accessible to *all* employees at the organization, not only the
manager. Thus the accessibility requirements are firmer for this aspect of the
program. There are no particular demographics that are more likely to be in
either management or individual employee roles; as such, accessibility of these
components defers to general web accessibility standards. (For example, both
the web app and the output shift schedule should use semantic HTML compliant
with relevant accessibility standards, so that screen readers are first-class.)
