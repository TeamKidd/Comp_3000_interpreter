This week’s task


Your task this week is to work out how something like a plus symbol allows you to describe the
way rivers combine into other rivers.


Keep in mind that you are working towards an assignment submission based on this work and
the more your team gets done in class, the easier your job is! This week we would like you to
create (and to show at the end of class)


• Expressions in your chosen notation/language which describe some example river systems.
• Be able to talk through the execution of those trees in theory
• A parser in Nystrom’s CFG notation that extends Lox’s parser to cover your language1


River addition:

river1 & river2

Default Interaction:
Flow is combined but can be modified:

(river1 & river2)

rivers are pre defined

<!-- rivers normally defined as

river_name{
    flow : 1 // In litres per second

} -->

literals are going to be:
river1 = {F: 1, R: .5}
river2 = {F: .5, R: .25}

Where:
F is flow
R is Rainfall

river3 = river1 & river2
We add flow, set R to 0

add R using:
river3{R: .5}

access values using:
river3{R}

global functions to
process step

() -> process day
(1) -> 1 day