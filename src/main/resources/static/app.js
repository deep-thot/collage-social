/**
 * Created by Jonatan on 2015-12-01.
 */
var collageApp = angular.module('collageApp', [
    'collageSocial',
    'profileServices',
    'ngRoute'
]);
collageApp.config(function ($routeProvider, $locationProvider){
    $routeProvider.when('/profile/:id', {
        templateUrl: '/profile.html'
    }).otherwise({
        templateUrl: '/list.html'
    })
});