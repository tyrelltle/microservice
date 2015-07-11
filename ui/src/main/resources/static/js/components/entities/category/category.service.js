'use strict';

angular.module('jhipsterApp')
    .factory('Category', function ($resource, DateUtils,$rootScope) {
        return $resource($rootScope.GATEWAY_URL+'/api/product/categorys/:id', {}, {
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
    }).service('CategoryServices',['$http','$rootScope',function($http,$rootScope){

        this.importProduct=function(category_id,import_quantity,cb){
            var url=$rootScope.GATEWAY_URL+'/api/product/categorys/import';
            $http.post(url,{category_id:category_id, import_quantity:import_quantity}).success(function(data){
                cb(data);
            });
        };

    }]);
