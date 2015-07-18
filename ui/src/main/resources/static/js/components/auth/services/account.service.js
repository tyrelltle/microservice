'use strict';

angular.module('jhipsterApp')
    .factory('Account', function Account(OAUTH_URL,$resource) {
        return $resource(OAUTH_URL+'/api/account', {}, {
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
