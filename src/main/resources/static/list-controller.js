/**
 * Created by Jonatan on 2015-12-01.
 */
angular.module('collageSocial', ['ngResource', 'ngRoute'])
    .controller('ProfileListController', ['$scope', 'Profile', 'Auth', '$location', '$routeParams', '$route',  function($scope, Profile, Auth, $location, $routeParams, $route){

        function reload(){
            $scope.profiles = Profile.list();
        }
        reload();
        $scope.$on('$routeChangeSuccess', function(){
            if($routeParams.id){
                selectProfile($routeParams.id);
            }
        });
        $scope.newProfile = {};
        $scope.save = function (){
            Profile.new($scope.newProfile, function(value, headers){
                reload();
                $scope.newProfile.name = '';
            });
        };
        $scope.logout = function (){
            Auth.logout().$promise.then(function(){
                window.location.reload();
            });
        };

        function selectProfile(id) {
            $scope.profile = $scope.profiles.filter(function (profile) {
                console.log(profile.id);
                return profile.id == id;
            })[0];
        }

        $scope.showProfile = function(id){
            selectProfile(id);
            $location.path('profile/'+id);
        }


    }])
    .controller('ProfileController', ['$scope', 'state', 'Profile', '$routeParams', function($scope, state, Profile, $routeParams){
         Profile.list(function(profiles){
            $scope.profile = profiles.filter(function(profile){
                console.log(profile.id);
                return profile.id == $routeParams.id;
            })[0];
         })
    }]);
