angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('signup', {
                url: '/signup',
                views: {
                    'sidenavbar': {
                        templateUrl: 'sidenavbar/sidenavbar.html',
                        controller: 'SideNavbarController'
                    },
                    'content': {
                        templateUrl: 'authentication/signup/signup.html',
                        controller: 'SignupController'
                    }
                }
            })
            .state('signin', {
                url: '/signin',
                views: {
                    'sidenavbar': {
                        templateUrl: 'sidenavbar/sidenavbar.html',
                        controller: 'SideNavbarController'
                    },
                    'content': {
                        templateUrl: 'authentication/signin/signin.html',
                        controller: 'SigninController'
                    }
                }
            });
    });