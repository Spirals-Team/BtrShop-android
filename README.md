[![Build Status](https://travis-ci.org/Oupsla/BtrShop-android.svg?branch=master)](https://travis-ci.org/Oupsla/BtrShop-cloud)

# BtrShop-android

Android application for the project `Recommendation in situ de produits en magasins` submitted by Université Lille 1 in collaboration with the Inria and the team Spirals.

## Authors
- Nicolas Delperdange
- Denis Hamann
- Charlie Quetstroey

## Purpose

The purpose of the application is to retrieve a recommendation of different products scanned.

We will explain you step by step how the application works.

### Screenshots

The main activity is a list of recommended product. If the list is empty, it displays an empty list. The user have to swipe to refresh the list.

<img src="https://www.pictshare.net/300/564d728401.png" style="width:250px;" alt="Main activity"/>

If the user click on the button with the scanner, there is an
screen with a scanner to allow the user to scan.

<img src="https://www.pictshare.net/300/60cf8e6e5b.png" alt="Scanner" style="width:250px;"/>

When the user have scanned his product, the application shows two kind of view.

The loading

<img src="https://www.pictshare.net/300/50657b87a8.png" alt="Loading" style="width:250px;"/>

The error with a message

<img src="https://www.pictshare.net/300/ca8def997d.png" alt="Error" />

When the loading is finished, the application display the products and send the differents beacons on the server.

<img src="https://www.pictshare.net/300/5ecfdcc9cb.png" alt="Product"/>

<img src="https://www.pictshare.net/300/c76b8d5d76.png" alt="Product"/>


## Architecture

This project use the MVP CLEAN ARCHITECTURE (BLUEPRINTS) :

<img src="https://github.com/googlesamples/android-architecture/wiki/images/mvp-clean.png" alt="Diagram"/>


* **MVP**: Model View Presenter pattern from the base sample.
* **Domain**: Holds all business logic. The domain layer starts with classes named *use cases* or *interactors* used by the application presenters. These *use cases* represent all the possible actions a developer can perform from the presentation layer. 
* **Repository**: Repository pattern from the base sample.  


## Tests

Tests are written with Espresso.
