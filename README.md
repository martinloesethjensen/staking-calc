# Staking Calculator

This is a simple staking calculator that imitates staking a crypto-currency and then has the option of redeeming rewards if any.

The application is only to showcase some utilization of software design patterns.

## Idea

As a HODLer I want to grow my crypto portfolio and one way of doing that is through staking tokens that you already have and then earn rewards after a period.

## Patterns

This application need to use at least 4 patterns from the GoF, one from each pattern category; **creational**, **behavioral**, and **structural**.
Then one more of own choice.

### Command Pattern

The Command Pattern is a Behavioral Pattern.

With the Command Pattern, we can encapsulate a request as an object and decouple the request from its invoker.
We use this code in our staking_command.kt file, where we have requests for both staking and claiming tokens and we have the implementation of the command processor in
base_command where "addToQueue" and "processCommands" live, both methods for adding to the command queue and processing one or multiple commands.

Command is good for our application, because of the decoupling and encapsulation it provides, where some of our most important functionality lives, is invaluable.
Ironically, the Command Pattern is also known as "Transaction", which fits perfectly with the usage it has to handle this transaction functionality in our program.

### Facade Pattern

The Facade Pattern is a Structural Pattern.

With a facade we can keep complexity from the client, so that the client calling the code won't know of any subsystems that might exist.

We use this in our code for our "store" which is a subsystem that handles the logic for staking funds and claiming rewards.
The facade is then a "repository" with access to subsystems and their logic, but without exposing them and their lifecycles.
It can be thought of like a layer that links together subsystems and exposes them to the client.

A facade is good for this kind of application because if changes need to be made on the underlying subsystem logic, without changes to the client code.
The client will still call the same code, so the system would achieve low coupling.

If there were to be made another subsystem for handling oracles to fetch prices on tokens sort of like an API.
Then another subsystem would be made and there would either be made another "repository", or the same "repository"/facade would handle the lifecycle of the new subsystem.

### Factory Method Pattern

The Factory Method Pattern is a Creational Pattern.

By using the Factory Method Pattern we can decouple choice of instantiation to subclases, in this case we use a when conditional inside of protocol_factory.kt.
Our reasoning is that it is simple enough for our relatively easy choices, as they are nothing too complex our vary wildly from each other. If that was the case, perhaps Builder would have been a better choice.
In the code it is used to select and instantiate a protocol object and/or APY digit, based on the tokenType input.

>Singleton and Null Object patterns are also being used in this project, but they are not worth writing about.
