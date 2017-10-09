angular.module('app')
    .controller('UserDialogController', function ($scope, $mdDialog, user, storageService, userService) {
        $scope.currentUser = storageService.getCurrentUser();
        $scope.user = user;

        $scope.blockUser = function () {
            var block = !$scope.user.blocked;
            userService.blockUser($scope.user.id, block, function (res) {
                $scope.user = res.data;
            }, function () {

            });
        };

        $scope.cancel = function () {
            $mdDialog.cancel($scope.user);
        };

        $scope.uploadPhoto = function (files) {
            if (files && files[0].size > (1024 * 1024)) {
                alertify.error("Max 1MB photo size allowed.").dismissOthers();
                return;
            }
            userService.uploadPhoto(files[0], function (res) {
                console.log(res.data);
                $scope.user.picture = res.data.photo;
            }, function () {
            });
        };

        $scope.initMap = function () {
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 10,
                center: {lat: -34.397, lng: 150.644}
            });
            var geocoder = new google.maps.Geocoder();
            $scope.geocodeAddress(geocoder, map);
        };

        $scope.geocodeAddress = function (geocoder, resultsMap) {
            geocoder.geocode({'address': $scope.user.address.addressString}, function (results, status) {
                if (status === 'OK') {
                    resultsMap.setCenter(results[0].geometry.location);
                    var marker = new google.maps.Marker({
                        map: resultsMap,
                        position: results[0].geometry.location
                    });
                }
            });
        }
        angular.element(document).ready(function () {
            $scope.initMap();
        });
    });