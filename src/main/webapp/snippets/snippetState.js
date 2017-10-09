angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('snippets', {
                url: '/snippets',
                views: {
                    'sidenavbar': {
                        templateUrl: 'sidenavbar/sidenavbar.html',
                        controller: 'SideNavbarController'
                    },
                    'content': {
                        templateUrl: 'snippets/snippets/snippets.html',
                        controller: 'SnippetsController'
                    }
                }
            })
            .state('snippet', {
                url:'/snippet/:id',
                views: {
                    'sidenavbar': {
                        templateUrl: 'sidenavbar/sidenavbar.html',
                        controller: 'SideNavbarController'
                    },
                    'content': {
                        templateUrl: 'snippets/snippet/snippet.html',
                        controller: 'SnippetController'
                    }
                }
            });
    });