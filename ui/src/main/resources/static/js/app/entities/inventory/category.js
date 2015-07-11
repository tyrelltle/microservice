'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('category', {
                parent: 'entity',
                url: '/category',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'zimoloApp.category.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'js/app/entities/inventory/categorys.html',
                        controller: 'CategoryController'
                    }
                }
            })
            .state('categoryImport', {
                parent: 'entity',
                url: '/category/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'zimoloApp.category.import.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'js/app/entities/inventory/category-import.html',
                        controller: 'CategoryImportController'
                    }
                }
            })
            .state('categoryDetail', {
                parent: 'entity',
                url: '/category/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'zimoloApp.category.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'js/app/entities/inventory/category-detail.html',
                        controller: 'CategoryDetailController'
                    }
                }
            })
            .state('product', {
                parent: 'entity',
                url: '/product/:category_id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'zimoloApp.product.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'js/app/entities/inventory/products.html',
                        controller: 'ProductController'
                    }
                }

            })
            .state('productDetail', {
                parent: 'entity',
                url: '/product/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'zimoloApp.product.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'js/app/entities/inventory/product-detail.html',
                        controller: 'ProductDetailController'
                    }
                }
            });
    });
