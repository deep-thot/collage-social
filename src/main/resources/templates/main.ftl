<!doctype html>
<html ng-app="collageApp">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-resource.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.js"></script>
    <script src="ng-file-upload-all.min.js" ></script>
    <script src="angular-filter.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="bootstrap-social.css" />

    <script src="app.js" ></script>
    <script src="service.js" ></script>
    <script src="list-controller.js" ></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="container">
<div ng-controller="ProfileListController">
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
                    <button ng-click="logout()">Logga ut</button>
                    <p class="navbar-text">${userDetails.name.givenName}</p>
                    <img src="${userDetails.image.url}" />
                </#if>
                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
<#if currentUser.loggedIn>

        <#if profileRequested??>
            Hej ${userDetails.name.givenName}, av någon anledning blev du inte igenkänd automatiskt. Du kommer åt sidan så fort ditt medlemskap i kören bekräftats
        <#else>
            <ng-view></ng-view>
        </#if>
    </div>
<#else>
    <div class="row">
        <div class="col-lg-10 center-block">
            <div class="col-lg-10 center-block">
                <h1>Välkommen till Collagekörens medlemskatalog</h1>
                <div class="col-lg-5 center-block marginTop20">
                    <a class="btn btn-social btn-lg btn-google" href="/login/google"><i class="fa fa-google"></i>Logga in med Google</a>
                </div>
            </div>
        </div>

    </div>
</#if>

</div>


</body>
</html>