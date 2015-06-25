'use strict';

angular.module('jhipsterApp')
    .factory('Register', function ($resource) {
        return $resource('http://localhost:9999/api/register', {}, {
        });
    });


