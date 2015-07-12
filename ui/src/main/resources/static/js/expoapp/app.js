'use strict';

var ang=angular.module('ExpoApp',['ngResource', 'ui.router']);

ang.run(function ($rootScope,$state,ENV, VERSION,GATEWAY_URL,OAUTH_URL) {
    $rootScope.ENV = ENV;
    $rootScope.VERSION = VERSION;
    $rootScope.GATEWAY_URL=GATEWAY_URL;
    $rootScope.OAUTH_URL=OAUTH_URL;
    //$state.go('home');
})

ang.config(function ($stateProvider,$urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home', {
            url: '/',
            views: {
                'content@': {
                    templateUrl: 'js/expoapp/categorys.html',
                    controller: 'Ctrl'
                }
            }
        });
});

ang.factory('Category', function ($resource,$rootScope) {
    return $resource($rootScope.GATEWAY_URL + '/api/product/categorys/:id', {}, {
        'query': {method: 'GET', isArray: true},
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        },
        'update': {method: 'PUT'}
    });
});

ang.controller('Ctrl', ['$scope', 'Category','$rootScope',
    function($scope, Category,$rootScope) {
        $scope.categorys = [];
         Category.query(function(result) {
            $scope.categorys = result;
            $scope.categorys.forEach(function(cat){
                cat.imgurl=$rootScope.GATEWAY_URL+'/api/product/imgget/categorys/'+cat.id+'/image/'+new Date().getTime().toString();
            });
        });
     }]);
