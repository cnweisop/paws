'use strict'

paws = angular.module('paws', [
  'ngRoute'
  'users'
])

paws.config [
  '$routeProvider',
  ($routeProvider) ->
    $routeProvider.when '/',
      templateUrl: 'assets/partials/index.tpl.html'
    $routeProvider.otherwise
      redirectTo: '/'
]

paws.controller 'NavigationCntl',
  class NavigationCntl
    constructor: ($scope, $http) ->

      $scope.default = ->
        $http.get('default-navigation')
        .success (response) ->
            $scope = response

paws.directive 'pawsInput', ->
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

paws.directive 'pawsForm', ->
  restrict: 'E'
  scope:
    controller: '='
    submit: '='
  
paws.directive 'pawsSubmit', ->
  restrict: 'E'
  scope:
    class: '@'
    value: '@'
  templateUrl: 'assets/partials/submit.tpl.html'

paws.directive 'pawsButton', ->
  restrict: 'E'
  scope:
    click: '@'
    value: '@'
  templateUrl: 'assets/partials/button.tpl.html'

paws.directive 'pawsHeader', ->
  restrict: 'E'
  scope:
    h1: '@'
    h2: '@'
    lead: '@'
    subtext: '@'
  templateUrl: 'assets/partials/header.tpl.html'

paws.directive 'pawsNav', ->
  restrict: 'E'
  replace: 'true'
  scope:
    nav: '='
  templateUrl: 'assets/partials/navigation.tpl.html'