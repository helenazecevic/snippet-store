angular.module('app')
    .controller('SigninController', function ($rootScope, $scope, $state, storageService, authService) {
        $scope.signin = function () {
            authService.signin(
                $scope.user,
                function (res) {
                    alertify.success('Successfully signed in!').dismissOthers();
                    storageService.setCurrentUser(res.data);
                    $scope.user = {};
                    $state.go("snippets");
                }
                , function () {
                    alertify.error('Wrong username or password!').dismissOthers();
                }
            );
        }
    });