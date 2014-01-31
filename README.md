# Play AngularJs Webapp Seed

First and foremost, this is currently in early alpha stages. Take as is.

## Setup and Notes

- By default in play-domain models.sorm.SormDb is set to initMode = InitMode.DoNothing, change this to initMode = InitMode.Create to initialize the database
- The smtp server is set to mock, so the outgoing emails will be logged in the console

## Updates

- 2014-01-30: First pure AngularJs build
- 2014-01-24: Split the project into multiple modules. Views do not work!
- 2014-01-21: Converted most screens to use AngularJs instead of full page reloads

## Goals

- Create a generic starter template for user based websites
- Security and data layer
- Use less for css
- Admin pages
- Customize the outgoing securesocial emails
- Customize the social providers (twitter, facebook, etc.) sign up and login piece.

## Technologies Used

- [Scala 2.10](http://www.scala-lang.org/)
- [Play 2.2](http://www.playframework.com/)
- [SORM](https://github.com/sorm/sorm)
- [SecureSocial](https://github.com/jaliss/securesocial)
- [AngularJs 1.2](http://angularjs.org/)
- [Bootstrap 3.1](http://getbootstrap.com/)
- [CoffeeScript](http://coffeescript.org/)

## Known Issues

- Password pages do not work
- tons of others

## In Progress

- Dynamic navigation menu
- Password pages
- Admin pages
- Break out core data layer into a sub-project
- Develop tests
- Move database creation to the application.conf

## Generic Disclaimer

This SOFTWARE PRODUCT is provided by THE PROVIDER "as is" and "with all faults." THE PROVIDER makes no representations or warranties of any kind concerning the safety, suitability, lack of viruses, inaccuracies, typographical errors, or other harmful components of this SOFTWARE PRODUCT. There are inherent dangers in the use of any software, and you are solely responsible for determining whether this SOFTWARE PRODUCT is compatible with your equipment and other software installed on your equipment. You are also solely responsible for the protection of your equipment and backup of your data, and THE PROVIDER will not be liable for any damages you may suffer in connection with using, modifying, or distributing this SOFTWARE PRODUCT.
