/**
 * Created by Jonatan on 2015-12-01.
 */
angular.module('profileServices', ['ngResource']).factory('Profile', ['$resource',
    function($resource){
        return $resource('/profile/:action', {}, {
            list: {method: 'GET', params:{action:'all'}, isArray:true},
            new: {method: 'POST', params:{action:'new'}, isArray:false}
        });
    }]).factory('Auth', ['$resource', function($resource){
        return $resource('/:action', {}, {
            logout: {method: 'POST', params:{action:'logout'}, isArray:false}
        });
}]);

