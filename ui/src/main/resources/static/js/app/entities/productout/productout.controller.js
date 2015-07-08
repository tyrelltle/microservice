'use strict';

angular.module('jhipsterApp')
    .controller('ProductOutController', function ($scope, ProductCheckoutServices, ParseLinks, $modal) {
        $scope.checkout_state={
            sku:-1
        };
        $scope.products = [];
        $scope.page = 1;

        $scope.loadAll = function() {
            ProductCheckoutServices.query(function(result,link) {
                $scope.links = ParseLinks.parse(link);
                for (var i = 0; i < result.length; i++) {
                    $scope.products.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.products = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.showUpdate = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#saveProductModal').modal('show');
            });
        };



        $scope.checkout = function (how) {

            if(how!='sku' && how!='qr')
                throw new Error('what the ...');

            var modalInstance = $modal.open({
                templateUrl: 'qr_scanner_modal',
                controller:'QrModalCtrl',
                size:'lg',
                resolve:{
                    how:function(){
                        return how;
                    }
                }
            });


            modalInstance.result.then(function (sku) {
                ProductCheckoutServices.checkout(sku, function () {
                    alert('success!');
                    $scope.refresh();
                });
            });



         };


        $scope.refresh = function () {
            //$scope.reset();
            $('#saveProductModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.product = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };



     });

angular.module('jhipsterApp').controller('QrModalCtrl', ['$scope','$modalInstance','how',
    function ($scope,$modalInstance,how) {
        $scope.sku=undefined;
        $scope.how=how;
        if(how=='qr') {
            qr_init('video', function (qr) {
                qr_stop('video');
                $scope.sku=qr;
                $scope.$apply();
            });
        }


        $scope.comfirm=function(){
            $modalInstance.close($scope.sku);
        }

        $scope.cancell=function(){
            $modalInstance.dismiss();
        }
    }]);
