angular.module('app')
    .controller('SignupController', function ($scope, $rootScope, $state, authService) {
        $scope.signup = function () {
            authService.signup(
                $scope.user,
                function () {
                    alertify.success('Successfully signed up!').dismissOthers();
                    $scope.user = {};
                    $state.go("signin");
                }, function () {
                    alertify.error('Username already taken!').dismissOthers();
                }
            );
        }
    });