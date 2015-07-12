'use strict';

angular.module('jhipsterApp')
    .controller('ProductController', function ($scope, Product, ParseLinks,$stateParams) {
        $scope.category_id=$stateParams.category_id;
        $scope.products = [];
        $scope.page = 1;

        $scope.loadAll = function() {
            Product.query({page: $scope.page, per_page: 20, category_id:$scope.category_id}, function(result, headers) {
                if(headers==undefined) return;
                $scope.links = ParseLinks.parse(headers('link'));
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
            //$scope.page = page;
            //$scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#saveProductModal').modal('show');
            });
        };

        $scope.$watchGroup(['product.height', 'product.width','product.unit_price'], function(newValues, oldValues, scope) {
            if($scope.product==undefined)
                return;
            $scope.product.price=(+ newValues[0] * +newValues[1] )/144 * +newValues[2];
            $scope.product.price=Math.floor(100 *  $scope.product.price) / 100;
        });

        $scope.print=function(){
                var dataUrl = document.getElementsByClassName('qrcode_canvas')[0].toDataURL(); //attempt to save base64 string to server using this var
                var windowContent = '<!DOCTYPE html>';
                windowContent += '<html>'
                windowContent += '<head><title>Print QRCODE</title></head>';
                windowContent += '<body>'
                windowContent += '<img src="' + dataUrl + '">';
                windowContent += '</body>';
                windowContent += '</html>';
                var printWin = window.open('','','width=200,height=200');
                printWin.document.open();
                printWin.document.write(windowContent);
                printWin.document.close();
                printWin.focus();
                printWin.print();

        }
        $scope.save = function () {
            if ($scope.product.id != null) {
                Product.update($scope.product,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Product.save($scope.product,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#deleteProductConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteProductConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveProductModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.product = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
