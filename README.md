# Stockbit Assimgnet

An Android app consuming a [Crypto Compare API](https://min-api.cryptocompare.com/) to Cryptocurrency market price
it has been built with clean architecture principles, Repository Pattern and MVVM
pattern as well as Architecture Components.

## Table of Contents

- [Architecture](#architecture)
- [Libraries](#libraries)

## Architecture

The Application is split into a three layer architecture:
- Presentation
- Domain
- Data

![Architecture Flow Diagram](img/arch_flow.png)

This provides better abstractions between framework implementations
and the underlying business logic.It requires a number of classes to get
things running but the pros outweigh the cons in terms of building an app
that should scale.

The 3 layered architectural approach is majorly guided by clean architecture which provides
a clear separation of concerns with its Abstraction Principle.

#### Presentation

```app``` contains the UI files and handles binding of DI components from other modules.
Binding of data is facilitated by jetpacks data binding by serving data from the viewmodel
to the UI.The data being received is part of a viewstate class that has properties contained in the
relevant state.

#### Domain

The ```domain``` module contains domain model classes which represent the
data we will be handling across presentation and data layer.

Use cases are also provided in the domain layer and orchestrate the flow
of data from the data layer onto the presentation layer and a split into
modular pieces serving one particular purpose.

The UseCases use a ```BaseUseCase``` interface that defines the parameters its taking in and
output this helps in creating fakes using in testing.

#### Data

- ```data-remote```

Handles data interacting with the network and is later serverd up to the presentation layer through
domain object

- ```data-local```

Handles persistence of object with Room ORM from.This module is responsible for handling all local related
logic and serves up data to and from the presentation layer through domain objects.

With this separation we can easily swap in new or replace the database being used without causeing
major ripples across the codebase.