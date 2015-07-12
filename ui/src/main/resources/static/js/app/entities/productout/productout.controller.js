'use strict';

angular.module('jhipsterApp')
    .controller('ProductOutController', function ($scope, ProductCheckoutServices, ParseLinks, $modal) {
        $scope.modes={
            new:{
                title:"Scan product to checkout",
                products:[]
            },
            hist:{
                title:"Historical Checked Out Products",
                products:[]
            }
        }

        $scope.radioModel="hist";

        $scope.currentmode=$scope.modes.hist;

        $scope.switchToNewMode=function(){
            $scope.currentmode=$scope.modes.new;
            $scope.radioModel="new";
        }
        $scope.switchToHistMode=function(){
            $scope.currentmode=$scope.modes.hist;
            $scope.radioModel="hist";
            $scope.loadHistCheckouts();
        }

        $scope.clearNewProducts=function(){
            $scope.modes.new.products=[];
        }

        $scope.$watch('radioModel', function(newValues, oldValues, scope) {
           if(newValues=="new") {
               $scope.switchToNewMode();
           }else if(newValues=="hist"){
               $scope.switchToHistMode();
           }else{
               alert("weird radio model state!");
           }
         });

        $scope.loadHistCheckouts = function() {
            ProductCheckoutServices.query(function(result,link) {
                $scope.links = ParseLinks.parse(link);
                for (var i = 0; i < result.length; i++) {
                    $scope.modes.hist.products.push(result[i]);
                }
            });
        };
        $scope.switchToHistMode();
        $scope.checkout_state={
            sku:-1
        };
        $scope.page = 1;


        $scope.reset = function() {
            $scope.page = 1;
            $scope.modes.new.products = [];
            $scope.modes.hist.products = [];
            //$scope.loadHistCheckouts();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            //$scope.loadHistCheckouts();
        };




        $scope.checkout = function (how) {
            $scope.currentmode=$scope.modes.new;

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
                ProductCheckoutServices.checkout(sku, function (data) {
                    alert('success!');
                    $scope.modes.new.products.unshift(data);
                });
            });



         };


        $scope.refresh = function () {
            $scope.reset();
            $('#saveProductModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.product = {name: null, id: null};
           // $scope.editForm.$setPristine();
           // $scope.editForm.$setUntouched();
        };

        $scope.print=function(){
            var mywindow = window.open('', 'my div', 'height=400,width=600');
            mywindow.document.write('<html><head><title>my div</title>');
            /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
            mywindow.document.write('</head><body >');
            mywindow.document.write($('.prodtab').html());
            mywindow.document.write('</body></html>');

            mywindow.document.close(); // necessary for IE >= 10
            mywindow.focus(); // necessary for IE >= 10

            mywindow.print();
            mywindow.close();

            return true;
        }


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
