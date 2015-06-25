'use strict';

angular.module('jhipsterApp')
    .factory('Account', function Account($resource) {
        return $resource('http://localhost:9999/api/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
