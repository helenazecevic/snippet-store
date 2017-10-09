angular.module('app')
    .controller('LanguageController', function ($rootScope, $scope, $state, $mdDialog, languageService, storageService) {
        $scope.user = storageService.getCurrentUser();
        if ($scope.user == null || $scope.user.role != 'ADMIN') {
            $state.go('snippets');
        }
        languageService.getAll(function (res) {
            $scope.languagesList = res.data;
        }, function () {
            $scope.languagesList = [];
        });

        $scope.addLanguage = function () {
            var confirm = $mdDialog.prompt()
                .title('New language')
                .clickOutsideToClose(true)
                .placeholder('Language name...')
                .ariaLabel('Language name')
                .initialValue('')
                .ok('Add')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function (result) {
                var newLanguage = {name: result};
                languageService.addLanguage(
                    newLanguage, function () {
                        alertify.success('Language added!').dismissOthers();
                        $scope.languagesList.push(newLanguage);
                    }, function () {
                        alertify.error("Language must have a unique name!").dismissOthers();
                    }
                );
            }, function () {
            });
        };
    });