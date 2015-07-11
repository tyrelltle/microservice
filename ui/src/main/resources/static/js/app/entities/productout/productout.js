'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productout', {
                parent: 'entity',
                url: '/productout',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'zimoloApp.category.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'js/app/entities/productout/productout.html',
                        controller: 'ProductOutController'
                    }
                }
            });

    });
