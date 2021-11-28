<!-- For each Principle of Universal Design, write 2-5 sentences or point form
notes explaining which features your program adhere to that principle. If you
do not have any such features you can either:

    (a) Describe features that you could implement in the future that would adhere to principle or

    (b) Explain why the principle does not apply to a program like yours. -->


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
