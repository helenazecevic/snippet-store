angular.module('app')
    .service('snippetService', function ($http) {
        return {
            getAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/snippets',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, popularity, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/snippets/' + id + '/' + popularity,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            addSnippet: function (snippet, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/snippets',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: snippet
                }
                $http(req).then(onSuccess, onError);
            },
            deleteSnippet: function (id, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/snippets/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            blockSnippet: function (id, blocked, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/snippets/' + id + '/block/' + blocked,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            filterSnippets: function (filterDto, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/snippets/filter',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: filterDto
                }
                $http(req).then(onSuccess, onError);
            },
            addComment: function (id, comment, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/snippets/' + id + '/comments',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: comment
                }
                $http(req).then(onSuccess, onError);
            },
            rateComment: function (id, commentId, vote, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/snippets/' + id + '/comments/' + commentId,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: vote
                }
                $http(req).then(onSuccess, onError);
            },
            deleteComment: function (id, commentId, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/snippets/' + id + '/comments/' + commentId,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                $http(req).then(onSuccess, onError);
            }
        }
    });