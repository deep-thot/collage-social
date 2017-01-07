<!doctype html>
<html ng-app="collageApp">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-resource.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
    <script src="ng-file-upload-all.min.js" ></script>
    <script src="angular-filter.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="app.js" ></script>
    <script src="service.js" ></script>
    <script src="list-controller.js" ></script>
</head>
<body>
<div class="container">
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#cs-nav" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Collage</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="cs-nav">
            <ul class="nav navbar-nav">
                <li><a href="/">Medlemslista</a></li>
            </ul>
            <div class="navbar-right">
                <#if userDetails??>
                <p class="navbar-text">${userDetails.name.givenName}</p>
                <img src="${userDetails.image.url}" />
                </#if>
            </div>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<#if principal?? && principal.authenticated>
<div ng-controller="ProfileListController">
    <#if profileRequested??>
        Hej ${userDetails.name.givenName}, av någon anledning blev du inte igenkänd automatiskt. Du kommer åt sidan så fort ditt medlemskap i kören bekräftats
    <#else>
        <ng-view></ng-view>
    </#if>


<button ng-click="logout()">Logga ut</button>
</div>
<#else>
<div class="row">
    <div class="col-lg-10 center-block">
    <a href="/login/google">Logga in med Google</a>
    </div>

</div>
</#if>

</div>


</body>
</html>