angular.module('app')
    .service('userService', function ($http) {
        return {
            getAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            blockUser: function (id, blocked, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/users/' + id + '/block/' + blocked,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            filter: function (username, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users/filter/' + username,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },

            uploadPhoto: function (file, onSuccess, onError) {
                var formData = new FormData();
                formData.append('photo', file);
                var req = {
                    method: 'POST',
                    url: '/api/users/photo',
                    headers: {
                        'Content-Type': undefined
                    },
                    data: formData,
                    transformRequest: angular.identity
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });