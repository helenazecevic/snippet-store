angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('users', {
                url: '/users',
                views: {
                    'sidenavbar': {
                        templateUrl: 'sidenavbar/sidenavbar.html',
                        controller: 'SideNavbarController'
                    },
                    'content': {
                        templateUrl: 'users/users/users.html',
                        controller: 'UsersController'
                    }
                }
            });
    });