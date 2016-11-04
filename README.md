[![Build Status](https://travis-ci.org/Oupsla/BtrShop-android.svg?branch=master)](https://travis-ci.org/Oupsla/BtrShop-cloud)

# BtrShop-android

Android application for the project `Recommendation in situ de produits en magasins` submitted by Université Lille 1 in collaboration with the Inria and the team Spirals.

## Authors
- Nicolas Delperdange
- Denis Hamann
- Charlie Quetstroey

## Purpose

The purpose of this application is to scan an ean barcode, and retrieve via the api server the data of the product.


## Architecture

This project use the MVP CLEAN ARCHITECTURE (BLUEPRINTS) :

<img src="https://github.com/googlesamples/android-architecture/wiki/images/mvp-clean.png" alt="Diagram"/>


* **MVP**: Model View Presenter pattern from the base sample.
* **Domain**: Holds all business logic. The domain layer starts with classes named *use cases* or *interactors* used by the application presenters. These *use cases* represent all the possible actions a developer can perform from the presentation layer. 
* **Repository**: Repository pattern from the base sample.  


## Tests

Tests are written with Espresso.

## Screenshots

![screenshot main activity](https://www.pictshare.net/4f04291889.jpg)

![screenshot scan a product](https://www.pictshare.net/8e39bf4bc7.jpg)

![screenshot error no product found](https://www.pictshare.net/6f4662ba44.jpg)

![screenshot product detail 1](https://www.pictshare.net/23f53dee08.jpg)

![screenshot product detail 2](https://www.pictshare.net/1ef98ee3b4.jpg)

