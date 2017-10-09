angular.module('app', ['ui.router', 'ngMaterial'])
    .config(function ($stateProvider, $urlRouterProvider, $mdThemingProvider) {
        $urlRouterProvider.otherwise('/snippets');
        $mdThemingProvider.theme('default')
            .primaryPalette('grey', {'default': '700'})
            .accentPalette('deep-orange')
            .warnPalette('red')
            .backgroundPalette('grey', {'hue-1': '500'});
    }).run(function ($rootScope, authService, storageService) {
    authService.auth(
        function (res) {
            if (res.data == "") {
                storageService.setCurrentUser(null);
            } else {
                storageService.setCurrentUser(res.data);
            }
        }, function () {
            storageService.setCurrentUser(null);
        });
});
;