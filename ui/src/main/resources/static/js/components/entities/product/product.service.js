'use strict';

angular.module('jhipsterApp')
    .factory('Product', function ($resource, DateUtils) {
        return $resource('http://localhost:8765/api/product/products/:id', {}, {
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
