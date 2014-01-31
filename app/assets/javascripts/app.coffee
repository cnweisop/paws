'use strict'

paws = angular.module('paws', [
  'ngRoute'
  'users'
])

paws.config [
  '$routeProvider',
  ($routeProvider) ->
    $routeProvider.when '/index',
      templateUrl: 'assets/partials/index.tpl.html'
    $routeProvider.otherwise
      redirectTo: '/index'
]

paws.directive 'pawsInput', () ->
  restrict: 'E'
  replace: 'true'
  scope:
    name: '@'
    model: '='
    label: '@'
    placeholder: '@'
    size: '@'
    type: '@'
    value: '@'
    required: '@'
    errors: "="
  templateUrl: 'assets/partials/input.tpl.html'

paws.directive 'pawsSubmit', () ->
  restrict: 'E'
  scope:
    class: '@'
    value: '@'
  templateUrl: 'assets/partials/submit.tpl.html'

paws.directive 'pawsButton', () ->
  restrict: 'E'
  scope:
    click: '@'
    value: '@'
  templateUrl: 'assets/partials/button.tpl.html'

paws.directive 'pawsHeader', () ->
  restrict: 'E'
  scope:
    h1: '@'
    h2: '@'
    lead: '@'
    subtext: '@'
  templateUrl: 'assets/partials/header.tpl.html'

paws.directive 'pawsNav', () ->
  restrict: 'E'
  templateUrl: 'assets/partials/navigation.tpl.html'