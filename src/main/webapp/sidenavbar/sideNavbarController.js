angular.module('app')
    .controller('SideNavbarController', function ($scope, $rootScope, $mdDialog, $state, storageService, authService) {
        $scope.user = storageService.getCurrentUser();

        $scope.signoutCallback = function () {
            storageService.setCurrentUser(null);
            $state.go('snippets');
            window.location.reload();
        };

        $scope.signout = function () {
            authService.signout($scope.signoutCallback, $scope.signoutCallback);
        };

        $scope.openUser = function () {
            $mdDialog.show({
                locals: {user: $scope.user},
                controller: 'UserDialogController',
                templateUrl: 'users/user/userDialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            }).then(function () {
            }, function () {
            });
        };
    });