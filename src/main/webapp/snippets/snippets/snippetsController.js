angular.module('app')
    .controller('SnippetsController', function ($rootScope, $scope, $state, $mdDialog, snippetService, storageService, languageService) {
        $scope.user = storageService.getCurrentUser();

        languageService.getAll(function (res) {
            $scope.languagesList = res.data;
        }, function () {
            $scope.languagesList = [];
        });

        snippetService.getAll(function (res) {
            $scope.snippetsList = res.data;
        }, function () {
            $scope.snippetsList = [];
        });

        $scope.search = function () {
            console.log($scope.filterDto);
            snippetService.filterSnippets($scope.filterDto, function (res) {
                $scope.snippetsList = res.data;
            }, function () {

            });
        }

        $scope.addSnippet = function () {
            $mdDialog.show({
                controller: 'AddSnippetController',
                templateUrl: 'snippets/snippets/addSnippet/addSnippetDialog.html',
                parent: angular.element(document.body),
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            }).then(function (snippet) {
                snippetService.addSnippet(snippet, function (res) {
                    snippet.id = res.data;
                    $scope.snippetsList.push(snippet);
                    alertify.success('Successfully added snippet!').dismissOthers();
                }, function () {
                    alertify.error('Error while adding snippet!').dismissOthers();
                });
            }, function () {
            });
        };

        $scope.openSnippet = function (snippet) {
            $state.go('snippet', {id: snippet.id});
        };
    });