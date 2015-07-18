'use strict';

angular.module('jhipsterApp')
    .factory('Account', function Account($rootScope,$resource) {
        return $resource($rootScope.OAUTH_URL+'/api/account', {}, {
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
