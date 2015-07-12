'use strict';

angular.module('jhipsterApp')
    .controller('MainController', function ($scope, Principal,$state) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            if(!$scope.isAuthenticated())
                $state.transitionTo('login');
        });
    });
