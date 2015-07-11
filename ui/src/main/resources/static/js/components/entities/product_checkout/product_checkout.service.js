'use strict';

angular.module('jhipsterApp')
    .service('ProductCheckoutServices',['$http','$rootScope',function($http,$rootScope){

        this.query=function(cb){
            var url=$rootScope.GATEWAY_URL+'/api/product/product_checkout/list';
            $http.get(url).success(function(data, status, headers, config){
                cb(data,headers().link);
            });
        };

        this.checkout=function(sku,cb){
            var url=$rootScope.GATEWAY_URL+'/api/product/product_checkout/'+sku;
            $http.post(url).success(function(data){
                if(!data.type) {
                    alert(data.error);
                    return;
                }
                cb(data.data);
            });
        };

    }]);

