angular.module('app')
    .service('languageService', function ($http) {
        return {
            getAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/languages',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/languages/' + id,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            addLanguage: function (language, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/languages',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    data: language
                }
                $http(req).then(onSuccess, onError);
            }
        }
    });