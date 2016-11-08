/**
 * Created by Jonatan on 2015-12-01.
 */
angular.module('profileServices', ['ngResource']).factory('Profile', ['$resource',
    function($resource){
        var resource = $resource('/profile/:action', {}, {
            list: {method: 'GET', params:{action:'all'}, isArray:true},
            new: {method: 'POST', params:{action:'new'}, isArray:false}
        });
        return {
            list: function (success) {
                if (this.profiles) {
                    if(success) {
                        success(this.profiles);
                    }
                    return this.profiles;
                } else {
                    this.profiles = resource.list(success);
                    return this.profiles;
                }
            },
            new: resource.new
        };
    }]).factory('Auth', ['$resource', function($resource){
        return $resource('/:action', {}, {
            logout: {method: 'POST', params:{action:'logout'}, isArray:false}
        });
}]).factory('state', [function(){
    return {};

}]);

