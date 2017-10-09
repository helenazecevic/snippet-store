angular.module('app')
    .service('storageService', function ($rootScope) {
        var currentUser;

        return {
            getCurrentUser: function () {
                console.log(currentUser);
                return currentUser;
            },
            setCurrentUser: function (value) {
                currentUser = value;
            }
        };
    });