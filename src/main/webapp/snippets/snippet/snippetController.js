angular.module('app')
    .controller('SnippetController', function ($rootScope, $scope, $state, $stateParams, $mdDialog, snippetService, storageService) {
        $scope.user = storageService.getCurrentUser();

        snippetService.findById($stateParams.id, false, function (res) {
            $scope.snippet = res.data;
        }, function () {
            $scope.snippet = {};
            $state.go('snippets');
        });

        $scope.blockSnippet = function () {
            var block = !$scope.snippet.blocked;
            snippetService.blockSnippet($stateParams.id, block, function (res) {
                $scope.snippet = res.data;
            }, function () {

            });
        }

        $scope.deleteSnippet = function () {
            snippetService.deleteSnippet($stateParams.id, function () {
                $state.go('snippets');
            }, function () {

            });
        }

        $scope.addComment = function () {
            snippetService.addComment($stateParams.id, $scope.newComment, function (res) {
                var comment = res.data;
                $scope.snippet.comments.push(comment);
                $scope.newComment = {};
            }, function () {

            });
        }

        $scope.deleteComment = function (comment) {
            snippetService.deleteComment($stateParams.id, comment.id, function () {
                var index = $scope.snippet.comments.indexOf(comment);
                if (index > -1) {
                    $scope.snippet.comments.splice(index, 1);
                }
            }, function () {

            });
        };

        $scope.customOrder = function (comment) {
            if ($scope.orderByRating) {
                return comment.rating.positive - comment.rating.negative;
            }

            return comment.date;
        };

        $scope.userVoted = function (comment, upvote, relevant) {
            var votes = comment.rating.votes;
            for (i = 0; i < votes.length; i++) {
                if (votes[i].user.id == $scope.user.id) {
                    if (relevant) {
                        if (upvote && votes[i].status == 'UPVOTE') {
                            return true;
                        } else if (!upvote && votes[i].status == 'DOWNVOTE') {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    else {
                        return true;
                    }
                }
            }
            return false;
        };

        $scope.vote = function (comment, upvote) {
            snippetService.rateComment($stateParams.id, comment.id, {status: upvote}, function (res) {
                if (upvote == 'UPVOTE') {
                    comment.rating.positive++;
                } else {
                    comment.rating.negative++;
                }
                comment.rating.votes.push(res.data);
            }, function () {

            });
        };

        $scope.openUser = function (selectedUser) {
            if (selectedUser != null) {
                $mdDialog.show({
                    locals: {user: selectedUser},
                    controller: 'UserDialogController',
                    templateUrl: 'users/user/userDialog.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true,
                    fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
                }).then(function () {
                }, function () {
                });
            }
        };
    });