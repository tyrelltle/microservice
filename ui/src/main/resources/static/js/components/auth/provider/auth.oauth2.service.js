'use strict';

angular.module('jhipsterApp')
    .factory('AuthServerProvider', function loginService($http, localStorageService, Base64) {
        return {
            login: function(credentials) {
                var data = "username=" + credentials.username + "&password="
                    + credentials.password + "&grant_type=password&scope=webshop&" +
                    "client_secret=acmesecret&client_id=acme";
                return $http.post('http://localhost:9999/uaa/oauth/token', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Authorization": "Basic " + Base64.encode("acme" + ':' + "acmesecret")
                    }
                }).success(function (response) {
                    var expiredAt = new Date();
                    expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
                    response.expires_at = expiredAt.getTime();
                    localStorageService.set('token', response);
                    return response;
                }).error(function(data, status, headers, config) {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                        console.log('asd');
                    });
            },
            logout: function() {
                // logout from the server
                $http.post('api/logout').then(function() {
                    localStorageService.clearAll();
                });
            },
            getToken: function () {
                return localStorageService.get('token');
            },
            hasValidToken: function () {
                var token = this.getToken();
                return token && token.expires_at && token.expires_at > new Date().getTime();
            }
        };
    });
