'use strict'

user = angular.module('users', [])

user.config [
  '$routeProvider',
  ($routeProvider) ->
    $routeProvider.when '/home',
      templateUrl: 'assets/users/partials/home.tpl.html'
    $routeProvider.when '/login',
      templateUrl: 'assets/users/partials/login.tpl.html'
    $routeProvider.when '/signup',
      templateUrl: 'assets/users/partials/signup-start.tpl.html'
    $routeProvider.when '/signup/:token',
      templateUrl: 'assets/users/partials/signup.tpl.html'
    $routeProvider.when '/reset',
      templateUrl: 'assets/users/partials/reset-start.tpl.html'
    $routeProvider.when '/reset/:token',
      templateUrl: 'assets/users/partials/reset.tpl.html'
    $routeProvider.otherwise
      redirectTo: '/home'
]

user.controller 'SignUpCntl',
  class SignUpCntl
    constructor: ($scope, $http, $location, $routeParams) ->
      $scope.form = {} if $scope.form is undefined

      $scope.generateName = ->
        $http.get('users/generate-name')
        .success (response) ->
            $scope.form.firstName = response.firstName
            $scope.form.lastName = response.lastName

      $scope.getEmail = ->
        $http.get('users/email/' + $routeParams.token)
        .success (response) ->
            $scope.form.email = response.email
            $location.path("login") if response.email = null

      $scope.sendEmail = ->
        $http.post('users/signup', $scope.form)
        .success () ->
            $location.path("login")
        .error (response) ->
            $scope.form.errors = response

      $scope.signUp = ->
        $http.post('users/signup/' + $routeParams.token, $scope.form)
        .success () ->
            $location.path("login")
        .error (response) ->
            $scope.form.errors = response

            if (response["password"])
              $scope.form.errors["password.password2"] = response["password"]

user.controller 'LoginCntl',
  class LoginCntl
    constructor: ($scope, $http, $location) ->
      $scope.form = {} if $scope.form is undefined

      $scope.login = ->
        $http.post('users/authenticate/userpass', $scope.form)
        .success () ->
            $location.path("home")
        .error (response) ->
            $scope.form.errors = response

      $scope.logout = () ->
        $http.get('users/logout')
        .success(() -> $location.path("/login"))
        .error(() -> $location.path("/login"))

user.controller 'PasswordCntl',
  class PasswordCntl
    constructor: ($scope, $http, $location, $routeParams) ->
      $scope.form = {} if $scope.form is undefined

      $scope.sendEmail = ->
        $http.post('users/reset', $scope.form)
        .success () ->
            $location.path("login")
        .error (response) ->
            $scope.form.errors = response

      $scope.reset = ->
        $http.post('users/reset/' + $routeParams.token, $scope.form)
        .success () ->
            $location.path("login")
        .error (response) ->
            $scope.form.errors = response

            if (response["password"])
              $scope.form.errors["password.password2"] = response["password"]
