'use strict';

angular.module('jhipsterApp')
    .factory('Product', function ($resource, DateUtils,$rootScope) {
        return $resource($rootScope.GATEWAY_URL+'/api/product/products/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
