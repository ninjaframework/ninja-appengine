package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.CommentController;
import controllers.LicenseController;

public class Routes implements ApplicationRoutes {
	
	/**
	 * Using a (almost) nice DSL we can configure the router.
	 * 
	 * The second argument NinjaModuleDemoRouter contains
	 * all routes of a submodule. By simply injecting it we activate the routes.
	 * 
	 * @param router The default router of this application
	 */
    @Override
	public void init(Router router) {

        
        router.GET().route("/listLicenses").with(LicenseController.class, "listLicenses");
     
        router.GET().route("/assets/.*").with(AssetsController.class, "serve");
	}

}
