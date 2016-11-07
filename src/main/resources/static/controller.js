/**
 * Created by Jonatan on 2015-12-01.
 */
angular.module('collageSocial', ['ngResource'])
    .controller('ProfileListController', ['$scope', 'Profile', 'Auth', function($scope, Profile, Auth){

        $scope.reload = function(){
            $scope.profiles = Profile.list();
        };
        $scope.reload();
        $scope.newProfile = {};
        $scope.save = function (){
            Profile.new($scope.newProfile, function(value, headers){
                $scope.reload();
                $scope.newProfile.name = '';
            });
        };
        $scope.logout = function (){
            Auth.logout().$promise.then(function(){
                window.location.reload();
            });
        };
    }]);
