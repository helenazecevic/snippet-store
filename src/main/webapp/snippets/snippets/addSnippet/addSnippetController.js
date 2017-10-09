angular.module('app')
    .controller('AddSnippetController', function ($rootScope, $scope, $state, $mdDialog, languageService, storageService) {
        $scope.user = storageService.getCurrentUser();

        languageService.getAll(function (res) {
            $scope.languagesList = res.data;
        }, function () {
            $scope.languagesList = [];
        });

        $scope.cancel = function () {
            $mdDialog.cancel();
        }

        $scope.addSnippet = function () {
            $scope.snippet.timeRemaining = $scope.snippet.timeRemaining * 3600000;
            $mdDialog.hide($scope.snippet);
        };

        $scope.isDefault = function (language) {
            if (language.name == 'undefined') {
                return true;
            }
            return false;
        }
    });