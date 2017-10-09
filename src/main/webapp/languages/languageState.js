angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('languages', {
                url: '/languages',
                views: {
                    'sidenavbar': {
                        templateUrl: 'sidenavbar/sidenavbar.html',
                        controller: 'SideNavbarController'
                    },
                    'content': {
                        templateUrl: 'languages/languages.html',
                        controller: 'LanguageController'
                    }
                }
            });
    });