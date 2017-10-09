angular.module('app')
    .controller('UsersController', function ($rootScope, $scope, $state, $mdDialog, storageService, userService) {
        $scope.usersList = userService.getAll(
            function (res) {
                $scope.usersList = res.data;
            }, function () {
                $scope.usersList = [];
            }
        );

        $scope.search = function () {
            if ($scope.username == "") {
                $scope.usersList = userService.getAll(
                    function (res) {
                        $scope.usersList = res.data;
                    }, function () {
                        $scope.usersList = [];
                    }
                );
            } else {
                userService.filter($scope.username, function (res) {
                    $scope.usersList = res.data;
                }, function () {
                    $scope.usersList = [];
                });
            }
        }

        $scope.openUser = function (selectedUser) {
            $mdDialog.show({
                locals: {user: selectedUser},
                controller: 'UserDialogController',
                templateUrl: 'users/user/userDialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            }).then(function () {
                $scope.usersList = userService.getAll(
                    function (res) {
                        $scope.usersList = res.data;
                    }, function () {
                        $scope.usersList = [];
                    }
                );
            }, function () {
                $scope.usersList = userService.getAll(
                    function (res) {
                        $scope.usersList = res.data;
                    }, function () {
                        $scope.usersList = [];
                    }
                );
            });
        };
    });