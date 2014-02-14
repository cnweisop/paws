package controllers.common

import play.api.mvc.{Action, Controller}
import models.common.{NavigationItem, NavigationMenu, Navigation}


object Application extends Controller {
  def navigation() = Action { implicit request =>
    val menus =
      Seq(
        NavigationMenu(
          Seq(
            NavigationItem("Sign Up", "#/signup"),
            NavigationItem("Sign In", "#/login")
          ),
          position = "left"
        ),
        NavigationMenu(
          Seq(
            NavigationItem("Sign Out", "#/signout")
          ),
          position = "right"
        )
      )

    val navigation = Navigation("default", menus)

    Ok(Navigation.toJson(navigation))
  }
}
