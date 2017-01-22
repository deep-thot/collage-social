/**
 * Created by Jonatan on 2015-12-01.
 */
angular.module('collageSocial', ['ngResource', 'ngRoute', 'ngFileUpload', 'angular.filter', 'ngSanitize'])
    .controller('ProfileListController', ['$scope', 'Profile', 'User',  'Auth', '$location', '$routeParams', 'Upload',  function($scope, Profile, User, Auth, $location, $routeParams, Upload){
        $scope.voices = {
            SOPRANO_1 : 'Sopran 1',
            SOPRANO_2 : 'Sopran 2',
            ALTO_1 : 'Alt 1',
            ALTO_2 : 'Alt 2',
            TENOR_1 : 'Tenor 1',
            TENOR_2 : 'Tenor 2',
            BASS_1 : 'Bas 1',
            BASS_2 : 'Bas 2'
        };
        $scope.currentYear = new Date().getFullYear();
        $scope.showAdmin = false;
        $scope.editProfile = false;
        $scope.editImage = false;
        $scope.reloadString = '';
        function reload(){
            $scope.profiles = Profile.list(true, function (values) {
                values.map(function(p){
                    p.voiceText = $scope.voices[p.voice];
                    return p;
                });
                if($routeParams.id){
                    selectProfile($routeParams.id);
                }
            });
            $scope.user = User.get();
        }
        reload();
        $scope.$on('$routeChangeSuccess', function(){
            if($routeParams.id){
                if($scope.profiles && $scope.profiles.length > 0) {
                    selectProfile($routeParams.id);
                }
            }
        });
        $scope.newProfile = {};
        $scope.newImage = {};

        function uploadImage(id, image) {
            return Upload.upload({url: '/profile/image/' + id, data: {image: image}});
        }

        $scope.save = function (){

            Profile.new($scope.newProfile, function(value, headers){
                uploadImage(value.id, $scope.newImage.myImage).then(function(){
                    reload();
                    $scope.newProfile = {};
                    $scope.newImage.myImage = {};

                });

            });
        };
        $scope.logout = function (){
            Auth.logout().$promise.then(function(){
                window.location.reload();
            });
        };

        function selectProfile(id) {
            $scope.profile = $scope.profiles.filter(function (profile) {
                return profile.id == id;
            })[0];
            $scope.updateProfile =  {
                started: $scope.profile.started,
                bio: $scope.profile.rawBio,
                fbLink: $scope.profile.fbLink,
                lastFmProfile: $scope.profile.lastFmProfile,
                phoneNumber: $scope.profile.phoneNumber,
                address: $scope.profile.address
            };
            $scope.editProfile = false;
        }

        $scope.toggleAdmin = function (){
            $scope.showAdmin = !$scope.showAdmin;
        };

        $scope.showProfile = function(id){
            selectProfile(id);
            $location.path('profile/'+id);
        };



        $scope.update = function(){
            $scope.updateProfile.id = $scope.profile.id;
            Profile.update($scope.updateProfile, function (value) {
                $scope.profile = value;
                $scope.updateProfile = $scope.profile;
                $scope.toggleEdit();
            })
        };

        $scope.toggleEdit = function(){
            $scope.editProfile = !$scope.editProfile;
        };

        $scope.toggleImageEdit = function(){
            $scope.editImage = !$scope.editImage;
        };

        $scope.updateImage = function(){
            uploadImage($scope.profile.id, $scope.newImage.myImage).then(function(){
                $scope.newImage.myImage = {};
                $scope.newImage = {};
                $scope.reloadString = '?cb='+new Date().getTime();
                $scope.toggleImageEdit();
            })
        }


    }]);

