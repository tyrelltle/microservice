'use strict';

angular.module('jhipsterApp')
    .controller('CategoryController', function ($scope, Category,Upload,$rootScope) {
        $scope.categorys = [];
        $scope.loadAll = function() {
            Category.query(function(result) {
               $scope.categorys = result;
                $scope.categorys.forEach(function(cat){
                    cat.imgurl=$rootScope.GATEWAY_URL+'/api/product/imgget/categorys/'+cat.id+'/image/'+new Date().getTime().toString();
                });
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Category.get({id: id}, function(result) {
                $scope.category = result;
                $('#saveCategoryModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.category.id != null) {
                Category.update($scope.category,
                    function (response) {
                        alert(response.$resolved?"success!":"failed");
                    });
            } else {
                Category.save($scope.category,
                    function (response) {
                        alert(response.$resolved?"success!":"failed");
                        $scope.category.id=response.id;
                        $scope.categorys.unshift(response);
                     });
            }
        };

        $scope.delete = function (id) {
            Category.get({id: id}, function(result) {
                $scope.category = result;
                $('#deleteCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Category.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
          //  $('#saveCategoryModal').modal('hide');
           // $scope.clear();
        };

        $scope.clear = function () {
            $scope.category = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };


        /**
         * image upload
         */
        $scope.imgupload_states={
            showimg:false,
            url:''
        };

        $scope.uploadimg=function(file){
            if(file==null)
                return;
            file.upload = Upload.upload({
                url:$rootScope.GATEWAY_URL+'/api/product/categorys/image/upload',
                method: 'POST',
                file: file,
                fields:{category_id:$scope.category.id}
            });

            file.upload.then(function(response) {
                if(response.status!=200)
                    alert(response.statusText);
                else {
                    $scope.imgupload_states.showimg=true;
                    $scope.imgupload_states.url=$rootScope.GATEWAY_URL+'/api/product/imgget/categorys/'+$scope.category.id+'/image/'+new Date().getTime().toString();
                    $scope.refresh();
                    $scope.$apply();
                }
            }, function(response) {
                alert(response);
            });

            file.upload.progress(function(evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        }

        $scope.close=function(){
            $modalInstance.dismiss('cancel');
        }
    });
