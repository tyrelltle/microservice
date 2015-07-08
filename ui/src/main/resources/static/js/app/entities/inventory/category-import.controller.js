'use strict';

angular.module('jhipsterApp')
    .controller('CategoryImportController', function ($scope, $stateParams, CategoryServices) {
        $scope.quantity= undefined;
        //$scope.products=[{_id:'asd',name:'asd'},{_id:'asd',name:'asd'},{_id:'asd',name:'asd'}];
        $scope.products=[];

        $scope.category_id=$stateParams.id;
        $scope.import=function(){
            CategoryServices.importProduct($scope.category_id, $scope.quantity,function(data){
                if(!(data instanceof Array))
                    alert("wow! this is not a fucking array! "+data);
                else
                    $scope.products=data;
            });
        }

        $scope.printqr=function(){
            var lastindex = ($scope.products.length - 1);
            window.open('/qrcode/'+$scope.products[0].sku+'/'+$scope.products[lastindex].sku);
        }

    });
