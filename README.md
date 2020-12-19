# Staking Calculator

This is a simple staking calculator that imitates staking a crypto-currency and then has the option of redeeming rewards if any.

The application is only to showcase some utilization of software design patterns. 

## Idea

As a HODLer I want to grow my crypto portfolio and one way of doing that is through staking tokens that you already have and then earn rewards after a period.

## Patterns 

This application need to use at least 4 patterns from the GoF, one from each pattern category; **creational**, **behavioral**, and **structural**.
Then one more of own choice.

### Command Pattern

The command pattern is a behavioral pattern.

TODO: Write about this pattern and why it is good in this application

### Facade Pattern

The facade pattern is a structural pattern.

With a facade we can keep complexity from the client, so that the client calling the code won't know of any subsystems that might exist.

We use this in our code for our "store" which is a subsystem that handles the logic for staking funds and claiming rewards. 
The facade is then a "repository" with access to subsystems and their logic, but without exposing them and their lifecycles.
It can be thought of like a layer that links together subsystems and expose them to the client.

A facade is good for this kind of application because if changes need to made to the underlying subsystem logic, without changes to the client code.
The client will still call the same code, so the system would achieve low coupling. 

If there were going to be made another subsystem for handling oracles to fetch prices on tokens sort of like an API. 
Then another subsystem would be made and there would either be made another "repository", or the same "repository"/facade would handle the lifecycle of the new subsystem. 

### Factory Method Pattern

The factories method pattern is a creational pattern.


-----
Singleton and Null Object patterns are also being used in this project 